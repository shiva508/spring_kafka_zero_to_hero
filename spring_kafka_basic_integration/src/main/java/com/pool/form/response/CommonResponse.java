package com.pool.form.response;

import java.util.Date;

public class CommonResponse {
	private String message;
	private Date timeStamp;

	public CommonResponse() {

	}

	public String getMessage() {
		return message;
	}

	public CommonResponse setMessage(String message) {
		this.message = message;
		return this;
	}

	public Date getTimeStamp() {
		return timeStamp;
	}

	public CommonResponse setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
		return this;
	}

}
