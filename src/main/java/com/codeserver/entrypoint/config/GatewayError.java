package com.codeserver.entrypoint.config;

import lombok.Value;

/**
 * @author Bruno Okafor 2020-08-05
 */
@Value(staticConstructor = "of")
class GatewayError {

	String errorMessage;

	String status;

	String time;
}
