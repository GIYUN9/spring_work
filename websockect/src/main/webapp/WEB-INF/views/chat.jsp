<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
    <h1>채팅</h1>

    <input type="text" name="msg">
    <button onclick="sendMsg();">전송</button>
	
	<br>
	<div id="msg-container"></div>
    <script>
        //socket연결 요청
        const socket = new WebSocket("ws://localhost:8010/socket/server");

        //socket연결 성공시
        socket.onopen = function(){
            console.log("웹소켓 연결ok...");
        }

        //socket연결 끊어졌을 시
        socket.onclose = function(){
            console.log("웹소켓 끊어짐...");
        }

        //socket연결 실패시
        socket.onerror = function(){
            console.log("웹소켓 연결 실패...");
            alert("웹소켓 연결 실패");
        }

        //socket연결로 부터 데이터가 도착했을때
        //서버로부터 데이터가 도착했을 때
        socket.onmessage = function(ev){
            console.log(ev.data);
            const msgContainer = document.querySelector("#msg-container");
            msgContainer.innerHTML += (ev.data + "<br>")
        }

        function sendMsg() {
            const msgInput = document.querySelector("input[name=msg]");
			
            const str = msgInput.value;
            //연결된 socket session에 데이터 전송
            socket.send(str);

            msgInput.value = "";
		}
    </script>
</body>
</html>