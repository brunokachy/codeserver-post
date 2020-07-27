package com.codeserver.core.project.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Bruno Okafor 2020-07-27
 */
@Getter
@AllArgsConstructor(staticName = "of")
public class Project {

	private String projectId;

	private String externalId;

	private String name;

	private SdlcSystem sdlcSystem;

	private Date createdDate;

	private Date lastModifiedDate;

	private String sdlcSystemId;
}
