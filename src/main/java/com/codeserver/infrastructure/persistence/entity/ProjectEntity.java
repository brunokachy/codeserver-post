package com.codeserver.infrastructure.persistence.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

/**
 * @author Bruno Okafor 2020-08-05
 */
@Data
@Entity
@Table(name = "project")
public class ProjectEntity extends AuditModel implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "external_id", nullable = false)
	private String externalId;

	@Column(name = "name")
	private String name;

	@ManyToOne
	@JoinColumn(name = "sdlc_system_id")
	private SdlcSystemEntity sdlcSystemEntity;

}
