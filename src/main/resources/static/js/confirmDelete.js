document.addEventListener("DOMContentLoaded", () => {
  const deleteButtons = document.querySelectorAll(".delete-btn");
  const modal = document.getElementById("deleteModal");
  const confirmBtn = document.getElementById("confirmDeleteBtn");
  const cancelBtn = document.getElementById("cancelDeleteBtn");
  const postIdInput = document.getElementById("deletePostId");

  if (!modal || !confirmBtn || !cancelBtn || !postIdInput) {
    console.error("削除モーダルの要素が見つかりません");
    return;
  }

  deleteButtons.forEach(button => {
    button.addEventListener("click", () => {
      const postId = button.getAttribute("data-id");
      postIdInput.value = postId;
      modal.style.display = "block";
    });
  });

  cancelBtn.addEventListener("click", () => {
    modal.style.display = "none";
  });

  // モーダル外クリックで閉じる
  window.addEventListener("click", (e) => {
    if (e.target === modal) {
      modal.style.display = "none";
    }
  });
});