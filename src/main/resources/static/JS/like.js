//8월 11일 로그인 여부확인해서 like버튼 접근 차단및 동작 구현 완료
const element = document.querySelector('.info-box .info .like');
if (element) {
    element.addEventListener('click', handleClickLike);
}

function getCookie() {
    const value = `${document.cookie}`;
    if (value) {//쿠키값존재
        console.log(value);
        return value;
    }else {
        // 쿠키 값을 찾지 못한 경우
        return null;
    }
}

function updateLikeCount(element, likeCount) {
    const likeCountElement = element.closest('.info-box').querySelector('.like-count');
    likeCountElement.textContent = likeCount;
}

// 좋아요 버튼 클릭 처리 함수
function handleClickLike(e) {
    // 로그인 여부 확인
    const loggedIn = getCookie('loggedIn');
    if (loggedIn !== null) {
        const isLiked = e.target.classList.contains('liked'); // 현재 좋아요 상태 확인
        // 이미지 경로 추출
        const imgSrc = e.target.closest('.info-box').querySelector('img').src;
        e.target.classList.toggle('liked');

        // 서버로 좋아요 상태 및 이미지 이름 전송
        $.ajax({
            url: "/like",
            method: "GET",
            data: {imgSrc: imgSrc, action: isLiked ? "unlike" : "like"}, // 좋아요 상태에 따라 "like" 또는 "unlike" 값 전송,, 쿠키값 보내야 한다고 하면 추가,cookieValue: getCookie('loggedIn')
            dataType: "json",
            success: function (response) {
                const likeCount = parseInt(response); //response.count에 있는 숫자를 가져와서 정수로 바꿔서 likeCount라는 이름으로 저장
                const likeCountElement = e.target.closest('.info-box').querySelector('.like-count');
                updateLikeCount(e.target, likeCount);
                // 좋아요 숫자를 like count에 업데이트
                likeCountElement.textContent = likeCount;
                console.log(likeCount)
                console.log("ajax 인식한다 게이야")

                // likeCountElement.textContent = likeCount;

                // 버튼의 클래스 업데이트
                if (isLiked) {
                    e.target.classList.remove('liked');
                    console.log("좋아요가 취소 된다요")
                } else {
                    e.target.classList.add('liked');
                    console.log("좋아요가 된다요");
                }
            },
            error: function () {
                console.log("AJAX 요청 오류 한마디로 ㅈ댔다고 볼 수 있지");
            }
        });
    }
    else if(loggedIn === null ) {//쿠키 값이 비어 있을때 실행
        console.log('로그인되어 있지 않습니다.');
        alert('로그인을 해주세요.');
    }
}


function addClickEventListeners() {
    const elements = document.querySelectorAll('.info-box .info .like');
    elements.forEach((element) => {
        element.addEventListener('click', handleClickLike);
    });
}

addClickEventListeners();
