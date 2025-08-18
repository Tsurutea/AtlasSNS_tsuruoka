console.log("header.js 読み込みOK");

document.addEventListener('DOMContentLoaded', () => {
  const toggleButton = document.getElementById('toggleButton');
  const accordionMenu = document.getElementById('accordionMenu');

  if (!toggleButton || !accordionMenu) {
    console.error("要素が見つかりません");
    return;
  }

  toggleButton.addEventListener('click', () => {
    accordionMenu.classList.toggle('hidden');
    toggleButton.classList.toggle('close'); // アイコンを上下反転
  });
});