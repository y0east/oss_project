import json
import os
import numpy as np
from sentence_transformers import SentenceTransformer
from sklearn.metrics.pairwise import cosine_similarity

class HubotChatbotKR:
    def __init__(self, folder_path=None):
        # hubot.py 파일이 있는 폴더 기준으로 경로 설정
        base_dir = os.path.dirname(os.path.abspath(__file__))
        if folder_path is None:
            folder_path = os.path.join(base_dir, 'hubot_data', 'cur_data')
        print("스크립트 기준 절대경로:", folder_path)
        if not os.path.exists(folder_path):
            raise FileNotFoundError(f"폴더가 존재하지 않습니다: {folder_path}")
        file_list = os.listdir(folder_path)
        if len(file_list) == 1:
            file_path = os.path.join(folder_path, file_list[0])
            with open(file_path, encoding='utf-8') as f:
                qa_dataset = json.load(f)
                self.univ_qa_list = qa_dataset["univ_qa"]
                self.chatting_qa_list = qa_dataset["chatting"]
        else:
            raise Exception("폴더에 파일이 없거나, 파일이 여러 개 있습니다.")

        # 모델 로드
        self.model = SentenceTransformer('BM-K/KoSimCSE-roberta-multitask')

        # 질문 임베딩 미리 계산
        self.univ_question_embeddings = self.model.encode(
            [item['question'] for item in self.univ_qa_list],
            convert_to_numpy=True
        )
        self.chatting_question_embeddings = self.model.encode(
            [item['question'] for item in self.chatting_qa_list],
            convert_to_numpy=True
        )

    def get_embedding(self, text):
        return self.model.encode(text, convert_to_numpy=True)
    
    def answer(self, user_question, threshold=0.6):
        user_embedding = self.get_embedding(user_question)
        univ_similarities = cosine_similarity([user_embedding], self.univ_question_embeddings)[0]
        chatting_similarities = cosine_similarity([user_embedding], self.chatting_question_embeddings)[0]

        univ_best_idx = int(np.argmax(univ_similarities))
        univ_best_score = univ_similarities[univ_best_idx]
        chatting_best_idx = int(np.argmax(chatting_similarities))
        chatting_best_score = chatting_similarities[chatting_best_idx]

        if max(univ_best_score, chatting_best_score) < threshold:
            return "죄송해요, 잘 모르겠어요.", None
        else:
            if univ_best_score >= chatting_best_score:
                return self.univ_qa_list[univ_best_idx]['answer'], "학교 QA"
            else:
                return self.chatting_qa_list[chatting_best_idx]['answer'], "일반 채팅"
