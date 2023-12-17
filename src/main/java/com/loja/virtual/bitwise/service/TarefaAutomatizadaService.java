package com.loja.virtual.bitwise.service;

import com.loja.virtual.bitwise.model.Usuario;
import com.loja.virtual.bitwise.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.List;

@Service
public class TarefaAutomatizadaService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ServiceSendEmail serviceSendEmail;

    @Scheduled(cron = "0 0 11 * * *", zone = "America/Sao_Paulo")
    public void notificarUsuarioTrocaSenha() throws MessagingException, UnsupportedEncodingException, InterruptedException {
        List<Usuario> usuarios = usuarioRepository.usuarioSenhaExpirada();

        for (Usuario usuario : usuarios) {
            StringBuilder message = new StringBuilder();

            message.append("Olá, ").append(usuario.getPessoa().getNome()).append("<br/>");
            message.append("Está na hora de trocar sua senha, pois o mesmo já passou o limite de 90 dias de validade").append("<br/>");
            message.append("Troque sua senha a loja virtual agradece pela compreensão!!!");

            serviceSendEmail.enviarEmailHtml("Troca de senha deve ser realizada", message.toString(), usuario.getLogin());

            Thread.sleep(3000);

        }
    }
}
