package com.codeserver.core.dto;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * @author Bruno Okafor 2020-08-05
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SdlcSystem {

	@NotBlank(message = "SDLC System Id cannot be null or empty")
	private long id;

	private String baseUrl;

	private String description;

}
