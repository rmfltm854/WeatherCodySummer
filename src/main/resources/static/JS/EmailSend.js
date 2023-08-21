var number;
function sendEmail(){
    const email = document.getElementById("email").value; //사용자의 이메일 입력값.
    console.log(email);
    var msg = "<input type='text' placeholder='email인증번호' style='margin-top: 30px' oninput='maxLengthCheck(this)' maxlength='8'><h4 id='resultText'></h4>";
    document.getElementById('checkNum').innerHTML += msg;
    const target = document.getElementById('EmailNum');
    target.disabled = true;
    $.ajax({
        type: 'post',
        url: '/mail',
        async:false,
        data: {
            email:email
        },
        dataType: 'text',
        success:function(data) {
            // number = data.value; // data로 받아온 List를 변수에 담기
            number = data;
        }
    });
    alert('인증번호가 전송되었습니다.' + number);
    //mailSender();
}

function checkNum(emailNum){
    var element = document.getElementById("resultText");
    var msg;
    if(emailNum === number){
        console.log('일치');
        msg = "일치합니다.";
        element.style.color = "green";
        element.innerText = msg;

    }else {
        console.log('불일치');
        msg = "일치하지않습니다.";
        element.style.color = "red";
        element.innerText = msg;

    }
}
function maxLengthCheck(object) {
    if (object.value.length > object.maxLength) {
        object.value = object.value.slice(0, object.maxLength);
    }

    if(object.value.length === 8){
        console.log(object.value.length);
        console.log(object.value);
        const emailNum = object.value;
        checkNum(emailNum);
    }
}

