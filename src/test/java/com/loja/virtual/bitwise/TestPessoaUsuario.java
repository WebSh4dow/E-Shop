package com.loja.virtual.bitwise;

import com.loja.virtual.bitwise.controller.PessoaController;
import com.loja.virtual.bitwise.exception.ExceptionErro;
import com.loja.virtual.bitwise.model.PessoaFisica;
import com.loja.virtual.bitwise.model.PessoaJuridica;
import com.loja.virtual.bitwise.repository.PessoaRepository;
import com.loja.virtual.bitwise.service.PessoaUsuarioService;
import junit.framework.TestCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

@SpringBootTest(classes = Application.class)
@Profile("test")
public class TestPessoaUsuario extends TestCase {

    @Autowired
    private PessoaUsuarioService usuarioService;

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private PessoaController pessoaController;

    @Test
    public void testCadastroPessoa() throws ExceptionErro {

        PessoaJuridica pessoaJuridica = new PessoaJuridica();

        pessoaJuridica.setCnpj("00023418954");
        pessoaJuridica.setInscricaoEstadual("9920232302932");
        pessoaJuridica.setNome("jarmison");
        pessoaJuridica.setEmail("jarmison@gmail.com");
        pessoaJuridica.setTelefone("9920432202932");
        pessoaJuridica.setNomeFantasia("PAIVA");
        pessoaJuridica.setRazaoSocial("PAIVA LTDA");

        pessoaController.salvarPessoaJuridica(pessoaJuridica);
    }


}
