package com.codeserver.core.service.impl;

import java.text.MessageFormat;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.codeserver.core.dto.Project;
import com.codeserver.core.exception.ConflictException;
import com.codeserver.core.exception.ProjectNotFoundException;
import com.codeserver.core.exception.SdlcSystemNotFoundException;
import com.codeserver.core.service.ProjectPersistenceService;
import com.codeserver.core.service.ProjectService;
import com.codeserver.core.service.SdlcSystemPersistenceService;
import lombok.RequiredArgsConstructor;

/**
 * @author Bruno Okafor 2020-08-05
 */
@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

	private final ProjectPersistenceService projectPersistenceService;

	private final SdlcSystemPersistenceService sdlcSystemPersistenceService;

	@Override
	public Project getProject(final long projectId) {

		final Optional<Project> optionalProject = projectPersistenceService.findProjectByProjectId(projectId);

		return optionalProject.orElseThrow(()
				-> new ProjectNotFoundException(MessageFormat.format("Could not find project with id {0}", projectId)));
	}

	@Override
	public Project createProject(final Project project) {

		validateSDLCSystem(project.getSdlcSystem().getId());

		validateProject(project.getExternalId(), project.getSdlcSystem().getId());

		return projectPersistenceService.createProject(project);
	}

	private void validateSDLCSystem(final long sdlcSystemId) {

		boolean doesSDLCSystemExist = sdlcSystemPersistenceService.doesSDLCSystemExist(sdlcSystemId);

		if (!doesSDLCSystemExist) {
			throw new SdlcSystemNotFoundException(
					MessageFormat.format("Could not find SDLC System with id {0}", sdlcSystemId));
		}

	}

	private void validateProject(final String externalId, final long sdlcSystemId) {

		boolean doesProjectExist = projectPersistenceService.doesProjectExist(externalId, sdlcSystemId);

		if (doesProjectExist) {
			throw new ConflictException(
					MessageFormat.format("A project with this external id {0} already exist in SDLC System of id {1}",
							externalId,
							sdlcSystemId
					));
		}

	}

}
