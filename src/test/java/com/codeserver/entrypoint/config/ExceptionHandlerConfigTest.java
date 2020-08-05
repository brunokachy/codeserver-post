package com.codeserver.entrypoint.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import javax.persistence.PersistenceException;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.codeserver.core.exception.ConflictException;
import com.codeserver.core.exception.ProjectNotFoundException;
import com.codeserver.core.exception.SdlcSystemNotFoundException;

/**
 * @author Bruno Okafor 2020-08-05
 */
class ExceptionHandlerConfigTest {

	private static final ExceptionHandlerConfig REST_EXCEPTION_HANDLER = new ExceptionHandlerConfig();

	private static final String ERROR = "Error";


	@Test
	void handleMessageProjectNotFoundException_whenProjectNotFoundException_throwsNotFound() {

		final ResponseEntity<Object> result = REST_EXCEPTION_HANDLER.handleMessageProjectNotFoundException(new ProjectNotFoundException(ERROR));

		assertNotNull(result);
		assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
	}

	@Test
	void handleMessageSDLCSystemNotFoundException_whenSdlcSystemNotFoundException_throwsNotFound() {

		final ResponseEntity<Object> result = REST_EXCEPTION_HANDLER.handleMessageSDLCSystemNotFoundException(new SdlcSystemNotFoundException(ERROR));

		assertNotNull(result);
		assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
	}

	@Test
	void handleMessageConflictException_whenConflictException_throwsNotFound() {

		final ResponseEntity<Object> result = REST_EXCEPTION_HANDLER.handleMessageConflictException(new ConflictException(ERROR));

		assertNotNull(result);
		assertEquals(HttpStatus.CONFLICT, result.getStatusCode());
	}

	@Test
	void handleMessagePersistenceException_whenPersistenceException_throwsNotFound() {

		final ResponseEntity<Object> result = REST_EXCEPTION_HANDLER.handleMessagePersistenceException(new PersistenceException(ERROR));

		assertNotNull(result);
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
	}

	@Test
	void handleMessageIllegalArgumentException_whenIllegalArgumentException_throwsNotFound() {

		final ResponseEntity<Object> result = REST_EXCEPTION_HANDLER.handleIllegalArgumentException(new IllegalArgumentException(ERROR));

		GatewayError gatewayError = (GatewayError) result.getBody();

		assertNotNull(result);
		assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
		assertEquals("ERROR", gatewayError.getStatus());
		assertNotNull(gatewayError.getTime());
		assertNotNull(gatewayError.getErrorMessage());
	}
}

