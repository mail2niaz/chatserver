package com.cgs.chat.util;

import java.io.IOException;

import com.cgs.chat.dto.MessageDto;
import com.cgs.chat.dto.RegistrationResponseDto;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ChatUtil {

	public static String convertPojoToString(Object object) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			// if (object instanceof MessageDto) {
			// return mapper.writeValueAsString(object);
			// }
			// if (object instanceof RegistrationResponseDto) {
			// return mapper.writeValueAsString(object);
			// }
			return mapper.writeValueAsString(object);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static MessageDto convertStringToMessageDto(String json) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			return mapper.readValue(json, MessageDto.class);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}

	public static RegistrationResponseDto convertStringToResponseDto(String json) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			return mapper.readValue(json, RegistrationResponseDto.class);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}

	public static RegistrationResponseDto convertStringToRegistrationResponseDto(String json) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			return mapper.readValue(json, RegistrationResponseDto.class);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}

}
