package com.pool.util;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.pool.form.response.CommonResponse;

@Component
public class UserProfileUtil {

	public CommonResponse generateCustomResponse(String message) {
		return new CommonResponse().setMessage(message).setTimeStamp(new Date());
	}

}
