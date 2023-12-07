package com.kh.socket;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component("chatSocketServer")
//public class ChatSocketServer implements WebSocketHandler
public class ChatSocketServer extends TextWebSocketHandler{
	private final Set<WebSocketSession> sessionSet = new HashSet();
	
	//연결되었을 때
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		String nick = (String)session.getAttributes().get("nick");
		log.info("{} 연결됨", nick);
		sessionSet.add(session);
	}

	//연결이 끊어졌을 때
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		String nick = (String)session.getAttributes().get("nick");
		log.info("{} 연결끊킴", nick);
		sessionSet.remove(session);
	}
	
	//메세지를 수신할 때
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		//수신된 메세지 모든 세션에 전달
		
		String sender = (String)session.getAttributes().get("nick");
		String msg = message.getPayload(); //chat.jsp에서 send(str)로 넘겨준 데이터 받아줌 json형태로
		
		TextMessage textMsg = new TextMessage(sender + " : " + msg);
		
		for (WebSocketSession s : sessionSet) {
			s.sendMessage(textMsg);
		}
		
	}
	
}
