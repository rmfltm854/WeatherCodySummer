const element = document.querySelector('.info-box .info .like');
if (element) {

    element.addEventListener('click', handleClickLike);
}

function handleClickLike(e) {
    console.log('하이');
    e.target.classList.toggle('liked');
    console.log('하이');
}

// 이벤트 리스너를 추가하는 함수
function addClickEventListeners() {
    const elements = document.querySelectorAll('.info-box .info .like');
    elements.forEach((element) => {
        element.addEventListener('click', handleClickLike);
    });
}

// 초기에 한 번 이벤트 리스너를 추가하고 이후에도 계속 추가하기 위해
addClickEventListeners();
