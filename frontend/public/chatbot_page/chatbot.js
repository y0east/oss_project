// chat.js

const form = document.getElementById('chat-form');
const input = document.getElementById('chat-input');
const body  = document.getElementById('chat-body');
const faqToggle   = document.getElementById('faq-toggle');
const faqDropdown = document.getElementById('faq-dropdown');

/* ─── 공통 메시지 삽입 ─── */
function appendMessage(content, sender){
  const msg = document.createElement('div');
  msg.className = 'message ' + sender;
  const bubble = document.createElement('div');
  bubble.className = 'bubble';
  bubble.textContent = content;
  msg.appendChild(bubble);
  body.appendChild(msg);
  body.scrollTop = body.scrollHeight;
}

/* ─── 로딩 후 Flask 서버에 POST 요청 & 답변 출력 ─── */
function botReply(text){
  document.body.classList.add('surf');        // 파도 모드 ON

  const msg    = document.createElement('div');
  msg.className = 'message bot';
  const bubble = document.createElement('div');
  bubble.className = 'bubble';
  bubble.innerHTML = '<div class="loader"><span></span><span></span><span></span></div>';
  msg.appendChild(bubble);
  body.appendChild(msg);
  body.scrollTop = body.scrollHeight;

  // Flask 서버에 POST 요청
  fetch('http://localhost:5000/chat', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify({question: text})
  })
  .then(response => response.json())
  .then(data => {
    setTimeout(()=>{
      if (data.answer) {
        bubble.textContent = data.answer;
      } else {
        bubble.textContent = '죄송합니다. 답변을 가져오지 못했습니다.';
      }
      document.body.classList.remove('surf');
    }, 800); // 약간의 딜레이로 자연스럽게
  })
  .catch(err => {
    setTimeout(()=>{
      bubble.textContent = '서버와 통신에 실패했습니다.';
      document.body.classList.remove('surf');
    }, 800);
  });
}

/* ─── 입력 이벤트 ─── */
form.addEventListener('submit',e=>{
  e.preventDefault();
  const text = input.value.trim();
  if(!text) return;
  appendMessage(text,'user');
  input.value = '';
  botReply(text);
});

/* ─── FAQ 드롭다운 ─── */
faqToggle.addEventListener('click',()=>faqDropdown.classList.toggle('show'));
faqDropdown.querySelectorAll('div').forEach(item=>{
  item.addEventListener('click',e=>{
    const text = e.target.textContent;
    // 나가기 버튼이면 클릭 처리 X
    if(text === '나가기') return;
    input.value = text;
    faqDropdown.classList.remove('show');
    form.dispatchEvent(new Event('submit'));
  });
});

/* ─── 나가기 버튼 ─── */
document.getElementById('exit-btn').addEventListener('click',()=>{
  document.querySelector('.chat-wrapper').style.display='none';
  faqDropdown.classList.remove('show');
});

document.getElementById('exit-btn').addEventListener('click', function() {
  // 예시: 홈페이지로 이동
  window.location.href = 'http://localhost:3000/main_page/main_page.html';  // 원하는 이동할 URL로 변경
});
