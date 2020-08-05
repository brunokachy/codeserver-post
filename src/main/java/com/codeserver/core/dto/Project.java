package com.codeserver.core.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

/**
 * @author Bruno Okafor 2020-08-05
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"externalId", "name", "sdlcSystem"})
@Data
public class Project {

	@JsonIgnore
	private long id;

	private String name;

	@NotBlank(message = "Project Entity External Id cannot be null or empty")
	private String externalId;

	@NotNull
	private SdlcSystem sdlcSystem;
}
