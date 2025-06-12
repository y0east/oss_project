document.addEventListener('DOMContentLoaded', () => {
  /* ───── Mega-menu 제어 ───── */
  const megaMenu = document.getElementById('mega-menu');
  const nav = document.getElementById('mainNav');
  const navItems = document.querySelectorAll('.nav-item');
  const cols = document.querySelectorAll('.mega-col');

  const showMega = () => megaMenu.classList.remove('hidden');
  const hideMega = () => megaMenu.classList.add('hidden');
  const highlight = (col) =>
    cols.forEach((c) => c.classList.toggle('highlight', c.dataset.col === col));

  navItems.forEach((item) =>
    item.addEventListener('mouseenter', () => {
      showMega();
      highlight(item.dataset.col);
    })
  );
  cols.forEach((col) =>
    col.addEventListener('mouseenter', () => {
      showMega();
      highlight(col.dataset.col);
    })
  );
  nav.addEventListener('mouseleave', () => {
    hideMega();
    cols.forEach((c) => c.classList.remove('highlight'));
  });
  megaMenu.addEventListener('mouseleave', () => {
    hideMega();
    cols.forEach((c) => c.classList.remove('highlight'));
  });

  /* ───── 공지사항 상세 불러오기 ───── */
  const params = new URLSearchParams(location.search);
  const id = params.get('id');

  if (!id) {
    document.getElementById('post-title').textContent = '잘못된 요청입니다.';
    return;
  }

  fetch(`http://localhost:8080/api/posts/${id}`, {
      method: 'GET',
      credentials: 'include', // <- 쿠키 포함시키는 핵심 옵션!
    })
    .then((res) => {
      if (!res.ok) throw new Error('게시글을 불러올 수 없습니다');
      return res.json();
    })
    .then((post) => {
      const d = new Date(post.createdAt);
      const date = `${d.getFullYear()}.${String(d.getMonth() + 1).padStart(
        2,
        '0'
      )}.${String(d.getDate()).padStart(2, '0')}`;

      document.getElementById('post-title').textContent = post.title;
      document.getElementById('post-date').textContent = `작성일: ${date}`;
      document.getElementById('post-content').innerHTML = post.content.replace(
        /\n/g,
        '<br>'
      );
      document.getElementById(
        'post-views'
      ).textContent = `조회수: ${post.viewCount}`;
    })
    .catch((err) => {
      console.error(err);
      document.getElementById('post-title').textContent =
        '오류가 발생했습니다.';
      document.getElementById('post-date').textContent = '';
      document.getElementById('post-content').textContent = '';
      document.getElementById('post-views').textContent = '';
    });
});
