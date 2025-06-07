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

  /* ===== 게시글 작성 폼: 데모용 처리 ===== */
  const form = document.getElementById('postForm');
  form.addEventListener('submit', (e) => {
    e.preventDefault();
    alert('게시글이 임시로 저장되었습니다! 실제 저장 로직을 구현하세요.');
    form.reset();
  });
});
