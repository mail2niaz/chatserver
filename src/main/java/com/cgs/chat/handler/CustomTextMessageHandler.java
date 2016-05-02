package com.cgs.chat.handler;

import java.util.ArrayList;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.cgs.chat.constant.ChatConstant;
import com.cgs.chat.dto.FileDto;
import com.cgs.chat.dto.MessageDto;
import com.cgs.chat.dto.RegistrationResponseDto;
import com.cgs.chat.dto.ResponseDto;
import com.cgs.chat.dto.UserDto;
import com.cgs.chat.service.ChatService;

public class CustomTextMessageHandler extends MessageHandler {

	private MessageDto messageDto = null;
	private WebSocketSession session;
	// String filename, sessionId;
	FileDto fileDto;

	CustomTextMessageHandler(WebSocketSession session, TextMessage textMessage, ChatService chatService,
			FileDto fileDto) {
		super(session, textMessage, chatService);
		messageDto = getMessageDto();
		this.session = session;
		this.fileDto = fileDto;
		// this.filename = fileDto.getFilename();
	}

	public void handleMessage() {
		switch (messageDto.getType()) {
		case ChatConstant.ChatType.REGISTER:
			doRegister();
			break;
		case ChatConstant.ChatType.TEXT_MESSAGE:
			if (messageDto.getToType().equalsIgnoreCase(ChatConstant.ToType.PUBLIC)) {
				ResponseDto responseDto = new ResponseDto();
				responseDto.setStatus(ChatConstant.ResponseValue.SUCCESS);
				responseDto.setDescription(ChatConstant.ChatResponseDescription.TEXT_MESSAGE_NOTFICIATION
						+ messageDto.getUserDto().getUsername() + " !");
				responseDto.setType(ChatConstant.ToType.PUBLIC);
				responseDto.setFromSessionId(session.getId());
				responseDto.setMessage(messageDto.getMessage());
				sendPublicMessage(responseDto);
			}
			if (messageDto.getToType().equalsIgnoreCase(ChatConstant.ToType.PRIVATE)) {
				ResponseDto responseDto = new ResponseDto();
				responseDto.setStatus(ChatConstant.ResponseValue.SUCCESS);
				responseDto.setDescription(ChatConstant.ChatResponseDescription.TEXT_MESSAGE_NOTFICIATION
						+ messageDto.getUserDto().getUsername() + " !");
				responseDto.setType(ChatConstant.ToType.PRIVATE);
				responseDto.setFromSessionId(session.getId());
				responseDto.setMessage(messageDto.getMessage());
				sendPrivateMessage(responseDto, messageDto.getToId());
			}
			break;
		case ChatConstant.ChatType.BINARY_MESSAGE:
			initBinaryMessageHandler();
			break;
		default:
			break;
		}

	}

	private void initBinaryMessageHandler() {
		fileDto.setFilename(messageDto.getFilename());
		fileDto.setToSessionid(messageDto.getToId());
		fileDto.setFromSessionid(session.getId());
	}

	private void doRegister() {
		if (!chatService.isUserExist(session)) {
			// No nickname has been assigned by now
			// the first message is the nickname
			// escape the " character first
			// message = message.replace("\"", "\\\"");
			// Register the nickname with the
			UserDto userDto = messageDto.getUserDto();
			userDto.setSessionid(session.getId());
			chatService.nickNames.put(session, userDto);

			// broadcast all the nicknames to him
			RegistrationResponseDto registrationResponseDto = new RegistrationResponseDto();
			registrationResponseDto.setStatus(ChatConstant.ResponseValue.SUCCESS);
			registrationResponseDto.setType(ChatConstant.ChatResponseType.MY_REGISTER);
			registrationResponseDto.setDescription(ChatConstant.ChatResponseDescription.MY_REGISTER);
			ArrayList<UserDto> userDtosToSend = new ArrayList();
			ArrayList<UserDto> userDtosFull = new ArrayList(chatService.nickNames.values());
			System.out.println("userDtosFull="+ userDtosFull.size());
			System.out.println("userDtosFull="+ userDtosFull);
			for (UserDto dto : userDtosFull) {
				if (!dto.getSessionid().equalsIgnoreCase(session.getId())) {
					userDtosToSend.add(dto);
				}
			}
			registrationResponseDto.setUserDtos(userDtosToSend);
			sendPrivateMessage(registrationResponseDto, session.getId());

			// broadcast him to everyone now
			registrationResponseDto = new RegistrationResponseDto();
			registrationResponseDto.setStatus(ChatConstant.ResponseValue.SUCCESS);
			registrationResponseDto.setType(ChatConstant.ChatResponseType.NEW_REGISTRATION_NOTFICIATION);
			registrationResponseDto.setDescription(ChatConstant.ChatResponseDescription.NEW_REGISTRATION_NOTFICIATION);
			ArrayList<UserDto> userDtos = new ArrayList<>();
			userDtos.add(userDto);
			registrationResponseDto.setUserDtos(userDtos);
			sendPublicMessage(registrationResponseDto);
		}
	}
}
