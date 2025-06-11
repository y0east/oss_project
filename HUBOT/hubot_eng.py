import json
import os
import numpy as np
from sentence_transformers import SentenceTransformer
from sklearn.metrics.pairwise import cosine_similarity

class HubotChatbotENG:
    def __init__(self, folder_path=None):
        # 경로 설정 (영어 데이터 폴더)
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

    def answer(self, user_question, threshold=0.6):
        user_embedding = self.model.encode(user_question, convert_to_numpy=True)
        
        # 유사도 계산
        univ_similarities = cosine_similarity([user_embedding], self.univ_question_embeddings)[0]
        chatting_similarities = cosine_similarity([user_embedding], self.chatting_question_embeddings)[0]

        # 최대 유사도 인덱스 찾기
        univ_best_idx = np.argmax(univ_similarities)
        univ_best_score = univ_similarities[univ_best_idx]
        chatting_best_idx = np.argmax(chatting_similarities)
        chatting_best_score = chatting_similarities[chatting_best_idx]

        if max(univ_best_score, chatting_best_score) < threshold:
            return "I'm sorry, I don't know the answer.", None
        else:
            if univ_best_score >= chatting_best_score:
                return self.univ_qa_list[univ_best_idx]['answer'], "University QA"
            else:
                return self.chatting_qa_list[chatting_best_idx]['answer'], "General Chat"
