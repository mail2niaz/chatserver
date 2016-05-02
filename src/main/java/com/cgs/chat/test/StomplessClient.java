package com.cgs.chat.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.cgs.chat.constant.ChatConstant;
import com.cgs.chat.dto.MessageDto;
import com.cgs.chat.dto.RegistrationResponseDto;
import com.cgs.chat.dto.UserDto;
import com.cgs.chat.util.ChatUtil;
import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketExtension;
import com.neovisionaries.ws.client.WebSocketFactory;

/**
 * A sample WebSocket client application using nv-websocket-client library.
 *
 * <p>
 * This application connects to the echo server on websocket.org (
 * {@code ws://echo.websocket.org}) and repeats to (1) read a line from the
 * standard input, (2) send the read line to the server and (3) print the
 * response from the server, until {@code exit} is entered.
 * </p>
 *
 * @see <a href="https://github.com/TakahikoKawasaki/nv-websocket-client" >nv-
 *      websocket-client</a>
 *
 * @author Takahiko Kawasaki
 */
public class StomplessClient {
	/**
	 * The echo server on websocket.org.
	 */
	private final String SERVER = "ws://localhost:8080/as/java";

	/**
	 * The timeout value in milliseconds for socket connection.
	 */
	private final int TIMEOUT = 5000;

	/**
	 * The entry point of this command line application.
	 */
	private static int client_cnt = 0;
	private static int file_cnt = 0;

	WebSocket ws = null;

	List<UserDto> userDtos = null;
	UserDto myUserDto = null;

	public static void main(String[] args) throws Exception {
//		for (int i = 0; i < 10; i++) {
//			System.out.println("Java Client " + i + " starts here .....................");
			StomplessClient stomplessClient = new StomplessClient();
			stomplessClient.startTesting();
//		}
	}

	public void startTesting() throws Exception {
		ws = connect();
	}

	// public void doTesting() {
	// doRegister();
	// // Thread.sleep(4000);
	// sendPublicTextMessage();
	// sendPrivateTextMessage();
	// ws.disconnect();
	// }

	private void sendPrivateTextMessage() {
		String message = "Hi this is a Private message from " + myUserDto.getUsername();
		MessageDto messageDto = new MessageDto();
		messageDto.setType(ChatConstant.ChatType.TEXT_MESSAGE);
		messageDto.setToId(userDtos.get(getRandomUser()).getSessionid());
		messageDto.setToType(ChatConstant.ToType.PRIVATE);
		messageDto.setUserDto(myUserDto);
		messageDto.setMessage(message);
		String json = ChatUtil.convertPojoToString(messageDto);
		// System.out.println("About to send Private message::" + json);
		ws.sendText(json);

	}

	private void sendPublicTextMessage() {
		String message = "Hi this is a Public message from " + myUserDto.getUsername();
		MessageDto messageDto = new MessageDto();
		messageDto.setType(ChatConstant.ChatType.TEXT_MESSAGE);
		messageDto.setToType(ChatConstant.ToType.PUBLIC);
		messageDto.setUserDto(myUserDto);
		messageDto.setMessage(message);
		String json = ChatUtil.convertPojoToString(messageDto);
		ws.sendText(json);

	}

	private void sendPublicBinaryMessage() {
		MessageDto messageDto = new MessageDto();
		messageDto.setType(ChatConstant.ChatType.BINARY_MESSAGE);
		messageDto.setToType(ChatConstant.ToType.PUBLIC);
		messageDto.setUserDto(myUserDto);
		messageDto.setFilename("public_file_" + ++file_cnt + ".pdf");
		String json = ChatUtil.convertPojoToString(messageDto);
		ws.sendText(json);
		sendPublicBinaryFile();
	}

	private void sendPrivateBinaryMessage() {
		MessageDto messageDto = new MessageDto();
		messageDto.setType(ChatConstant.ChatType.BINARY_MESSAGE);
		messageDto.setToType(ChatConstant.ToType.PRIVATE);
		messageDto.setToId(userDtos.get(getRandomUser()).getSessionid());
		messageDto.setUserDto(myUserDto);
		messageDto.setFilename("private_file_" + ++file_cnt + ".mp4");
		String json = ChatUtil.convertPojoToString(messageDto);
		ws.sendText(json);
		sendPrivateBinaryFile();
	}

	private void sendPublicBinaryFile() {
		try {
			String path = "C:Users\\niaz\\Desktop\\Temp\\Desktop2016-04-20\\TNSTC_13.pdf";
			// String path =
			// "C:\\Users\\niaz\\Downloads\\working3-simple-web-chat.zip";

			// saveInLocal(path);
			// byte[] imgInBytes = extractBytes(path);
			Path paths = Paths.get(path);

			byte[] data = Files.readAllBytes(paths);
			ws.connectAsynchronously();
			// ws.sendBinary(data);

			int chunksize = 1000;
			int start = 0;
			int i = 0;
			boolean isFirst = true;
			while (true) {

				int end = Math.min(data.length, start + chunksize);
				if (start > data.length) {
					byte[] emptyArray = new byte[0];
					// System.out.println("last time" + i);
					ws.sendContinuation(emptyArray, true);
					break;
				}

				if (isFirst) {
					isFirst = false;
					// System.out.println("First time" + i);
					ws.sendBinary(Arrays.copyOfRange(data, start, end), false);
				} else {
					// result.add(Arrays.copyOfRange(source, start, end));
					// System.out.println("middle" + i);
					ws.sendContinuation(Arrays.copyOfRange(data, start, end));
				}
				start += chunksize;
				i++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void sendPrivateBinaryFile() {
		try {
			String path = "C:\\Users\\niaz\\Downloads\\spacetestSMALL_512kb.mp4";

			// saveInLocal(path);
			// byte[] imgInBytes = extractBytes(path);
			Path paths = Paths.get(path);

			byte[] data = Files.readAllBytes(paths);
			ws.connectAsynchronously();
			// ws.sendBinary(data);

			int chunksize = 1000;
			int start = 0;
			int i = 0;
			boolean isFirst = true;
			while (true) {

				int end = Math.min(data.length, start + chunksize);
				if (start > data.length) {
					byte[] emptyArray = new byte[0];
					// System.out.println("last time" + i);
					ws.sendContinuation(emptyArray, true);
					break;
				}

				if (isFirst) {
					isFirst = false;
					// System.out.println("First time" + i);
					ws.sendBinary(Arrays.copyOfRange(data, start, end), false);
				} else {
					// result.add(Arrays.copyOfRange(source, start, end));
					// System.out.println("middle" + i);
					ws.sendContinuation(Arrays.copyOfRange(data, start, end));
				}
				start += chunksize;
				i++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void doRegister() {
		myUserDto = new UserDto();
		myUserDto.setUsername("java_client" + ++client_cnt);
		MessageDto messageDto = new MessageDto();
		messageDto.setType(ChatConstant.ChatType.REGISTER);
		messageDto.setUserDto(myUserDto);
		String json = ChatUtil.convertPojoToString(messageDto);
		// System.out.println("REGISTER=" + json);
		ws.sendText(json);
	}

	/**
	 * Connect to the server.
	 */
	private WebSocket connect() throws Exception {

		return new WebSocketFactory().setConnectionTimeout(TIMEOUT).createSocket(SERVER)
				.addListener(new WebSocketAdapter() {
					// A text message arrived from the server.
					public void onTextMessage(WebSocket websocket, String message) {
						System.out.println(message);
						handleResponseFromServer(message);
					}

					@Override
					public void onBinaryMessage(WebSocket websocket, byte[] binary) throws Exception {
						// TODO Auto-generated method stub
						super.onBinaryMessage(websocket, binary);
						// System.out.println("size=" + binary.length);
					}

					@Override
					public void onConnected(WebSocket websocket, Map<String, List<String>> headers) throws Exception {
						// TODO Auto-generated method stub
						super.onConnected(websocket, headers);
						doRegister();
					}
				}).addExtension(WebSocketExtension.PERMESSAGE_DEFLATE).connect();
	}

	protected void handleResponseFromServer(String message) {
		RegistrationResponseDto responseDto = ChatUtil.convertStringToResponseDto(message);
		System.out.println(responseDto.getDescription());
		switch (responseDto.getType()) {
		case ChatConstant.ChatResponseType.MY_REGISTER:
			RegistrationResponseDto registrationResponseDto = ChatUtil.convertStringToRegistrationResponseDto(message);
			userDtos = registrationResponseDto.getUserDtos();
			sendPublicTextMessage();
			sendPrivateTextMessage();
			sendPublicBinaryMessage();
			sendPrivateBinaryMessage();
			break;

		default:
			break;
		}

	}

	/**
	 * Wrap the standard input with BufferedReader.
	 */
	private BufferedReader getInput() throws IOException {
		return new BufferedReader(new InputStreamReader(System.in));
	}

	private int getRandomUser() {
		Random r = new Random();
		int Low = 0;
		int High = userDtos.size();
		int Result = r.nextInt(High - Low) + Low;
		return Result;
	}
}
