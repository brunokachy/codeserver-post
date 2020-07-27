package com.codeserver.entrypoint.controller.dto;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Value;

/**
 * @author Bruno Okafor 2020-07-27
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Value(staticConstructor = "of")
public class ProjectRequest {

	@NotBlank(message = "SDLC System Id cannot be null or empty")
	String sdlcSystemId;

	@NotBlank(message = "Project name cannot be null or empty")
	String name;
}
