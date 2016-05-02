package com.cgs.chat.handler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.BinaryWebSocketHandler;

import com.cgs.chat.dto.FileDto;
import com.cgs.chat.service.ChatService;

public class ChatHandler extends BinaryWebSocketHandler {

	@Autowired
	private ChatService chatService;

	FileDto fileDto = new FileDto();

	List<BinaryMessage> binaryMessages = new ArrayList<BinaryMessage>();
	String filename = null;
	String sessionId = null;

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		System.out.println("New connection");
		chatService.registerOpenConnection(session);
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		System.out.println("CloseStatus=" + status.getReason());
		chatService.registerCloseConnection(session);

	}

	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		System.out.println("ERROR:::handleTransportError........................");
		chatService.registerCloseConnection(session);
		exception.printStackTrace();
	}

	@Override
	public boolean supportsPartialMessages() {
		return true;
	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) {
		System.out.println("New message: " + message.getPayload());

		CustomTextMessageHandler customTextMessageHandler = new CustomTextMessageHandler(session, message, chatService,
				fileDto);
		customTextMessageHandler.handleMessage();
	}

	@Override
	protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) throws Exception {
		// // TODO Auto-generated method stub
		super.handleBinaryMessage(session, message);
		CustomBinaryMessageHandler customBinaryMessageHandler = new CustomBinaryMessageHandler(session, message,
				chatService, binaryMessages, fileDto);
		customBinaryMessageHandler.handleMessage();

	}

}
