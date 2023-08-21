
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

// 리뷰 작성 버튼 클릭 처리
const button = document.querySelector('.review-write-button');

// 버튼 클릭 이벤트 핸들러를 추가합니다.
button.addEventListener('click', function (e) {
    e.preventDefault(); // 기본 제출 동작 중지

    const loggedIn = getCookie('loggedIn');
    if (loggedIn !== null) {
        // 리뷰 작성 코드 추가

        const reviewText = $("textarea[name='reveiw']").val(); // 리뷰 텍스트 가져오기
        const imgSrc = $(".main-pro img").attr("src"); // 이미지 경로 가져오기

        // Ajax를 사용하여 리뷰 작성 요청 보내기
        $.ajax({
            type: "GET",
            url: "/submit-review",
            data: {reviewText: reviewText, imgSrc: imgSrc, cookieValue: getCookie('loggedIn')}, // 리뷰 텍스트와 이미지 경로 함께 전송
            dataType: "json",
            success: function (response) {
                console.log("++++++++++++++++++++++++++++")
                if (response.success) {
                    // 리뷰 작성이 성공한 경우
                    var newReview = $("<div class='re-box'>").text(response.reviewText);
                    $(".review-display").append(newReview); // 리뷰를 화면에 추가
                    $("textarea[name='reveiw']").val(""); // 리뷰 입력란 비우기
                } else {
                    // 리뷰 작성이 실패한 경우
                    console.log("리뷰 작성 실패")
                    alert("리뷰 작성에 실패했습니다.");
                }
            },
            error: function () {
                console.log()
                // Ajax 요청 실패 시 처리
                alert("리뷰작성중 서버와의 통신 중 오류가 발생했습니다.");

            },
        });
        // 버튼을 리뷰 삭제 버튼으로 변경
        $(this).text("리뷰 삭제").removeClass("review-write-button").addClass("delete-review-button");
        $(this).text("리뷰 작성").removeClass("delete-review-button").addClass("review-write-button");

        // 리뷰 작성 완료 후, 리뷰 삭제 버튼의 클릭 이벤트 핸들러를 변경
        $(this).off("click"); // 기존 클릭 이벤트 핸들러 제거
        $(this).click(function () {
            // 리뷰 삭제 코드 추가
            $.ajax({
                type: "DELETE",
                url: "/delete-review", // 서버의 리뷰 삭제 엔드포인트 설정
                data: {imgSrc: imgSrc}, // 삭제할 리뷰의 아이디를 서버에 전송
                dataType:"json",
                success: function (response) {
                    if (response.success) {
                        // 리뷰 삭제가 성공한 경우
                        // 화면에서 해당 리뷰를 제거합니다.
                        $(`[data-imgSrc=]`).closest(".review").remove();
                    } else {
                        // 리뷰 삭제가 실패한 경우
                        alert("리뷰 삭제에 실패했습니다.");
                    }
                },
                error: function () {
                    // Ajax 요청 실패 시 처리
                    alert("서버와의 통신 중 오류가 발생했습니다.");
                },
            });

        });
    } else if (loggedIn === null) {
        console.log('로그인되어 있지 않습니다.');
        alert('로그인을 해주세요.');
    }
})





