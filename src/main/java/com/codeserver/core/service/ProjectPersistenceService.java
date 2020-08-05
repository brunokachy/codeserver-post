package com.codeserver.core.service;

import java.util.Optional;

import com.codeserver.core.dto.Project;

/**
 * @author Bruno Okafor 2020-08-05
 */
public interface ProjectPersistenceService {

	Optional<Project> findProjectByProjectId(long projectId);

	Project createProject(Project project);

	Boolean doesProjectExist(String externalId, long sdlcSystemId);
}
