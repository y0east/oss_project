document.addEventListener('DOMContentLoaded', () => {
  /* ───────── Mega-menu 제어 ───────── */
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

  /* ───────── 공지사항 동적 로딩 ───────── */
  const container = document.getElementById('posts-container');

  fetch('http://localhost:8080/api/posts')
    .then((res) => {
      if (!res.ok) throw new Error('네트워크 응답 오류');
      return res.json();
    })
    .then((data) => {
      const posts = Array.isArray(data) ? data : [data];
      posts.sort((a, b) => b.id - a.id); // 최신순

      container.innerHTML = '';
      if (posts.length === 0) {
        container.innerHTML =
          '<p class="text-center text-gray-500">공지사항이 없습니다.</p>';
        return;
      }

      posts.forEach((post) => {
        // 날짜 포맷
        const d = new Date(post.createdAt);
        const date = `${d.getFullYear()}.${String(d.getMonth() + 1).padStart(
          2,
          '0'
        )}.${String(d.getDate()).padStart(2, '0')}`;

        // 카드(article) 생성
        const article = document.createElement('article');
        article.className =
          'bg-[linear-gradient(135deg,#002D56_0%,#003B70_100%)] rounded-lg shadow-md p-4 flex justify-between items-start text-white';
        article.innerHTML = `
          <div class="max-w-[80%]">
            <h4 class="font-semibold mb-2">${post.title}</h4>
            <p class="text-sm mb-4 line-clamp-2">${post.content}</p>
            <p class="flex items-center gap-2 text-sm opacity-90">
              <svg xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 24 24" class="w-4 h-4">
                <path d="M19.5 3h-1V1.5a.75.75 0 10-1.5 0V3h-9V1.5a.75.75 0 10-1.5 0V3h-1A2.25 2.25 0 003.75 5.25v13.5A2.25 2.25 0 006 21h13.5a2.25 2.25 0 002.25-2.25V5.25A2.25 2.25 0 0019.5 3zM20.25 9.75h-16.5V6h16.5v3.75z"/>
              </svg>
              ${date}
            </p>
          </div>
          <div class="flex flex-col items-end gap-2 text-sm font-medium">
            <span class="flex items-center gap-1 opacity-90">
              <svg xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 24 24" class="w-4 h-4">
                <path d="M12 5C7 5 2.73 8.11 1 12c1.73 3.89 6 7 11 7s9.27-3.11 11-7c-1.73 3.89-6-7-11-7zm0 11a4 4 0 110-8 4 4 0 010 8z"/>
              </svg>
              조회수: ${post.viewCount}
            </span>
          </div>
        `;

        const link = document.createElement('a');
        link.href = `/post_page/post_page.html?id=${post.id}`;
        link.className = 'block hover:opacity-90 transition-opacity';
        link.appendChild(article);
        container.appendChild(link);
      });
    })
    .catch((err) => {
      console.error(err);
      container.innerHTML =
        '<p class="text-center text-red-500">공지사항을 불러오는 중 오류가 발생했습니다.</p>';
    });
});
