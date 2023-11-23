package com.loja.virtual.bitwise;

import com.loja.virtual.bitwise.model.Acesso;
import com.loja.virtual.bitwise.service.AcessoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = Application.class)
class ApplicationTests {
	@Autowired
	private AcessoService acessoService;


	@Test
	void testeCadastroAcesso() {
		Acesso acesso = new Acesso();
		acesso.setDescricao("ROLE_NORMAL");
		acessoService.salvar(acesso);
	}

}
