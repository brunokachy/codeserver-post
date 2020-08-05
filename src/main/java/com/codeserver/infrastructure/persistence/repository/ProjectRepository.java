package com.codeserver.infrastructure.persistence.repository;

import org.springframework.data.repository.CrudRepository;

import com.codeserver.infrastructure.persistence.entity.ProjectEntity;

/**
 * @author Bruno Okafor 2020-08-05
 */
public interface ProjectRepository extends CrudRepository<ProjectEntity, Long> {

	Boolean existsByExternalIdAndSdlcSystemEntity_Id(String externalId, long sdlcSystemId);
}
