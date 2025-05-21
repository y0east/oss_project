from flask import Flask, request, jsonify
from flask_cors import CORS  # 추가
from hubot import HubotChatbot

app = Flask(__name__)
CORS(app)  # 이 줄이 반드시 필요합니다!

chatbot = HubotChatbot()

@app.route('/chat', methods=['POST'])
def chat():
    data = request.json
    user_question = data.get('question')
    if not user_question:
        return jsonify({'error': '질문이 없습니다.'}), 400
    answer, category = chatbot.answer(user_question)
    return jsonify({'answer': answer, 'category': category})

if __name__ == '__main__':
    app.run(port=5000)
