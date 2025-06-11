# hubot_ant.py
import json
import os
import numpy as np
from langdetect import detect, DetectorFactory  # FastText → langdetect로 교체
from sentence_transformers import SentenceTransformer
from sklearn.metrics.pairwise import cosine_similarity
from transformers import M2M100ForConditionalGeneration, M2M100Tokenizer

# 언어 감지 결과 일관성을 위한 설정
DetectorFactory.seed = 0

class HubotChatbotANT:
    def __init__(self, folder_path=None):
        # 데이터 경로
        base_dir = os.path.dirname(os.path.abspath(__file__))
        if folder_path is None:
            folder_path = os.path.join(base_dir, 'hubot_data', 'eng_data')
        if not os.path.exists(folder_path):
            raise FileNotFoundError(f"Folder not found: {folder_path}")

        # 영어 데이터 로드
        file_list = os.listdir(folder_path)
        if len(file_list) == 1:
            file_path = os.path.join(folder_path, file_list[0])
            with open(file_path, encoding='utf-8') as f:
                qa_dataset = json.load(f)
                self.univ_qa_list = qa_dataset["univ_qa"]
                self.chatting_qa_list = qa_dataset["chatting"]
        else:
            raise Exception("Folder should contain exactly one file")

        # 영어용 SimCSE 모델 로드
        self.model = SentenceTransformer('princeton-nlp/sup-simcse-bert-base-uncased')
        
        # 임베딩 사전 계산
        self.univ_question_embeddings = self.model.encode(
            [item['question'] for item in self.univ_qa_list],
            convert_to_numpy=True
        )
        self.chatting_question_embeddings = self.model.encode(
            [item['question'] for item in self.chatting_qa_list],
            convert_to_numpy=True
        )

        # 번역기 모델 및 토크나이저 (M2M-100)
        self.translator_tokenizer = M2M100Tokenizer.from_pretrained("facebook/m2m100_418M")
        self.translator_model = M2M100ForConditionalGeneration.from_pretrained("facebook/m2m100_418M")

    def lang_code(self, lang):
        lang_map = {
            'fr': 'fr', 'de': 'de', 'ja': 'ja', 
            'zh': 'zh', 'es': 'es', 'ru': 'ru',
            'it': 'it', 'pt': 'pt', 'ar': 'ar',
            'nl': 'nl', 'sv': 'sv', 'fi': 'fi',
            'pl': 'pl', 'tr': 'tr', 'cs': 'cs',
            'he': 'he', 'vi': 'vi', 'id': 'id',
            'hi': 'hi', 'th': 'th'
        }
        return lang_map.get(lang, 'en')

    def translate(self, text, src_lang, tgt_lang):
        self.translator_tokenizer.src_lang = src_lang
        encoded = self.translator_tokenizer(text, return_tensors="pt")
        generated_tokens = self.translator_model.generate(
            **encoded,
            forced_bos_token_id=self.translator_tokenizer.get_lang_id(tgt_lang)
        )
        return self.translator_tokenizer.batch_decode(generated_tokens, skip_special_tokens=True)[0]

    def answer(self, user_question, detected_lang=None, threshold=0.6):
        # 1. 언어 감지 (서버에서 감지한 언어 사용)
        try:
            user_lang = detected_lang if detected_lang else detect(user_question)
        except:
            user_lang = 'en'  # 감지 실패 시 기본값 영어

        # 2. 언어 코드 매핑
        src_lang = self.lang_code(user_lang)
        if src_lang == 'unknown':
            src_lang = 'en'
        tgt_lang = 'en'

        # 3. 입력 번역 (영어가 아니면)
        if src_lang != 'en':
            question_in_english = self.translate(user_question, src_lang=src_lang, tgt_lang=tgt_lang)
        else:
            question_in_english = user_question

        # 4. 답변 생성
        user_embedding = self.model.encode(question_in_english, convert_to_numpy=True)
        univ_similarities = cosine_similarity([user_embedding], self.univ_question_embeddings)[0]
        chatting_similarities = cosine_similarity([user_embedding], self.chatting_question_embeddings)[0]

        univ_best_idx = np.argmax(univ_similarities)
        univ_best_score = univ_similarities[univ_best_idx]
        chatting_best_idx = np.argmax(chatting_similarities)
        chatting_best_score = chatting_similarities[chatting_best_idx]

        if max(univ_best_score, chatting_best_score) < threshold:
            answer_text = "I'm sorry, I don't know the answer."
            source = None
        else:
            if univ_best_score >= chatting_best_score:
                answer_text = self.univ_qa_list[univ_best_idx]['answer']
                source = "University QA"
            else:
                answer_text = self.chatting_qa_list[chatting_best_idx]['answer']
                source = "General Chat"

        # 5. 답변 번역 (영어가 아니면)
        if src_lang != 'en':
            answer_text = self.translate(answer_text, src_lang='en', tgt_lang=src_lang)

        return answer_text, source
