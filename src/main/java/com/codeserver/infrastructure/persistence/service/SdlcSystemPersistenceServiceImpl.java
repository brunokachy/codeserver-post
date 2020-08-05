package com.codeserver.infrastructure.persistence.service;

import org.springframework.stereotype.Service;

import com.codeserver.core.service.SdlcSystemPersistenceService;
import com.codeserver.infrastructure.persistence.repository.SdlcSystemRepository;
import lombok.RequiredArgsConstructor;

/**
 * @author Bruno Okafor 2020-08-05
 */
@Service
@RequiredArgsConstructor
public class SdlcSystemPersistenceServiceImpl implements SdlcSystemPersistenceService {

	private final SdlcSystemRepository sdlcSystemRepository;

	@Override
	public Boolean doesSDLCSystemExist(final long sdlcSystemId) {

		return sdlcSystemRepository.existsById(sdlcSystemId);
	}
}
