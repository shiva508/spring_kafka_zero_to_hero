package com.pool.config.exception;

import java.util.Date;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.pool.form.exception.CustomErrorResponse;
import com.pool.form.exception.NoRecardsFoundException;

@RestControllerAdvice
public class UserProfileGlobalExceptionHandler {

	@ExceptionHandler(value = NoRecardsFoundException.class)
	public ResponseEntity<CustomErrorResponse> handleGenericNotFoundException(NoRecardsFoundException error){
		return new ResponseEntity<CustomErrorResponse>(new CustomErrorResponse()
															.setErrorCode(HttpStatus.NOT_FOUND.toString())
															.setErrorMsg(error.getMessage())
															.setStatus((HttpStatus.NOT_FOUND.value()))
															.setTimestamp(new Date()), HttpStatus.NOT_FOUND);
	}
}
