document.addEventListener('DOMContentLoaded', () => {
  const cards = document.querySelectorAll('article');

  cards.forEach((card, index) => {
    card.addEventListener('click', async () => {
      try {
        // 게시글 목록 요청
        const postsRes = await fetch('http://localhost:8080/api/posts');
        if (!postsRes.ok) throw new Error('게시글 목록 조회 실패');
        const posts = await postsRes.json();

        const post = posts[index];
        if (!post) {
          alert('선택한 게시글이 없습니다.');
          return;
        }

        // 게시글 상세 내용 요청
        const detailRes = await fetch(
          `http://localhost:8080/api/posts/${post.id}`
        );
        if (!detailRes.ok) throw new Error('게시글 상세 조회 실패');
        const detail = await detailRes.json();

        alert(
          `제목: ${detail.title}\n작성자: ${detail.author}\n내용: ${
            detail.content
          }\n작성일: ${new Date(detail.createdAt).toLocaleString()}`
        );
      } catch (err) {
        alert(`오류 발생: ${err.message}`);
      }
    });
  });
});
