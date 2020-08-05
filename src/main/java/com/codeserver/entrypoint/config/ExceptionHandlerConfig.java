package com.codeserver.entrypoint.config;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.PersistenceException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.codeserver.core.exception.ConflictException;
import com.codeserver.core.exception.ProjectNotFoundException;
import com.codeserver.core.exception.SdlcSystemNotFoundException;
import lombok.extern.log4j.Log4j2;

/**
 * @author Bruno Okafor 2020-08-05
 */
@ControllerAdvice
@Log4j2
public class ExceptionHandlerConfig extends ResponseEntityExceptionHandler {

	private final DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'hh:mm:ss");

	@ExceptionHandler({ProjectNotFoundException.class})
	public ResponseEntity<Object> handleMessageProjectNotFoundException(final ProjectNotFoundException ex) {

		return buildResponseEntity(
				createErrorResponse(
						ex.getMessage(),
						ex
				),
				HttpStatus.NOT_FOUND
		);
	}

	@ExceptionHandler({SdlcSystemNotFoundException.class})
	public ResponseEntity<Object> handleMessageSDLCSystemNotFoundException(final SdlcSystemNotFoundException ex) {

		return buildResponseEntity(
				createErrorResponse(
						ex.getMessage(),
						ex
				),
				HttpStatus.NOT_FOUND
		);
	}

	@ExceptionHandler({ConflictException.class})
	public ResponseEntity<Object> handleMessageConflictException(final ConflictException ex) {

		return buildResponseEntity(
				createErrorResponse(
						ex.getMessage(),
						ex
				),
				HttpStatus.CONFLICT
		);
	}

	@ExceptionHandler({PersistenceException.class})
	public ResponseEntity<Object> handleMessagePersistenceException(final PersistenceException ex) {

		return buildResponseEntity(
				createErrorResponse(
						ex.getMessage(),
						ex
				),
				HttpStatus.INTERNAL_SERVER_ERROR
		);
	}

	@ExceptionHandler(value = {IllegalArgumentException.class})
	ResponseEntity<Object> handleIllegalArgumentException(final Exception ex) {

		return buildResponseEntity(createErrorResponse(
				ex.getMessage(),
				ex
		), HttpStatus.BAD_REQUEST);
	}

	private static ResponseEntity<Object> buildResponseEntity(final Object response, final HttpStatus httpStatus) {
		return new ResponseEntity<>(response, httpStatus);
	}

	private GatewayError createErrorResponse(final String message, final Exception ex) {

		log.warn(
				"An exception {} has occurred",
				ex.getClass().getSimpleName(),
				ex
		);

		return GatewayError.of(message, "ERROR", LocalDateTime.now().format(format));
	}

}
