package com.codeserver.infrastructure.persistence.service;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import javax.persistence.PersistenceException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.codeserver.core.dto.Project;
import com.codeserver.core.dto.SdlcSystem;
import com.codeserver.infrastructure.persistence.entity.ProjectEntity;
import com.codeserver.infrastructure.persistence.entity.SdlcSystemEntity;
import com.codeserver.infrastructure.persistence.repository.ProjectRepository;
import com.codeserver.infrastructure.persistence.repository.SdlcSystemRepository;

/**
 * @author Bruno Okafor 2020-08-05
 */
class ProjectPersistenceServiceTest {

	@InjectMocks
	private ProjectPersistenceServiceImpl projectPersistenceService;

	@Mock
	private SdlcSystemRepository sdlcSystemRepository;

	@Mock
	private ProjectRepository projectRepository;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void testFindProjectByProjectId_withInvalidProjectId_returnsEmptyProject() {

		long projectId = 0L;

		when(projectRepository.findById(projectId)).thenReturn(Optional.empty());

		Optional<Project> response = projectPersistenceService.findProjectByProjectId(projectId);

		verify(this.projectRepository, times(1)).findById((projectId));

		assertNotNull(response);
		assertFalse(response.isPresent());
	}

	@Test
	void testFindProjectByProjectId_withValidProjectId_returnsProject() {

		ProjectEntity projectEntity = buildProjectEntity();

		long projectId = 0L;

		when(projectRepository.findById(projectId)).thenReturn(Optional.of(projectEntity));

		Optional<Project> response = projectPersistenceService.findProjectByProjectId(projectId);

		verify(this.projectRepository, times(1)).findById((projectId));

		assertNotNull(response);
		assertTrue(response.isPresent());
	}

	@Test
	void testCreateProject_withInValidSDLCSystem__throwPersistenceException() {

		ProjectEntity projectEntity = buildProjectEntity();

		Project project = buildProject();

		when(sdlcSystemRepository.findById(project.getSdlcSystem().getId())).thenReturn(Optional.empty());
		when(projectRepository.save(projectEntity)).thenReturn(projectEntity);

		assertThatExceptionOfType(PersistenceException.class).isThrownBy(() ->
				this.projectPersistenceService.createProject(project))
				.withMessageContaining("Error creating ProjectEntity record");
	}

	@Test
	void testCreateProject_withValidProject_createProjectEntity() {

		ProjectEntity projectEntity = buildProjectEntity();

		Project project = buildProject();

		when(sdlcSystemRepository.findById(project.getSdlcSystem().getId())).thenReturn(Optional.of(projectEntity.getSdlcSystemEntity()));
		when(projectRepository.save(projectEntity)).thenReturn(projectEntity);

		Project response = projectPersistenceService.createProject(project);

		verify(this.projectRepository, times(1)).save((projectEntity));
		verify(this.sdlcSystemRepository, times(1)).findById((project.getSdlcSystem().getId()));

		assertNotNull(response);
		assertEquals(projectEntity.getId(), response.getId());
		assertEquals(projectEntity.getSdlcSystemEntity().getId(), response.getSdlcSystem().getId());
		assertEquals(projectEntity.getExternalId(), response.getExternalId());
		assertEquals(projectEntity.getName(), response.getName());
		assertEquals(projectEntity.getSdlcSystemEntity().getDescription(), response.getSdlcSystem().getDescription());
		assertEquals(projectEntity.getSdlcSystemEntity().getBaseUrl(), response.getSdlcSystem().getBaseUrl());
		assertNull(projectEntity.getCreatedDate());
		assertNull(projectEntity.getLastModifiedDate());
	}

	@Test
	void testUpdateProject_withInValidSDLCSystem__throwPersistenceException() {

		ProjectEntity projectEntity = buildProjectEntity();

		Project project = buildProject();

		when(sdlcSystemRepository.findById(project.getSdlcSystem().getId())).thenReturn(Optional.empty());
		when(projectRepository.save(projectEntity)).thenReturn(projectEntity);

		assertThatExceptionOfType(PersistenceException.class).isThrownBy(() ->
				this.projectPersistenceService.updateProject(project))
				.withMessageContaining("Error updating ProjectEntity record");
	}

	@Test
	void testUpdateProject_withValidProject_updateProjectEntity() {

		ProjectEntity projectEntity = buildProjectEntity();

		Project project = buildProject();

		when(sdlcSystemRepository.findById(project.getSdlcSystem().getId())).thenReturn(Optional.of(projectEntity.getSdlcSystemEntity()));
		when(projectRepository.save(projectEntity)).thenReturn(projectEntity);

		Project response = projectPersistenceService.updateProject(project);

		verify(this.projectRepository, times(1)).save((projectEntity));
		verify(this.sdlcSystemRepository, times(1)).findById((project.getSdlcSystem().getId()));

		assertNotNull(response);
		assertEquals(projectEntity.getId(), response.getId());
		assertEquals(projectEntity.getSdlcSystemEntity().getId(), response.getSdlcSystem().getId());
		assertEquals(projectEntity.getExternalId(), response.getExternalId());
		assertEquals(projectEntity.getName(), response.getName());
		assertEquals(projectEntity.getSdlcSystemEntity().getDescription(), response.getSdlcSystem().getDescription());
		assertEquals(projectEntity.getSdlcSystemEntity().getBaseUrl(), response.getSdlcSystem().getBaseUrl());
		assertNull(projectEntity.getCreatedDate());
		assertNull(projectEntity.getLastModifiedDate());
	}

	@Test
	void testDoesProjectExist_withValidExternalIdAndSDLCSystemId_returnsTrue() {

		String externalId = "externalId";
		long sdlcSystemId = 0L;

		when(projectRepository.existsByExternalIdAndSdlcSystemEntity_Id(externalId, sdlcSystemId)).thenReturn(true);

		Boolean response = projectPersistenceService.doesProjectExist(externalId, sdlcSystemId);

		verify(this.projectRepository, times(1)).existsByExternalIdAndSdlcSystemEntity_Id(externalId, sdlcSystemId);

		assertNotNull(response);
		assertTrue(response);
	}


	private static ProjectEntity buildProjectEntity() {

		ProjectEntity project = new ProjectEntity();
		project.setExternalId("externalId");
		project.setName("name");
		project.setId(0L);
		project.setSdlcSystemEntity(buildSdlcSystemEntity());

		return project;
	}

	private static SdlcSystemEntity buildSdlcSystemEntity() {
		SdlcSystemEntity sdlcSystem = new SdlcSystemEntity();
		sdlcSystem.setBaseUrl("url");
		sdlcSystem.setDescription("description");
		sdlcSystem.setId(0L);

		return sdlcSystem;
	}

	private static Project buildProject() {
		SdlcSystem sdlcSystem = new SdlcSystem();
		sdlcSystem.setId(2L);
		sdlcSystem.setBaseUrl("url");
		sdlcSystem.setDescription("description");

		Project project = new Project();
		project.setExternalId("externalId");
		project.setName("name");
		project.setId(0L);
		project.setSdlcSystem(sdlcSystem);

		return project;
	}

}
