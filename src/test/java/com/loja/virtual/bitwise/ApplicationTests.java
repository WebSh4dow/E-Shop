package com.loja.virtual.bitwise;

import com.loja.virtual.bitwise.controller.AcessoController;
import com.loja.virtual.bitwise.model.Acesso;
import com.loja.virtual.bitwise.repository.AcessoRepository;
import com.loja.virtual.bitwise.service.AcessoService;
import junit.framework.TestCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;

@SpringBootTest(classes = Application.class)
class ApplicationTests extends TestCase {
	@Autowired
	private AcessoService acessoService;

	@Autowired
	private AcessoRepository acessoRepository;

	@Autowired
	private AcessoController acessoController;

	@Test
	void testeCadastroAcesso() {
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
