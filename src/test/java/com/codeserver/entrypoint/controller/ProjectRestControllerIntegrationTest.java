package com.codeserver.entrypoint.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.codeserver.CodeserverApplication;
import com.codeserver.core.dto.Project;
import com.codeserver.core.dto.SdlcSystem;
import com.codeserver.core.exception.ConflictException;
import com.codeserver.core.exception.ProjectNotFoundException;
import com.codeserver.core.exception.SdlcSystemNotFoundException;
import com.codeserver.core.service.ProjectService;

/**
 * @author Bruno Okafor 2020-08-05
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = {CodeserverApplication.class})
public class ProjectRestControllerIntegrationTest {
	private static final String ENDPOINT = "/api/v2/projects";

	@MockBean
	private ProjectService projectService;

	@Autowired
	private WebApplicationContext webApplicationContext;

	private MockMvc mockMvc;

	@BeforeAll
	public void setUp() {
		this.mockMvc = webAppContextSetup(webApplicationContext).build();
	}

	@Test
	public void should_GetProject_When_ValidRequest() throws Exception {

		Project project = buildProject();

		when(projectService.getProject(12345L)).thenReturn(project);

		mockMvc.perform(get(ENDPOINT + "/12345")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.externalId").value("externalId"))
				.andExpect(jsonPath("$.name").value("name"))
				.andExpect(jsonPath("$.sdlcSystem.id").value(2));
	}

	@Test
	public void should_ThrowProjectNotFoundException_When_InValidProjectId() throws Exception {

		when(projectService.getProject(12345L)).thenThrow(ProjectNotFoundException.class);

		mockMvc.perform(get(ENDPOINT + "/12345")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}

	@Test
	public void should_ThrowBadRequestException_When_IllegalPathVariable() throws Exception {

		mockMvc.perform(get(ENDPOINT + "/whatever")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void should_ThrowProjectNotFoundException_When_InvalidPathVariable() throws Exception {

		mockMvc.perform(get(ENDPOINT + "/path/12345")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}

	@Test
	public void should_CreateProject_When_FullPayLoad() throws Exception {

		Project project = buildProject();

		when(projectService.createProject(any(Project.class))).thenReturn(project);

		mockMvc.perform(post(ENDPOINT)
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\n\t\"externalId\": \"EXTERNALID\",\n\t\"name\": \"Name\",\n\t\"sdlcSystem\": {\n\t\t\"id\": 1\n\t}\n}")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andExpect(header().string("Location", ENDPOINT + "/1"))
				.andExpect(jsonPath("$.externalId").value("EXTERNALID"))
				.andExpect(jsonPath("$.name").value("Name"))
				.andExpect(jsonPath("$.sdlcSystem.id").value(1));
	}

	@Test
	public void should_CreateProject_When_MinimalPayLoad() throws Exception {

		Project project = buildProject();

		when(projectService.createProject(any(Project.class))).thenReturn(project);

		mockMvc.perform(post(ENDPOINT)
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\n\t\"externalId\": \"EXTERNAL-ID\",\n\t\"sdlcSystem\": {\n\t\t\"id\": 1\n\t}\n}")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andExpect(header().string("Location", ENDPOINT + "/1"))
				.andExpect(jsonPath("$.externalId").value("EXTERNAL-ID"))
				.andExpect(jsonPath("$.sdlcSystem.id").value(1));
	}

	@Test
	public void should_ThrowBadRequestException_When_PayloadContainingIllegalValue() throws Exception {

		mockMvc.perform(post(ENDPOINT)
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\n\t\"sdlcSystem\": {\n\t\t\"id\": \"Whatever\"\n\t}\n}")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void should_ThrowBadRequestException_When_PayloadNotContainingExternalID() throws Exception {

		mockMvc.perform(post(ENDPOINT)
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\n\t\"sdlcSystem\": {\n\t\t\"id\": 1\n\t}\n}")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void should_ThrowBadRequestException_When_PayloadNotContainingSystem() throws Exception {

		mockMvc.perform(post(ENDPOINT)
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\n\t\"externalId\": \"EXTERNAL-ID\"\n}")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void should_ThrowSdlcSystemNotFoundException_When_PayloadContainingNonExistingSystem() throws Exception {

		when(projectService.createProject(any(Project.class))).thenThrow(SdlcSystemNotFoundException.class);

		mockMvc.perform(post(ENDPOINT)
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\n\t\"externalId\": \"EXTERNALID\",\n\t\"sdlcSystem\": {\n\t\t\"id\": 12345\n\t}\n}")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}

	@Test
	public void should_ThrowConflictException_When_PatchPayloadContainingConflictingSystemExternalID() throws Exception {

		when(projectService.createProject(any(Project.class))).thenThrow(ConflictException.class);

		mockMvc.perform(post(ENDPOINT)
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\n\t\"externalId\": \"SAMPLEPROJECT\",\n\t\"sdlcSystem\": {\n\t\t\"id\": 1\n\t}\n}")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isConflict());
	}

	private static Project buildProject() {
		SdlcSystem sdlcSystem = new SdlcSystem();
		sdlcSystem.setId(2);

		Project project = new Project();
		project.setExternalId("externalId");
		project.setName("name");
		project.setId(1);
		project.setSdlcSystem(sdlcSystem);

		return project;
	}
}
