const element = document.querySelector('.info-box .info .like');
if (element) {
    element.addEventListener('click', handleClickLike);
}

function handleClickLike(e) {
    e.target.classList.toggle('liked');
}
