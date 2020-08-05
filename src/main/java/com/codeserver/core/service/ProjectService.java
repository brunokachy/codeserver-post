package com.codeserver.core.service;

import com.codeserver.core.dto.Project;

/**
 * @author Bruno Okafor 2020-08-05
 */
public interface ProjectService {

	Project createProject(Project project);

	Project updateProject(Project project, long projectId);

	Project getProject(long projectId);
}
