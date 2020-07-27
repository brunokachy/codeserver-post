package com.codeserver.core.project.service;

import com.codeserver.core.project.model.Project;

/**
 * @author Bruno Okafor 2020-07-27
 */
public interface ProjectService {

	Project createProject(Project project);

	Project getProject(String projectId);
}
