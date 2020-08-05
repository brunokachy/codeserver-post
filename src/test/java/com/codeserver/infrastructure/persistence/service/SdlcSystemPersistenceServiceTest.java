package com.codeserver.infrastructure.persistence.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.codeserver.infrastructure.persistence.repository.SdlcSystemRepository;

/**
 * @author Bruno Okafor 2020-08-05
 */
class SdlcSystemPersistenceServiceTest {

	@InjectMocks
	private SdlcSystemPersistenceServiceImpl sdlcSystemPersistenceService;

	@Mock
	private SdlcSystemRepository sdlcSystemRepository;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void testDoesSDLCSystemExist_withValidSDLCSystemId_returnsTrue() {

		long sdlcSystemId = 0L;

		when(sdlcSystemRepository.existsById(sdlcSystemId)).thenReturn(true);

		Boolean response = sdlcSystemPersistenceService.doesSDLCSystemExist(sdlcSystemId);

		verify(this.sdlcSystemRepository, times(1)).existsById(sdlcSystemId);

		assertNotNull(response);
		assertTrue(response);
	}

}
