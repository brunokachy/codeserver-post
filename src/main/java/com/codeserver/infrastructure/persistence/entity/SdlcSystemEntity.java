package com.codeserver.infrastructure.persistence.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.URL;

import lombok.Data;

/**
 * @author Bruno Okafor 2020-08-05
 */
@Data
@Entity
@Table(name = "sdlc_system")
public class SdlcSystemEntity extends AuditModel implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@URL
	@Column(name = "base_url", nullable = false)
	private String baseUrl;

	@Column(name = "description")
	private String description;

}
