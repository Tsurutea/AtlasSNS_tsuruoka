document.addEventListener('DOMContentLoaded', () => {
  const editButtons = document.querySelectorAll('.edit-open-btn'); // ← 修正
  const modal = document.getElementById('editModal');
  const closeModalBtn = document.getElementById('closeModalBtn');
  const editPostIdInput = document.getElementById('editPostId');
  const editContentInput = document.getElementById('editContent');

  // 投稿ごとの「編集」ボタンにイベント設定
  editButtons.forEach(button => {
    button.addEventListener('click', () => {
      const postId = button.getAttribute('data-id');
      const content = button.getAttribute('data-content');

      editPostIdInput.value = postId;
      editContentInput.value = content;

      modal.style.display = 'block';
    });
  });

  // モーダルを閉じる
  closeModalBtn.addEventListener('click', () => {
    modal.style.display = 'none';
  });

  // モーダル外クリックで閉じる
  window.addEventListener('click', (e) => {
    if (e.target === modal) {
      modal.style.display = 'none';
    }
  });
});