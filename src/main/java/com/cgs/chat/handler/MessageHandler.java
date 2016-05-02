package com.cgs.chat.handler;

import java.io.IOException;
import java.util.Map;

import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.cgs.chat.dto.MessageDto;
import com.cgs.chat.dto.ResponseDto;
import com.cgs.chat.dto.UserDto;
import com.cgs.chat.service.ChatService;
import com.cgs.chat.util.ChatUtil;

public abstract class MessageHandler {

	ChatService chatService;
	private WebSocketSession session;
	private TextMessage textMessage;
	private BinaryMessage binaryMessage;

	MessageHandler(WebSocketSession session, TextMessage textMessage, ChatService chatService) {
		this.session = session;
		this.textMessage = textMessage;
		this.chatService = chatService;
	}

	MessageHandler(WebSocketSession session, BinaryMessage binaryMessage, ChatService chatService) {
		this.session = session;
		this.binaryMessage = binaryMessage;
		this.chatService = chatService;
	}

	public WebSocketSession getSession() {
		return session;
	}

	public MessageDto getMessageDto() {
		return ChatUtil.convertStringToMessageDto(textMessage.getPayload());
	}

	public void sendPrivateMessage(ResponseDto messageDto, String toId) {
		TextMessage messageToSend = new TextMessage(ChatUtil.convertPojoToString(messageDto));
		for (Map.Entry<WebSocketSession, UserDto> entry : chatService.nickNames.entrySet()) {
			WebSocketSession key = entry.getKey();
			UserDto value = entry.getValue();
			if (toId.equalsIgnoreCase(value.getSessionid())) {
				try {
					key.sendMessage(messageToSend);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

//	public void sendAnyPrivateMessage(Object object, String to) {
//		TextMessage messageToSend = new TextMessage(ChatUtil.convertPojoToString(object));
//		for (Map.Entry<WebSocketSession, UserDto> entry : chatService.nickNames.entrySet()) {
//			WebSocketSession key = entry.getKey();
//			UserDto value = entry.getValue();
//			if (to.equalsIgnoreCase(key.getId())) {
//				try {
//					key.sendMessage(messageToSend);
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//	}
	
	public void sendAnyPrivateBinaryMessage(BinaryMessage object, String to) {
		for (Map.Entry<WebSocketSession, UserDto> entry : chatService.nickNames.entrySet()) {
			WebSocketSession key = entry.getKey();
			UserDto value = entry.getValue();
			if (to.equalsIgnoreCase(key.getId())) {
				try {
					key.sendMessage(object);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void sendPublicMessage(ResponseDto object) {
		TextMessage messageToSend = new TextMessage(ChatUtil.convertPojoToString(object));
		for (WebSocketSession sock : chatService.conns) {
			if (sock.getId().equalsIgnoreCase(session.getId())) {
				continue;
			}
			try {
				sock.sendMessage(messageToSend);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void sendPublicBinaryMessage(BinaryMessage binaryMessage) {
		for (WebSocketSession sock : chatService.conns) {
			if (sock.getId().equalsIgnoreCase(session.getId())) {
				continue;
			}
			try {
				sock.sendMessage(binaryMessage);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public abstract void handleMessage();
}
