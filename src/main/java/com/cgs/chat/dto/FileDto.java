package com.cgs.chat.dto;

public class FileDto {

	private String filename;
	private String toSessionid;
	private String fromSessionid;

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getToSessionid() {
		return toSessionid;
	}

	public void setToSessionid(String sessionid) {
		this.toSessionid = sessionid;
	}

	public String getFromSessionid() {
		return fromSessionid;
	}

	public void setFromSessionid(String fromSessionid) {
		this.fromSessionid = fromSessionid;
	}

}
