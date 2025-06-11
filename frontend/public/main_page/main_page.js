document.addEventListener('DOMContentLoaded', () => {
  /* ───────── Mega-menu 제어 ───────── */
  const megaMenu = document.getElementById('mega-menu');
  const nav = document.getElementById('mainNav');
  const navItems = document.querySelectorAll('.nav-item');
  const cols = document.querySelectorAll('.mega-col');

  const highlight = (colName) =>
    cols.forEach((c) =>
      c.classList.toggle('highlight', c.dataset.col === colName)
    );

  const showMega = () => megaMenu.classList.remove('hidden');
  const hideMega = () => {
    megaMenu.classList.add('hidden');
    cols.forEach((c) => c.classList.remove('highlight'));
  };

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

  nav.addEventListener('mouseleave', hideMega);
  megaMenu.addEventListener('mouseenter', showMega);
  megaMenu.addEventListener('mouseleave', hideMega);

  /* ───────── 게시글 동적 로딩 ───────── */
  const newsGrid = document.getElementById('newsGrid');

  const formatDate = (isoStr) => {
    const d = new Date(isoStr);
    return `${d.getFullYear()}.${String(d.getMonth() + 1).padStart(
      2,
      '0'
    )}.${String(d.getDate()).padStart(2, '0')}`;
  };

  const createCard = (post) => {
    const link = document.createElement('a');
    link.href = `http://localhost:3000/post_page/post_page.html?id=${post.id}`;
    link.className = 'block';

    const art = document.createElement('article');
    art.className =
      'bg-[#6b5b4b] text-gray-100 p-4 rounded-md shadow hover:-translate-y-1 transition';

    const h4 = document.createElement('h4');
    h4.className = 'text-xs font-semibold mb-1';
    h4.textContent = post.title;

    const p = document.createElement('p');
    p.className = 'text-sm line-clamp-2';
    p.textContent = post.content;

    const time = document.createElement('time');
    time.className = 'block mt-3 text-xs opacity-80';
    time.textContent = formatDate(post.createdAt);

    art.append(h4, p, time);
    link.appendChild(art);
    return link;
  };

  const loadPosts = async () => {
    try {
      const res = await fetch('http://localhost:8080/api/posts', {
        method: 'GET',
        credentials: 'include',
        headers: { Accept: 'application/json' },
      });
      if (!res.ok) throw new Error(`HTTP ${res.status}`);
      const posts = await res.json();

      newsGrid.innerHTML = '';
      posts
        .sort((a, b) => new Date(b.createdAt) - new Date(a.createdAt))
        .slice(0, 4)
        .forEach((p) => newsGrid.appendChild(createCard(p)));
    } catch (err) {
      console.error('[게시글 로딩 실패]', err);
      newsGrid.innerHTML =
        '<p class="text-red-700">게시글을 불러오지 못했습니다.</p>';
    }
  };

  loadPosts();
});
