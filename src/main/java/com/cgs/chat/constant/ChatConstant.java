package com.cgs.chat.constant;

public interface ChatConstant {

	interface ChatType {
		public static final String REGISTER = "REGISTER";
		public static final String TEXT_MESSAGE = "TEXT_MESSAGE";
		public static final String BINARY_MESSAGE = "BINARY_MESSAGE";
	}

	interface ToType {
		public static final String PRIVATE = "PRIVATE";
		public static final String PUBLIC = "PUBLIC";
		public static final String GROUP = "GROUP";
	}

	interface ResponseValue {
		public static final String SUCCESS = "SUCCESS";
		public static final String ERROR = "ERROR";

	}

	interface ChatResponseDescription {
		public static final String MY_REGISTER = "Your Account registered Successfully !";
		public static final String NEW_REGISTRATION_NOTFICIATION = "New User logged in into the Chat room !";
		public static final String FILE_RECEIVED = "New File received from ";
		public static final String FILE_SENT = "Your file has been sent successfully !";
		public static final String TEXT_MESSAGE_NOTFICIATION = "Your received a message from ";
	}
	
	interface ChatResponseType {
		public static final String MY_REGISTER = "REGISTER_SUCCESSFULLY";
		public static final String NEW_REGISTRATION_NOTFICIATION = "NEW_USER_NOTIFY";
		public static final String FILE_RECEIVED = "FILE_RECEIVED";
		public static final String FILE_SENT = "FILE_SENT";
	}
}
