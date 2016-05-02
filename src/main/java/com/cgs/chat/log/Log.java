package com.cgs.chat.log;

public class Log {

	public static void d(String TAG, String message) {
		System.out.println("DEBUG:" + TAG + ":" + message);
	}

	public static void e(String TAG, String message) {
		System.out.println("ERROR:" + TAG + ":" + message);
	}

	public static void w(String TAG, String message) {
		System.out.println("WARN:" + TAG + ":" + message);
	}

	public static void i(String TAG, String message) {
		System.out.println("WARN:" + TAG + ":" + message);
	}
}