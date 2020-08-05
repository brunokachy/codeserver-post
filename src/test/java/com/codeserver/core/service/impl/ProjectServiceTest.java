package com.codeserver.core.service.impl;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.codeserver.core.dto.Project;
import com.codeserver.core.dto.SdlcSystem;
import com.codeserver.core.exception.ConflictException;
import com.codeserver.core.exception.ProjectNotFoundException;
import com.codeserver.core.exception.SdlcSystemNotFoundException;
import com.codeserver.core.service.ProjectPersistenceService;
import com.codeserver.core.service.SdlcSystemPersistenceService;

/**
 * @author Bruno Okafor2020-08-05
 */
class ProjectServiceTest {

	@InjectMocks
	private ProjectServiceImpl projectService;

	@Mock
	private ProjectPersistenceService projectPersistenceService;

	@Mock
	private SdlcSystemPersistenceService sdlcSystemPersistenceService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void testGetProject_withValidProjectId_returnsProject() {

		Project project = buildProject();

		long projectId = 1;

		when(projectPersistenceService.findProjectByProjectId(projectId)).thenReturn(Optional.of(project));

		Project response = projectService.getProject(projectId);

		verify(this.projectPersistenceService, times(1)).findProjectByProjectId((projectId));

		assertNotNull(response);
		assertEquals(projectId, response.getId());
		assertEquals(project.getSdlcSystem().getId(), response.getSdlcSystem().getId());
		assertEquals(project.getExternalId(), response.getExternalId());
		assertEquals(project.getName(), response.getName());
	}

	@Test
	void testGetProject_withInValidProjectId_throwProjectNotFoundException() {

		long projectId = 1;

		when(projectPersistenceService.findProjectByProjectId(projectId)).thenReturn(Optional.empty());

		assertThatExceptionOfType(ProjectNotFoundException.class).isThrownBy(() ->
				this.projectService.getProject(projectId))
				.withMessageContaining("Could not find project with id " + projectId);
	}

	@Test
	void testCreateProject_withNonExistingSDLCSystemId_throwSdlcSystemNotFoundException() {

		Project project = buildProject();

		long sdlcSystemId = project.getSdlcSystem().getId();

		when(sdlcSystemPersistenceService.doesSDLCSystemExist(sdlcSystemId)).thenReturn(false);

		assertThatExceptionOfType(SdlcSystemNotFoundException.class).isThrownBy(() ->
				this.projectService.createProject(project))
				.withMessageContaining("Could not find SDLC System with id " + sdlcSystemId);
	}

	@Test
	void testCreateProject_withExistingSDLCSystemIdAndExternalId_throwConflictExceptionException() {

		Project project = buildProject();

		long sdlcSystemId = project.getSdlcSystem().getId();

		String externalId = project.getExternalId();

		when(sdlcSystemPersistenceService.doesSDLCSystemExist(sdlcSystemId)).thenReturn(true);
		when(projectPersistenceService.doesProjectExist(externalId, sdlcSystemId)).thenReturn(true);

		assertThatExceptionOfType(ConflictException.class).isThrownBy(() ->
				this.projectService.createProject(project))
				.withMessageContaining("A project with this external id " + externalId + " already exist in SDLC System of id " + sdlcSystemId);
	}

	@Test
	void testCreateProject_withValidProjectData_returnNewProject() {

		Project project = buildProject();

		long sdlcSystemId = project.getSdlcSystem().getId();
		String externalId = project.getExternalId();

		when(sdlcSystemPersistenceService.doesSDLCSystemExist(sdlcSystemId)).thenReturn(true);
		when(projectPersistenceService.doesProjectExist(externalId, sdlcSystemId)).thenReturn(false);
		when(projectPersistenceService.createProject(project)).thenReturn(project);

		Project response = projectService.createProject(project);

		verify(this.projectPersistenceService, times(1)).createProject((project));

		assertNotNull(response);
		assertEquals(project.getId(), response.getId());
		assertEquals(sdlcSystemId, response.getSdlcSystem().getId());
		assertEquals(externalId, response.getExternalId());
		assertEquals(project.getName(), response.getName());
		assertEquals(project.getSdlcSystem().getDescription(), response.getSdlcSystem().getDescription());
		assertEquals(project.getSdlcSystem().getBaseUrl(), response.getSdlcSystem().getBaseUrl());
	}

	private static Project buildProject() {
		SdlcSystem sdlcSystem = new SdlcSystem();
		sdlcSystem.setId(2);
		sdlcSystem.setBaseUrl("url");
		sdlcSystem.setDescription("description");

		Project project = new Project();
		project.setExternalId("externalId");
		project.setName("name");
		project.setId(1);
		project.setSdlcSystem(sdlcSystem);

		return project;
	}

}
