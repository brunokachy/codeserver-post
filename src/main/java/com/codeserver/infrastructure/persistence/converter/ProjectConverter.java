package com.codeserver.infrastructure.persistence.converter;

import com.codeserver.core.dto.Project;
import com.codeserver.core.dto.SdlcSystem;
import com.codeserver.infrastructure.persistence.entity.ProjectEntity;
import com.codeserver.infrastructure.persistence.entity.SdlcSystemEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author Bruno Okafor 2020-08-05
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProjectConverter {

	public static ProjectEntity convertToProjectEntity(final Project project, final SdlcSystemEntity sdlcSystemEntity) {

		final ProjectEntity projectEntity = new ProjectEntity();
		projectEntity.setExternalId(project.getExternalId());
		projectEntity.setName(project.getName());
		projectEntity.setSdlcSystemEntity(sdlcSystemEntity);

		return projectEntity;
	}

	public static ProjectEntity convertToExistingProjectEntity(final Project project, final SdlcSystemEntity sdlcSystemEntity) {

		final ProjectEntity projectEntity = new ProjectEntity();
		projectEntity.setId(project.getId());
		projectEntity.setExternalId(project.getExternalId());
		projectEntity.setName(project.getName());
		projectEntity.setSdlcSystemEntity(sdlcSystemEntity);

		return projectEntity;
	}

	public static Project convertToProjectDomain(final ProjectEntity projectEntity, final SdlcSystemEntity sdlcSystemEntity) {

		final SdlcSystem sdlcSystem = new SdlcSystem();
		sdlcSystem.setBaseUrl(sdlcSystemEntity.getBaseUrl());
		sdlcSystem.setDescription(sdlcSystemEntity.getDescription());
		sdlcSystem.setId(sdlcSystemEntity.getId());

		final Project project = new Project();
		project.setName(projectEntity.getName());
		project.setExternalId(projectEntity.getExternalId());
		project.setId(projectEntity.getId());
		project.setSdlcSystem(sdlcSystem);

		return project;
	}
}
