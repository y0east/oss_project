document.addEventListener('DOMContentLoaded', () => {
  /* ===== Mega-menu 제어 ===== */
  const megaMenu = document.getElementById('mega-menu');
  const navItems = document.querySelectorAll('.nav-item');
  const nav = document.getElementById('mainNav');
  const cols = document.querySelectorAll('.mega-col');

  const show = () => megaMenu.classList.remove('hidden');
  const hide = () => megaMenu.classList.add('hidden');
  const highlight = (col) =>
    cols.forEach((c) => c.classList.toggle('highlight', c.dataset.col === col));

  navItems.forEach((item) =>
    item.addEventListener('mouseenter', () => {
      show();
      highlight(item.dataset.col);
    })
  );
  cols.forEach((col) =>
    col.addEventListener('mouseenter', () => {
      show();
      highlight(col.dataset.col);
    })
  );
  nav.addEventListener('mouseleave', hide);
  megaMenu.addEventListener('mouseleave', hide);

  /* ===== 로그인 처리 ===== */
  const form = document.getElementById('loginForm');
  const btn = form.querySelector('button[type="submit"]');

  form.addEventListener('submit', async (e) => {
    e.preventDefault();
    btn.disabled = true;

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
        body: JSON.stringify({ studentId, password }),
        credentials: "include"
      });

      if (!res.ok) throw new Error(`HTTP 오류: ${res.status}`);
      const data = await res.json();

      if (data.accessToken) {
        alert(data.message || '로그인 성공');
        localStorage.setItem('accessToken', data.accessToken);
        console.log("data.accessToken" + data.accessToken);
        console.log("data.refreshToken" + data.refreshToken);
        localStorage.setItem('refreshToken', data.refreshToken);
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
