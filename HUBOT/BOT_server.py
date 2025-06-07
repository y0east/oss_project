from flask import Flask, request, jsonify
from flask_cors import CORS
from hubot_kr import HubotChatbotKR
from hubot_eng import HubotChatbotENG

app = Flask(__name__)
CORS(app)

hubotKR = HubotChatbotKR()
hubotENG = HubotChatbotENG()

def is_korean(text):
    return any('\uac00' <= char <= '\ud7a3' for char in text)

@app.route('/chat', methods=['POST'])
def chat():
    data = request.json
    user_question = data.get('question')
    if not user_question:
        return jsonify({'error': '질문이 없습니다.'}), 400

    if is_korean(user_question):
        answer, category = hubotKR.answer(user_question)
    else:
        answer, category = hubotENG.answer(user_question)

    return jsonify({'answer': answer, 'category': category})

if __name__ == '__main__':
    app.run(port=5000)
