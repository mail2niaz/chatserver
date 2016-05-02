package com.cgs.chat.service;

import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.cgs.chat.dto.UserDto;

@Service
public class ChatService {

	public Set<WebSocketSession> conns = java.util.Collections.synchronizedSet(new HashSet<WebSocketSession>());
	public Map<WebSocketSession, UserDto> nickNames = new ConcurrentHashMap<WebSocketSession, UserDto>();

	public void registerOpenConnection(WebSocketSession session) {
		conns.add(session);
	}

	public void registerCloseConnection(WebSocketSession session) {
		UserDto userDto = nickNames.get(session);
		conns.remove(session);
		nickNames.remove(session);
		if (userDto != null) {
			String messageToSend = "{\"removeUser\":\"" + userDto.getUsername() + "\"}";
			for (WebSocketSession sock : conns) {
				try {
					sock.sendMessage(new TextMessage(messageToSend));
				} catch (IOException e) {
					System.out.println("IO exception when sending remove user message");
				}
			}
		}
	}

	public void processMessage(WebSocketSession session, String message) {
		// if (!nickNames.containsKey(session)) {
		// // No nickname has been assigned by now
		// // the first message is the nickname
		// // escape the " character first
		// message = message.replace("\"", "\\\"");
		//
		// // broadcast all the nicknames to him
		// for (String nick : nickNames.values()) {
		// try {
		// session.sendMessage(new TextMessage("{\"addUser\":\"" + nick +
		// "\"}"));
		// } catch (IOException e) {
		// System.out.println("Error when sending addUser message");
		// }
		// }
		//
		// // Register the nickname with the
		// nickNames.put(session, message);
		//
		// // broadcast him to everyone now
		// String messageToSend = "{\"addUser\":\"" + message + "\"}";
		// for (WebSocketSession sock : conns) {
		// try {
		// sock.sendMessage(new TextMessage(messageToSend));
		// } catch (IOException e) {
		// System.out.println("Error when sending broadcast addUser message");
		// }
		// }
		// } else {
		// // Broadcast the message
		// String receiver_message = message;
		// System.out.println("session url=" + receiver_message);
		// String receiver = (receiver_message.split("~")[0]).trim();
		// String message1 = (receiver_message.split("~")[1]).trim();
		//
		// String messageToSend = "{\"nickname\":\"" + nickNames.get(session) +
		// "\", \"message\":\""
		// + message1.replace("\"", "\\\"") + "\"}";
		// String toCompare = nickNames.get(session);
		// for (WebSocketSession sock : conns) {
		// try {
		// System.out.println("nickNames.get(sock)=" + nickNames.get(sock));
		// System.out.println("receiver=" + receiver);
		// if (!nickNames.get(sock).equalsIgnoreCase(receiver))
		// sock.sendMessage(new TextMessage(messageToSend));
		// } catch (IOException e) {
		// System.out.println("Error when sending message message");
		// }
		// }
	}

	public UserDto getUserDto(WebSocketSession session) {
		return nickNames.get(session);
	}

	public boolean isUserExist(WebSocketSession session) {
		if (nickNames.containsKey(session)) {
			return true;
		} else {
			return false;
		}
	}

}
