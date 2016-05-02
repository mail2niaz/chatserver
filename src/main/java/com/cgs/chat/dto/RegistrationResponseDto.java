package com.cgs.chat.dto;

import java.util.List;

public class RegistrationResponseDto extends ResponseDto {

	private List<UserDto> userDtos;

	public List<UserDto> getUserDtos() {
		return userDtos;
	}

	public void setUserDtos(List<UserDto> userDtos) {
		this.userDtos = userDtos;
	}

}
