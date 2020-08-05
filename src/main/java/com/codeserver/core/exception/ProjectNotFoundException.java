package com.codeserver.core.exception;

/**
 * @author Bruno Okafor 2020-08-05
 */
public class ProjectNotFoundException extends RuntimeException {

	public ProjectNotFoundException(final String message) {
		super(message);
	}
}
