package com.cgs.chat.dto;

public class MessageDto {

	private UserDto userDto;
	private String type;
	private String toType;
	private String toId;
	private String message;
	private String filename;

	public UserDto getUserDto() {
		return userDto;
	}

	public String getToType() {
		return toType;
	}

	public void setToType(String toType) {
		this.toType = toType;
	}

	public void setUserDto(UserDto userDto) {
		this.userDto = userDto;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTo() {
		return toType;
	}

	public void setTo(String to) {
		this.toType = to;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getToId() {
		return toId;
	}

	public void setToId(String toId) {
		this.toId = toId;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

}
