package com.codeserver.core.project.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Bruno Okafor 2020-07-27
 */
@Getter
@AllArgsConstructor(staticName = "of")
public class SdlcSystem {

	private String sdlcSystemId;

	private String baseUrl;

	private String description;

	private Date createdDate;

	private Date lastModifiedDate;
}
