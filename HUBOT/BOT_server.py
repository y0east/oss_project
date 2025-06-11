# BOT_server.py
from flask import Flask, request, jsonify
from flask_cors import CORS
from langdetect import detect, DetectorFactory  # FastText → langdetect 교체
from hubot_kr import HubotChatbotKR
from hubot_eng import HubotChatbotENG
from hubot_ant import HubotChatbotANT

# 언어 감지 결과 일관성 설정
DetectorFactory.seed = 0

app = Flask(__name__)
CORS(app)

# 챗봇 인스턴스 생성
hubotKR = HubotChatbotKR()
hubotENG = HubotChatbotENG()
hubotANT = HubotChatbotANT()

@app.route('/chat', methods=['POST'])
def chat():
    data = request.json
    user_question = data.get('question')
    
    if not user_question:
        return jsonify({'error': '질문이 없습니다.'}), 400

    # langdetect로 언어 감지
    try:
        detected_lang = detect(user_question)
    except Exception as e:
        print(f"언어 감지 오류: {e}")
        detected_lang = 'en'  # 기본값 영어

    # 언어별 라우팅
    if detected_lang == 'ko':
        answer, category = hubotKR.answer(user_question)
    elif detected_lang == 'en':
        answer, category = hubotENG.answer(user_question)
    else:
        answer, category = hubotANT.answer(user_question, detected_lang=detected_lang)
    
    return jsonify({
        'answer': answer,
        'category': category,
        'detected_lang': detected_lang
    })

if __name__ == '__main__':
    app.run(port=5000)
