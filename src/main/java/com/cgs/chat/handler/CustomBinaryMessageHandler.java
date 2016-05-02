package com.cgs.chat.handler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.WebSocketSession;

import com.cgs.chat.constant.ChatConstant;
import com.cgs.chat.dto.FileDto;
import com.cgs.chat.dto.MessageDto;
import com.cgs.chat.dto.ResponseDto;
import com.cgs.chat.service.ChatService;

public class CustomBinaryMessageHandler extends MessageHandler {

	private MessageDto messageDto = null;
	private WebSocketSession session;
	List<BinaryMessage> binaryMessages;
	String filename;
	String toSessionId, fromSessionId;
	BinaryMessage message;
	byte[] consolidate = null;
	boolean isWriteFileIntoServerEnabled = false;

	CustomBinaryMessageHandler(WebSocketSession session, BinaryMessage binaryMessage, ChatService chatService,
			List<BinaryMessage> binaryMessages, FileDto fileDto) {
		super(session, binaryMessage, chatService);
		this.session = session;
		this.binaryMessages = binaryMessages;
		this.filename = fileDto.getFilename();
		this.toSessionId = fileDto.getToSessionid();
		this.fromSessionId = fileDto.getFromSessionid();
		this.message = binaryMessage;
	}

	public void handleMessage() {
		System.out.println("It is a binary message");
		binaryMessages.add(message);
		if (message.isLast()) {
			writeFile(filename, binaryMessages);

			BinaryMessage binaryMessage = new BinaryMessage(consolidate);
			ResponseDto responseDto = new ResponseDto();
			responseDto.setStatus(ChatConstant.ResponseValue.SUCCESS);
			responseDto.setType(ChatConstant.ChatResponseType.FILE_RECEIVED);
			responseDto.setDescription(ChatConstant.ChatResponseDescription.FILE_RECEIVED + " " + filename + "!");
			responseDto.setMessage(filename);
			responseDto.setFromSessionId(fromSessionId);

			if (toSessionId == null || toSessionId.equalsIgnoreCase("")) {
				// Send Public
				sendPublicMessage(responseDto);
				sendPublicBinaryMessage(binaryMessage);
			} else {
				// P2P
				sendPrivateMessage(responseDto, toSessionId);
				sendAnyPrivateBinaryMessage(binaryMessage, toSessionId);
			}
			// Send ACK
			responseDto = new ResponseDto();
			responseDto.setStatus(ChatConstant.ResponseValue.SUCCESS);
			responseDto.setType(ChatConstant.ChatResponseType.FILE_SENT);
			responseDto.setDescription(ChatConstant.ChatResponseDescription.FILE_SENT);
			responseDto.setMessage(filename);
			responseDto.setFromSessionId(fromSessionId);

			sendPrivateMessage(responseDto, session.getId());

			cleanup();
		}
	}

	private void cleanup() {
		System.out.println("calling cleanup....");
		binaryMessages.clear();
		binaryMessages = null;
		consolidate = null;
		binaryMessages = new ArrayList<BinaryMessage>();
		filename = null;
		toSessionId = null;

	}

	public File writeFile(String filename, List<BinaryMessage> binaryMessages) {
		File file = new File("C:\\Users\\niaz\\Desktop\\chat_files\\" + filename);
		System.out.println("before writeFile" + new Date());
		System.out.println("binaryMessages" + binaryMessages.size());

		try {

			boolean isFirst = true;
			for (BinaryMessage binaryMessage : binaryMessages) {
				if (isFirst) {
					isFirst = false;
					consolidate = binaryMessage.getPayload().array();
				} else {
					consolidate = convertToOne(consolidate, binaryMessage.getPayload().array());
				}
			}
			System.out.println("consolidate=" + consolidate.length);

			// write file into Server
			if (isWriteFileIntoServerEnabled) {
				FileOutputStream fos = new FileOutputStream(file);
				fos.write(consolidate);
				fos.flush();
				fos.close();
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("after writeFile" + new Date());
		return file;
	}

	public byte[] convertToOne(byte[] a, byte[] b) {
		byte[] c = new byte[a.length + b.length];
		System.arraycopy(a, 0, c, 0, a.length);
		System.arraycopy(b, 0, c, a.length, b.length);
		a = null;
		b = null;
		return c;
	}

	private byte[] convertFileToByteArray(File file) {
		System.out.println("before convertFileToByteArray" + new Date());
		byte[] bFile = new byte[(int) file.length()];

		try {
			// convert file into array of bytes
			FileInputStream fileInputStream = new FileInputStream(file);
			fileInputStream.read(bFile);
			fileInputStream.close();

			// for (int i = 0; i < bFile.length; i++) {
			// System.out.print((char) bFile[i]);
			// }

			System.out.println("after convertFileToByteArray" + new Date());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bFile;
	}

}
