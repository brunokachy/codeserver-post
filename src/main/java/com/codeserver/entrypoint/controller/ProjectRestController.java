package com.codeserver.entrypoint.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codeserver.core.project.model.Project;
import com.codeserver.core.project.service.ProjectService;
import com.codeserver.entrypoint.controller.converter.ProjectConverter;
import com.codeserver.entrypoint.controller.dto.ProjectRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;

/**
 * @author Bruno Okafor 2020-07-27
 */
@RequiredArgsConstructor
@RestController
@RequestMapping(ProjectRestController.ENDPOINT)
@Api(produces = MediaType.APPLICATION_JSON_VALUE, tags = "Project")
public class ProjectRestController {

	public static final String ENDPOINT = "/api/v2/projects";
	public static final String ENDPOINT_ID = "/{id}";
	public static final String CREATE_PROJECT = "/create-project";

	public static final String PATH_VARIABLE_ID = "id";

	private static final String API_PARAM_ID = "ID";

	private final ProjectService projectService;

	@ApiOperation("Get a Project")
	@GetMapping(ENDPOINT_ID)
	public Project getProject(
			@ApiParam(name = API_PARAM_ID, required = true)
			@PathVariable(PATH_VARIABLE_ID) final String projectId
	) {
		return projectService.getProject(projectId);
	}

	@ApiOperation("Create a Project")
	@PostMapping(CREATE_PROJECT)
	public ResponseEntity<Project> createProject(@Valid @RequestBody ProjectRequest projectRequest) {

		Project project = ProjectConverter.toProject(projectRequest);

		project = projectService.createProject(project);

		return new ResponseEntity<>(project, HttpStatus.CREATED);
	}

}
