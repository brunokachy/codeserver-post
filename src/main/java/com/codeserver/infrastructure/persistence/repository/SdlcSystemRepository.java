package com.codeserver.infrastructure.persistence.repository;

import org.springframework.data.repository.CrudRepository;

import com.codeserver.infrastructure.persistence.entity.SdlcSystemEntity;

/**
 * @author Bruno Okafor 2020-08-05
 */
public interface SdlcSystemRepository extends CrudRepository<SdlcSystemEntity, Long> {

	Boolean existsById(long sdlcSystemId);

}
