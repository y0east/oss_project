/* Mega-menu 제어 & 초기화 */
document.addEventListener('DOMContentLoaded', () => {
  const megaMenu = document.getElementById('mega-menu');
  const nav = document.getElementById('mainNav');
  const navItems = document.querySelectorAll('.nav-item');
  const cols = document.querySelectorAll('.mega-col');

  const highlight = (colName) => {
    cols.forEach((c) =>
      c.classList.toggle('highlight', c.dataset.col === colName)
    );
  };

  const showMega = () => megaMenu.classList.remove('hidden');
  const hideMega = () => {
    megaMenu.classList.add('hidden');
    cols.forEach((c) => c.classList.remove('highlight'));
  };

  /* ── 각 Nav 항목 & Mega-col Hover 처리 ───────────────── */
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

  /* 네비·메가메뉴 모두 벗어나면 숨김 */
  nav.addEventListener('mouseleave', hideMega);
  megaMenu.addEventListener('mouseleave', hideMega);
});
