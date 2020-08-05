package com.codeserver.infrastructure.persistence.entity;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;

/**
 * @author Bruno Okafor 2020-08-05
 */
@MappedSuperclass
@Getter
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditModel {

	@CreatedDate
	@Column(name = "created_date", nullable = false, updatable = false)
	private Instant createdDate;

	@LastModifiedDate
	@Column(name = "last_modified_date", nullable = false)
	private Instant lastModifiedDate;

}
