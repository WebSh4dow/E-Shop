package com.loja.virtual.bitwise.service;


import com.loja.virtual.bitwise.model.PessoaJuridica;
import com.loja.virtual.bitwise.model.Usuario;
import com.loja.virtual.bitwise.repository.PessoaRepository;
import com.loja.virtual.bitwise.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Calendar;

@Service
public class PessoaUsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private ServiceSendEmail serviceSendEmail;

    @Autowired
    private JdbcTemplate template;

    public PessoaJuridica salvarPessoaJuridica(PessoaJuridica pessoaJuridica) {

        for (int i = 0; i < pessoaJuridica.getEnderecos().size(); i++) {
            pessoaJuridica.getEnderecos().get(i).setPessoa(pessoaJuridica);
            pessoaJuridica.getEnderecos().get(i).setEmpresa(pessoaJuridica);
        }

        pessoaJuridica = pessoaRepository.save(pessoaJuridica);

        Usuario usuarioPessoaJuridica = usuarioRepository.findUserByPessoa(pessoaJuridica.getId(),pessoaJuridica.getEmail());

        if (usuarioPessoaJuridica == null) {
           String constraint = usuarioRepository.consultarConstraintAcesso();
           if (constraint != null)
               template.execute("begin; alter table usuarios_acesso drop constraint " + constraint + "; commit;" );
        }

        usuarioPessoaJuridica = new Usuario();

        usuarioPessoaJuridica.setDataAtualSenha(Calendar.getInstance().getTime());
        usuarioPessoaJuridica.setEmpresa(pessoaJuridica);
        usuarioPessoaJuridica.setLogin(pessoaJuridica.getEmail());
        usuarioPessoaJuridica.setPessoa(pessoaJuridica);

        String senha = "" + Calendar.getInstance().getTimeInMillis();
        String senhaCript = new BCryptPasswordEncoder().encode(senha);

        usuarioPessoaJuridica.setSenha(senhaCript);

        usuarioPessoaJuridica = usuarioRepository.save(usuarioPessoaJuridica);

        usuarioRepository.insereAcessoUsuarioPessoaJuridica(usuarioPessoaJuridica.getId());

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
        return pessoaJuridica;
    }
}
