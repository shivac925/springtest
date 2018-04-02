package com.spring.controller;

import java.sql.SQLException;
import java.util.Properties;

import javax.annotation.Resource;

import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.GenericJDBCException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.spring.model.EndResult;
import com.spring.model.ErrorResponse;

@ControllerAdvice
public class GlobalExceptionController extends ResponseEntityExceptionHandler {

	EndResult endResult;

	@Resource(name = "errorCodes")
	private Properties errorCodes;

	private final static Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionController.class);

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> exceptionHandler(Exception ex) {
		LOGGER.error("Exception: " + ex);
		ErrorResponse error = new ErrorResponse();
		error.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		error.setMessage("Please contact your administrator");
		return new ResponseEntity<ErrorResponse>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler({ AccessDeniedException.class })
	public ResponseEntity<Object> handleAccessDeniedException(Exception ex, WebRequest request) {
		return new ResponseEntity<Object>("Access denied message here", new HttpHeaders(), HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler(value = { IllegalArgumentException.class, IllegalStateException.class })
	protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
		String bodyOfResponse = "This should be application specific";
		return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.CONFLICT, request);
	}

	@ExceptionHandler(DataAccessException.class)
	public ResponseEntity<EndResult> dataAccessException(DataAccessException ex) {
		ResponseEntity<EndResult> resp;
		endResult = new EndResult();
		LOGGER.error("DataAccessException: " + ex);
		Throwable t = ex.getCause();
		if ((t != null) && (t instanceof ConstraintViolationException)) {
			ConstraintViolationException cve = (ConstraintViolationException) t;
			LOGGER.error("ConstraintViolationException: " + cve.getConstraintName());
			if (cve != null && cve.getConstraintName().equalsIgnoreCase("SHIVA.DUMMY_USER_PK")) {
				endResult.setData(null);
				endResult.setMessage("Data already exists");
				endResult.setStatus(errorCodes.getProperty("SHIVA.DUMMY_USER_PK"));
			}
			resp = new ResponseEntity<EndResult>(endResult, HttpStatus.CONFLICT);
			return resp;
		} else if ((t != null) && (t instanceof GenericJDBCException)) {
			GenericJDBCException gJdbcExp = (GenericJDBCException) t;
			SQLException sqlExp = gJdbcExp.getSQLException();
			LOGGER.error("SQLException getMessage: " + sqlExp.getMessage());
			endResult.setData(null);
			endResult.setMessage(sqlExp.getMessage());
			endResult.setStatus("fail");
		}
		return new ResponseEntity<EndResult>(endResult, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
