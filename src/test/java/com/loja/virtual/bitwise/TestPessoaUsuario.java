package com.loja.virtual.bitwise;

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

    @Test
    public void testCadastroPessoa() {

        PessoaJuridica pessoaJuridica = new PessoaJuridica();
        PessoaFisica pessoaFisica = new PessoaFisica();

        pessoaJuridica.setCnpj("3242423423");
        pessoaJuridica.setInscricaoEstadual("432432423");
        pessoaJuridica.setNome("Juridica");
        pessoaJuridica.setEmail("juri@gmail.com");
        pessoaJuridica.setTelefone("34242424");
        pessoaJuridica.setNomeFantasia("Juju");
        pessoaJuridica.setRazaoSocial("JUUU");

        pessoaRepository.save(pessoaJuridica);

       /* pessoaFisica.setCpf("000000112330");
        pessoaFisica.setNome("Jubileu");
        pessoaFisica.setEmail("jubileuestaesqusito@gmail.com");
        pessoaFisica.setTelefone("12321321312");
        pessoaFisica.setEmpresa(pessoaFisica);*/

    }


}
