

var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    $("#send").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#msg").html("");
}
window.onload = function (){
    connect();
}


function connect() {
    var socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/public', function (message) {
            const resultM = message.body.split(",");
            resultM[0] = resultM[0].replace("[","");
            resultM[1] = resultM[1].replace("]","");
            console.log(resultM[1]);
            RespondMessage(resultM); //서버에 메시지 전달 후 리턴받는 메시지
            console.log(resultM[1]);
            // showMessage("받은 메시지::: " + message.body); //서버에 메시지 전달 후 리턴받는 메시지

        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendMessage() {
    message = $("#message").val();
    console.log(message);
    showMessage(message);
    stompClient.send("/app/sendMessage", {}, JSON.stringify(message)); //서버에 보낼 메시지
    //document.getElementById("message").value = null;
}
function showMessage(message) {
    $("#chat-messages").append("<div class='chat ch2'> <div class='icon'><i class='fa-solid fa-user'></i></div> <div class='textbox'>" + message+ "</div></div>");
    console.log("showmessage" + message);
}
function RespondMessage(message) {
    if(message[1].indexOf("null") == -1){
        $("#chat-messages").append("<div class='chat ch1'> <div class='icon'><i class='fa-solid fa-user'></i></div> <div class='textbox'>"+ message[0] +"</div></div>");
        $("#chat-messages").append("<div class='chat ch1'><div class='icon'><i class='fa-solid fa-user'></i></div><div class='textbox'><a href=" + message[1] + ">"+message[1]+"</a></div>");
        console.log("if");
    }else {
        $("#chat-messages").append("<div class='chat ch1'> <div class='icon'><i class='fa-solid fa-user'></i></div> <div class='textbox'>"+ message[0] +"</div></div>");
        console.log("else");
    }
    $("#chatForm")[0].reset();
}

$(function() {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendMessage(); });
});