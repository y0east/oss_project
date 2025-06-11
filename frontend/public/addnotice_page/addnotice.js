document.addEventListener('DOMContentLoaded', () => {
  /* ===== Mega-menu control ===== */
  const megaMenu = document.getElementById('mega-menu');
  const nav = document.getElementById('mainNav');
  const navItems = document.querySelectorAll('.nav-item');
  const cols = document.querySelectorAll('.mega-col');

  function highlight(colName) {
    cols.forEach((c) => {
      c.classList.toggle('highlight', c.dataset.col === colName);
    });
  }
  function showMega() {
    megaMenu.classList.remove('hidden');
  }
  function hideMega() {
    megaMenu.classList.add('hidden');
    cols.forEach((c) => c.classList.remove('highlight'));
  }

  navItems.forEach((item) => {
    item.addEventListener('mouseenter', () => {
      showMega();
      highlight(item.dataset.col);
    });
  });
  nav.addEventListener('mouseleave', hideMega);
  megaMenu.addEventListener('mouseenter', showMega);
  megaMenu.addEventListener('mouseleave', hideMega);

  /* ===== 게시글 작성 폼 처리 ===== */
  const form = document.getElementById('postForm');

  form.addEventListener('submit', async (e) => {
    e.preventDefault();

    // 1) 입력값 읽기
    const title = form.querySelector('[name="title"]').value.trim();
    const content = form.querySelector('[name="content"]').value.trim();

    if (!title || !content) {
      alert('제목과 내용을 모두 입력하세요.');
      return;
    }

    try {
      // 2) JSON 형식으로 백엔드에 전송
      const res = await fetch('http://localhost:8080/api/posts/createPost', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ title, content }),
      });

      // 3) 응답 확인
      if (!res.ok) {
        throw new Error(`HTTP 오류: ${res.status}`);
      }

      alert('게시글이 성공적으로 등록되었습니다!');
      form.reset();
    } catch (err) {
      console.error(err);
      alert('게시글 등록 중 오류가 발생했습니다.');
    }
  });
});
