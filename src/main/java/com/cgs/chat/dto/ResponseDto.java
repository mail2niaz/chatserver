package com.cgs.chat.dto;

public class ResponseDto {

	private String status;
	private String type;
	private String message;
	private String errorCode;
	private String description;
	private String fromSessionId;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String errorDescription) {
		this.description = errorDescription;
	}

	public String getFromSessionId() {
		return fromSessionId;
	}

	public void setFromSessionId(String fromSessionId) {
		this.fromSessionId = fromSessionId;
	}

}
