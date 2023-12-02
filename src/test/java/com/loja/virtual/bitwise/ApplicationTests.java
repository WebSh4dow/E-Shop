package com.loja.virtual.bitwise;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.loja.virtual.bitwise.controller.AcessoController;
import com.loja.virtual.bitwise.exception.ExceptionErro;
import com.loja.virtual.bitwise.model.Acesso;
import com.loja.virtual.bitwise.repository.AcessoRepository;
import com.loja.virtual.bitwise.service.AcessoService;
import junit.framework.TestCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import java.util.List;

@SpringBootTest(classes = Application.class)
class ApplicationTests extends TestCase {
	@Autowired
	private AcessoService acessoService;

	@Autowired
	private AcessoRepository acessoRepository;

	@Autowired
	private AcessoController acessoController;

	@Autowired
	private WebApplicationContext webApplicationContext;

	private static String REQUEST_MAPPING_ACESSOS = "/acessos";

	@Test
	void testRestApiCadastroAcesso() throws JsonProcessingException, Exception {
		DefaultMockMvcBuilder defaultMockMvcBuilder = MockMvcBuilders.webAppContextSetup(this.webApplicationContext);
		MockMvc mockMvc = defaultMockMvcBuilder.build();

		Acesso acesso = new Acesso();

		acesso.setDescricao("ROLE_COMPRADOR");

		ObjectMapper objectMapper = new ObjectMapper();

		ResultActions retornoAPI = mockMvc
				.perform(MockMvcRequestBuilders.post(REQUEST_MAPPING_ACESSOS + "/salvarAcesso")
						.content(objectMapper.writeValueAsString(acesso))
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON));

		System.out.println("Retorno da api na chamada da controller de [salvar acessos]: " + retornoAPI.andReturn().getResponse().getContentAsString());

		Acesso objetoRetorno = objectMapper
				.readValue(retornoAPI.andReturn().getResponse().getContentAsString(), Acesso.class);

		assertEquals(acesso.getDescricao(), objetoRetorno.getDescricao());
	}

	@Test
	void testRestApiRemoverAcesso() throws JsonProcessingException, Exception {
		DefaultMockMvcBuilder defaultMockMvcBuilder = MockMvcBuilders.webAppContextSetup(this.webApplicationContext);
		MockMvc mockMvc = defaultMockMvcBuilder.build();

		Acesso acesso = new Acesso();

		acesso.setDescricao("ROLE_TESTE_DELETE");

		acesso = acessoRepository.save(acesso);

		ObjectMapper objectMapper = new ObjectMapper();

		ResultActions retornoAPI = mockMvc
				.perform(MockMvcRequestBuilders.post(REQUEST_MAPPING_ACESSOS + "/removerAcesso")
						.content(objectMapper.writeValueAsString(acesso))
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON));

		System.out.println("Retorno da api na chamada da controller de [remover acessos]: " + retornoAPI.andReturn().getResponse().getContentAsString());
		System.out.println("Status de retorno: " + retornoAPI.andReturn().getResponse().getStatus());

		assertEquals("Acesso removido com sucesso!!!",retornoAPI.andReturn().getResponse().getContentAsString());
		assertEquals(200,retornoAPI.andReturn().getResponse().getStatus());
	}

	@Test
	void testRestApiRemoverAcessoPorId() throws JsonProcessingException, Exception {
		DefaultMockMvcBuilder defaultMockMvcBuilder = MockMvcBuilders.webAppContextSetup(this.webApplicationContext);
		MockMvc mockMvc = defaultMockMvcBuilder.build();

		Acesso acesso = new Acesso();

		acesso.setDescricao("ROLE_TESTE_DELETE_POR_ID");

		acesso = acessoRepository.save(acesso);

		ObjectMapper objectMapper = new ObjectMapper();

		ResultActions retornoAPI = mockMvc
				.perform(MockMvcRequestBuilders.delete(REQUEST_MAPPING_ACESSOS + "/removerAcessoPorId/" + acesso.getId())
						.content(objectMapper.writeValueAsString(acesso))
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON));

		System.out.println("Retorno da api na chamada da controller de [remover acessos]: " + retornoAPI.andReturn().getResponse().getContentAsString());
		System.out.println("Status de retorno: " + retornoAPI.andReturn().getResponse().getStatus());

		assertEquals("Acesso removido com sucesso!!!",retornoAPI.andReturn().getResponse().getContentAsString());
		assertEquals(200,retornoAPI.andReturn().getResponse().getStatus());
	}


	@Test
	void testRestApiBuscarAcessoPorId() throws JsonProcessingException, Exception {
		DefaultMockMvcBuilder defaultMockMvcBuilder = MockMvcBuilders.webAppContextSetup(this.webApplicationContext);
		MockMvc mockMvc = defaultMockMvcBuilder.build();

		Acesso acesso = new Acesso();

		acesso.setDescricao("ROLE_TESTE_BUSCAR_POR_ID");

		acesso = acessoRepository.save(acesso);

		ObjectMapper objectMapper = new ObjectMapper();

		ResultActions retornoAPI = mockMvc
				.perform(MockMvcRequestBuilders.get(REQUEST_MAPPING_ACESSOS + "/obterAcesso/" + acesso.getId())
						.content(objectMapper.writeValueAsString(acesso))
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON));

		assertEquals(200,retornoAPI.andReturn().getResponse().getStatus());

		Acesso acessoRetorno = objectMapper.readValue(retornoAPI.andReturn().getResponse().getContentAsString(), Acesso.class);

		assertEquals(acesso.getDescricao(),acessoRetorno.getDescricao());

		assertEquals(acesso.getId(),acessoRetorno.getId());
	}

	@Test
	void testRestApiBuscarAcessoPorDescricao() throws JsonProcessingException, Exception {
		DefaultMockMvcBuilder defaultMockMvcBuilder = MockMvcBuilders.webAppContextSetup(this.webApplicationContext);
		MockMvc mockMvc = defaultMockMvcBuilder.build();

		Acesso acesso = new Acesso();

		acesso.setDescricao("ROLE_TESTE_BUSCAR_POR_DESCRICAO");

		acesso = acessoRepository.save(acesso);

		ObjectMapper objectMapper = new ObjectMapper();

		ResultActions retornoAPI = mockMvc
				.perform(MockMvcRequestBuilders.get(REQUEST_MAPPING_ACESSOS + "/buscarPor/ROLE_TESTE_BUSCAR_POR_DESCRICAO")
						.content(objectMapper.writeValueAsString(acesso))
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON));

		assertEquals(200,retornoAPI.andReturn().getResponse().getStatus());

		List<Acesso> retornoApiList = objectMapper
				.readValue(retornoAPI.andReturn().getResponse().getContentAsString(), new TypeReference<List<Acesso>>() {});

		assertEquals(7,retornoApiList.size());
		assertEquals(acesso.getDescricao(), retornoApiList.get(0).getDescricao());

		acessoRepository.deleteById(acesso.getId());
	}

	@Test
	void testeCadastroAcesso() throws ExceptionErro {
		Acesso acesso = new Acesso();

		acesso.setDescricao("ROLE_NORMAL");

		assertEquals(true,acesso.getId() == null);

		acesso = acessoController.salvarAcesso(acesso).getBody();

		assertEquals(true,acesso.getId() > 0);

		assertEquals("ROLE_NORMAL",acesso.getDescricao());

		Acesso segundoAcesso = acessoRepository.findById(acesso.getId()).get();

		assertEquals(acesso.getId(),segundoAcesso.getId());

		acessoRepository.deleteById(segundoAcesso.getId());
		acessoRepository.flush();

		Acesso terceiroAcesso = acessoRepository.findById(segundoAcesso.getId()).orElse(null);

		acesso = new Acesso();

		acesso.setDescricao("ROLE_GERENTE");

		assertEquals(true, terceiroAcesso == null);

		acesso = acessoController.salvarAcesso(acesso).getBody();

		List<Acesso> acessos = acessoRepository.buscarAcessoDesc("GERENTE".trim().toUpperCase());

		assertEquals(1,acessos.size());

		acessoRepository.deleteById(acesso.getId());
	}

}
