package com.codeserver.entrypoint.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.codeserver.core.dto.Project;
import com.codeserver.core.dto.SdlcSystem;
import com.codeserver.core.service.ProjectService;

/**
 * @author Bruno Okafor 2020-08-05
 */
class ProjectRestControllerTest {

	@InjectMocks
	private ProjectRestController projectRestController;

	@Mock
	private ProjectService projectService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void testCreateProjectPost_withProjectRequestObject_returnsProjectResponse() {

		Project project = buildProject();

		when(projectService.createProject(project)).thenReturn(project);

		ResponseEntity<Project> responseEntity = projectRestController.createProject(project);

		assertNotNull(responseEntity);
		assertTrue(responseEntity.hasBody());
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertTrue(responseEntity.getHeaders().containsKey("location"));
	}

	@Test
	void testUpdateProjectPatch_withProjectIdAndUpdateFields_returnsProjectResponse() {

		Project project = buildProject();
		long projectId = 0L;

		when(projectService.updateProject(project, projectId)).thenReturn(project);

		ResponseEntity<Project> responseEntity = projectRestController.updateProject(project, projectId);

		assertNotNull(responseEntity);
		assertTrue(responseEntity.hasBody());
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}

	@Test
	void testGetProject_withProjectId_returnsProjectResponse() {

		Project project = buildProject();

		long projectId = 0L;

		when(projectService.getProject(projectId)).thenReturn(project);

		Project response = projectRestController.getProject(projectId);

		assertNotNull(response);
	}

	private static Project buildProject() {
		SdlcSystem sdlcSystem = new SdlcSystem();
		sdlcSystem.setId(2L);

		Project project = new Project();
		project.setExternalId("externalId");
		project.setName("name");
		project.setId(1L);
		project.setSdlcSystem(sdlcSystem);

		return project;
	}
}
