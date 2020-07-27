package com.codeserver.entrypoint.controller.converter;

import com.codeserver.core.project.model.Project;
import com.codeserver.entrypoint.controller.dto.ProjectRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author Bruno Okafor 2020-07-27
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProjectConverter {

	public static Project toProject(ProjectRequest projectRequest) {
		return Project.of(
				null,
				null,
				projectRequest.getName(),
				null,
				null,
				null,
				projectRequest.getSdlcSystemId()
		);
	}
}
