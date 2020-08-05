package com.codeserver.infrastructure.persistence.service;

import java.util.Optional;

import javax.persistence.PersistenceException;

import org.springframework.stereotype.Service;

import com.codeserver.core.dto.Project;
import com.codeserver.core.service.ProjectPersistenceService;
import com.codeserver.infrastructure.persistence.converter.ProjectConverter;
import com.codeserver.infrastructure.persistence.entity.ProjectEntity;
import com.codeserver.infrastructure.persistence.entity.SdlcSystemEntity;
import com.codeserver.infrastructure.persistence.repository.ProjectRepository;
import com.codeserver.infrastructure.persistence.repository.SdlcSystemRepository;
import lombok.RequiredArgsConstructor;

/**
 * @author Bruno Okafor 2020-08-05
 */
@Service
@RequiredArgsConstructor
public class ProjectPersistenceServiceImpl implements ProjectPersistenceService {

	private final SdlcSystemRepository sdlcSystemRepository;

	private final ProjectRepository projectRepository;

	@Override
	public Optional<Project> findProjectByProjectId(final long projectId) {

		final Optional<ProjectEntity> optionalProjectEntity = projectRepository.findById(projectId);

		if (optionalProjectEntity.isPresent()) {

			final ProjectEntity projectEntity = optionalProjectEntity.get();

			final Project projectDTO = ProjectConverter.convertToProjectDomain(projectEntity, projectEntity.getSdlcSystemEntity());

			return Optional.of(projectDTO);
		}

		return Optional.empty();
	}

	@Override
	public Project createProject(final Project project) {

		try {

			final Optional<SdlcSystemEntity> optionalSdlcSystemEntity = sdlcSystemRepository.findById(project.getSdlcSystem().getId());

			final ProjectEntity projectEntity = ProjectConverter.convertToProjectEntity(project, optionalSdlcSystemEntity.get());

			final ProjectEntity createdProjectEntity = projectRepository.save(projectEntity);

			return ProjectConverter.convertToProjectDomain(createdProjectEntity, optionalSdlcSystemEntity.get());

		} catch (final Exception e) {
			throw new PersistenceException("Error creating ProjectEntity record", e);
		}
	}

	@Override
	public Boolean doesProjectExist(final String externalId, final long sdlcSystemId) {

		return projectRepository.existsByExternalIdAndSdlcSystemEntity_Id(externalId, sdlcSystemId);
	}
}
