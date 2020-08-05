package com.codeserver.entrypoint.controller;

import java.net.URI;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codeserver.core.dto.Project;
import com.codeserver.core.service.ProjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;

/**
 * @author Bruno Okafor 2020-08-05
 */
@RequiredArgsConstructor
@RestController
@RequestMapping(ProjectRestController.ENDPOINT)
@Api(produces = MediaType.APPLICATION_JSON_VALUE, tags = "ProjectEntity")
public class ProjectRestController {

	public static final String ENDPOINT = "/api/v2/projects";
	public static final String ENDPOINT_ID = "/{id}";

	public static final String PATH_VARIABLE_ID = "id";

	private static final String API_PARAM_ID = "ID";

	private final ProjectService projectService;

	@ApiOperation("Get a ProjectEntity")
	@GetMapping(ENDPOINT_ID)
	public Project getProject(
			@ApiParam(name = API_PARAM_ID, required = true)
			@PathVariable(PATH_VARIABLE_ID)
			@Positive final long projectId
	) {
		return projectService.getProject(projectId);
	}

	@ApiOperation("Create a ProjectEntity")
	@PostMapping
	public ResponseEntity<Project> createProject(@Valid @RequestBody Project project) {
		Project createdProject = projectService.createProject(project);
		URI location = URI.create(ENDPOINT + "/" + createdProject.getId());
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.setLocation(location);

		return new ResponseEntity<>(project, responseHeaders, HttpStatus.CREATED);
	}

	@ApiOperation("Update a ProjectEntity")
	@PatchMapping(ENDPOINT_ID)
	public ResponseEntity<Project> updateProject(
			@RequestBody Project project,
			@PathVariable(PATH_VARIABLE_ID)
			@Positive final long projectId
	) {

		Project updatedProject = projectService.updateProject(project, projectId);

		return new ResponseEntity<>(updatedProject, HttpStatus.OK);
	}

}
