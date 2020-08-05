package com.codeserver.core.service.impl;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
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

	private static final String NAME = "name";

	private static final String EXTERNAL_ID = "externalId";

	private static final String SDLC_SYSTEM_ID = "sdlc_system_id";

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

	@Override
	public Project updateProject(final Project project, final long projectId) {

		final Map<String, String> updateFields = mapProjectFields(project);

		Project updatedProject = getExistingProject(projectId);

		if (MapUtils.isEmpty(updateFields)) {

			return projectPersistenceService.updateProject(updatedProject);
		}

		if (updateFields.containsKey(SDLC_SYSTEM_ID) && updateFields.containsKey(EXTERNAL_ID)) {

			updatedProject = updateSDLCSystemIdAndExternalId(
					updatedProject,
					Long.parseLong(updateFields.get(SDLC_SYSTEM_ID)),
					updateFields.get(EXTERNAL_ID)
			);

		} else if (updateFields.containsKey(EXTERNAL_ID)) {

			updatedProject = updateExternalId(updatedProject, updateFields.get(EXTERNAL_ID));

		} else if (updateFields.containsKey(SDLC_SYSTEM_ID)) {

			updatedProject = updateSDLCSystemId(updatedProject, Long.parseLong(updateFields.get(SDLC_SYSTEM_ID)));
		}

		if (updateFields.containsKey(NAME)) {

			updatedProject.setName(updateFields.get(NAME));

			updatedProject = projectPersistenceService.updateProject(updatedProject);
		}

		return updatedProject;
	}

	private Project getExistingProject(final long projectId) {

		final Optional<Project> optionalProject = projectPersistenceService.findProjectByProjectId(projectId);

		if (!optionalProject.isPresent()) {
			throw new ProjectNotFoundException(MessageFormat.format("Could not find project with id {0}", projectId));
		}

		return optionalProject.get();
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

	private Project updateExternalId(final Project project, final String externalId) {

		validateProject(externalId, project.getSdlcSystem().getId());

		project.setExternalId(externalId);

		return projectPersistenceService.updateProject(project);
	}

	private Project updateSDLCSystemId(final Project project, final long sdlcSystemId) {

		validateSDLCSystem(sdlcSystemId);

		validateProject(project.getExternalId(), sdlcSystemId);

		project.getSdlcSystem().setId(sdlcSystemId);

		return projectPersistenceService.updateProject(project);
	}

	private Project updateSDLCSystemIdAndExternalId(final Project project, final long sdlcSystemId, final String externalId) {

		validateSDLCSystem(sdlcSystemId);

		validateProject(externalId, sdlcSystemId);

		project.setExternalId(externalId);
		project.getSdlcSystem().setId(sdlcSystemId);

		return projectPersistenceService.updateProject(project);
	}

	private Map<String, String> mapProjectFields(final Project project) {

		final Map<String, String> updateFields = new HashMap<>();

		if (StringUtils.isNotEmpty(project.getExternalId())) {
			updateFields.put(EXTERNAL_ID, project.getExternalId());
		}

		if (project.getSdlcSystem() != null && project.getSdlcSystem().getId() != 0) {
			updateFields.put(SDLC_SYSTEM_ID, String.valueOf(project.getSdlcSystem().getId()));
		}

		if (StringUtils.isNotEmpty(project.getName())) {
			updateFields.put(NAME, project.getName());
		}

		return updateFields;
	}
}
