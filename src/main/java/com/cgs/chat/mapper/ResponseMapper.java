package com.cgs.chat.mapper;

import org.mapstruct.Mapper;

import com.cgs.chat.dto.RegistrationResponseDto;
import com.cgs.chat.dto.ResponseDto;

@Mapper(componentModel = "spring", uses = {})
public interface ResponseMapper {

	ResponseDto registrationToresponse(RegistrationResponseDto registrationResponseDto);
}
