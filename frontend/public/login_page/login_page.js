// login_page.js
document.addEventListener('DOMContentLoaded', () => {
  const form = document.querySelector('form[action*="/api/login"]');
  if (!form) return;

  const btn = form.querySelector('button[type="submit"]');

  form.addEventListener('submit', async (e) => {
    e.preventDefault(); // 🚫 기본 form 제출 방지
    btn.disabled = true;

    // 🔧 수동으로 form 데이터 추출
    const studentId = form.querySelector('[name="student_id"]').value.trim();
    const password = form.querySelector('[name="password"]').value.trim();

    if (!studentId || !password) {
      alert('학번과 비밀번호를 모두 입력하세요.');
      btn.disabled = false;
      return;
    }

    try {
      const res = await fetch('http://localhost:8080/api/login', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ studentId, password }), // ✅ JSON 전송
      });

      if (!res.ok) throw new Error(`HTTP 오류: ${res.status}`);
      const data = await res.json();

      if (data.sessionToken) {
        alert(data.message || '로그인 성공');
        localStorage.setItem('sessionToken', data.sessionToken);
        window.location.href = '/main_page/main_page.html';
      } else {
        alert(data.message || '아이디 또는 비밀번호가 틀렸습니다.');
      }
    } catch (err) {
      console.error('로그인 실패:', err);
      alert('로그인 중 문제가 발생했습니다. 다시 시도해주세요.');
    } finally {
      btn.disabled = false;
    }
  });
});
