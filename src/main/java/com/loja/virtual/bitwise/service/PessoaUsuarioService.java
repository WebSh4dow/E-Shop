package com.loja.virtual.bitwise.service;


import com.loja.virtual.bitwise.model.*;
import com.loja.virtual.bitwise.model.dto.CepDTO;
import com.loja.virtual.bitwise.model.dto.ConsultaCnpjDto;
import com.loja.virtual.bitwise.repository.PessoaFisicaRepository;
import com.loja.virtual.bitwise.repository.PessoaRepository;
import com.loja.virtual.bitwise.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityNotFoundException;
import java.util.Calendar;

@Service
public class PessoaUsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private PessoaFisicaRepository pessoaFisicaRepository;

    @Autowired
    private ServiceSendEmail serviceSendEmail;

    @Autowired
    private JdbcTemplate template;

    private static Long EMPRESA_PADRAO = 42L;

    public PessoaJuridica salvarPessoaJuridica(PessoaJuridica pessoaJuridica) {

        for (int i = 0; i < pessoaJuridica.getEnderecos().size(); i++) {
            pessoaJuridica.getEnderecos().get(i).setPessoa(pessoaJuridica);
            pessoaJuridica.getEnderecos().get(i).setEmpresa(pessoaJuridica);
            pessoaJuridica.setEmpresa(pessoaJuridica);
        }
        pessoaJuridica = pessoaRepository.save(pessoaJuridica);

        Usuario usuarioPessoaJuridica = usuarioRepository.findUserByPessoa(pessoaJuridica.getId(), pessoaJuridica.getEmail());

        if (usuarioPessoaJuridica == null) {
            String constraint = usuarioRepository.consultarConstraintAcesso();
            if (constraint != null) {
                template.execute("begin; alter table usuarios_acesso drop constraint " + constraint + "; commit;");
            }

            usuarioPessoaJuridica = new Usuario();

            usuarioPessoaJuridica.setDataAtualSenha(Calendar.getInstance().getTime());
            usuarioPessoaJuridica.setEmpresa(pessoaJuridica);
            usuarioPessoaJuridica.setPessoa(pessoaJuridica);
            usuarioPessoaJuridica.setLogin(pessoaJuridica.getEmail());

            String senha = "" + Calendar.getInstance().getTimeInMillis();
            String senhaCript = new BCryptPasswordEncoder().encode(senha);

            usuarioPessoaJuridica.setSenha(senhaCript);

            usuarioPessoaJuridica = usuarioRepository.save(usuarioPessoaJuridica);

            usuarioRepository.insereAcessoUsuario(usuarioPessoaJuridica.getId());
            usuarioRepository.insereAcessoUsuario(usuarioPessoaJuridica.getId(), "ROLE_ADMIN");

            StringBuilder mensagemEmail = new StringBuilder();

            mensagemEmail.append("<b>Segue abaixo seus dados de acesso para a loja virtual</b>");
            mensagemEmail.append("<b>Login: </b>" + pessoaJuridica.getEmail() + "</b><br/>");
            mensagemEmail.append("<b>Senha: </b>").append(senha).append("<br/>");
            mensagemEmail.append("<b>Seja bem vindo a nossa loja virtual.</b>");

            try {
                serviceSendEmail.enviarEmailHtml("Acesso Gerado para Loja Virtual", mensagemEmail.toString(), pessoaJuridica.getEmail());

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return pessoaJuridica;
    }


    public PessoaFisica salvarPessoaFisica(PessoaFisica pessoaFisica) {

        PessoaJuridica empresaPadrao = pessoaRepository.buscarEmpresaPadrao(EMPRESA_PADRAO)
                .orElseThrow(() -> new EntityNotFoundException("Empresa padrão não encontrada"));

        if (empresaPadrao.getId() == null) {
            empresaPadrao = pessoaRepository.save(empresaPadrao);
        }

        pessoaFisica.setEmpresa(empresaPadrao);

        for (Endereco endereco : pessoaFisica.getEnderecos()) {
            endereco.setPessoa(pessoaFisica);
            endereco.setEmpresa(empresaPadrao);
        }

        pessoaFisica = pessoaFisicaRepository.save(pessoaFisica);

        Usuario usuarioPj = usuarioRepository.findUserByPessoa(pessoaFisica.getId(), pessoaFisica.getEmail());

        String constraint = usuarioRepository.consultarConstraintAcesso();

        if (usuarioPj == null && constraint != null) {
            template.execute("begin; alter table usuarios_acesso drop constraint " + constraint + "; commit;");

            usuarioPj = new Usuario();
            usuarioPj.setDataAtualSenha(Calendar.getInstance().getTime());

            usuarioPj.setPessoa(pessoaFisica);

            if (pessoaFisica.getEmpresa() instanceof PessoaJuridica) {
                usuarioPj.setEmpresa((PessoaJuridica) pessoaFisica.getEmpresa());
            }

            usuarioPj.setLogin(pessoaFisica.getEmail());

            String senha = "" + Calendar.getInstance().getTimeInMillis();
            String senhaCript = new BCryptPasswordEncoder().encode(senha);

            usuarioPj.setSenha(senhaCript);

            Usuario existingUser = usuarioRepository.findUserByPessoa(pessoaFisica.getId(), pessoaFisica.getEmail());

            if (existingUser == null) {
                usuarioPj = usuarioRepository.save(usuarioPj);
                usuarioRepository.insereAcessoUsuario(usuarioPj.getId());

                StringBuilder mensagemHtml = new StringBuilder();

                mensagemHtml.append("<b>Segue abaixo seus dados de acesso para a loja virtual</b><br/>");
                mensagemHtml.append("<b>Login: </b>" + pessoaFisica.getEmail() + "<br/>");
                mensagemHtml.append("<b>Senha: </b>").append(senha).append("<br/><br/>");
                mensagemHtml.append("Obrigado!");

                try {
                    serviceSendEmail.enviarEmailHtml("Acesso Gerado para Loja Virtual", mensagemHtml.toString(), pessoaFisica.getEmail());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return pessoaFisica;
    }

    public CepDTO consultaCepPessoa(String cep) {
        return new RestTemplate().getForEntity("https://viacep.com.br/ws/" + cep + "/json/", CepDTO.class).getBody();
    }

    public ConsultaCnpjDto consultaCnpjReceitaWs(String cnpj) {
        return new RestTemplate().getForEntity("https://receitaws.com.br/v1/cnpj/" + cnpj, ConsultaCnpjDto.class).getBody();

    }
}
