package com.loja.virtual.bitwise.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

@Service
public class ServiceSendEmail {

    private String userMail = "email";

    private String senha = "senha";

    @Async
    public void enviarEmailHtml(String assunto, String mensagem, String emailDestino) throws MessagingException, UnsupportedEncodingException {
        Properties properties = new Properties();
        properties.put("mail.smtp.ssl.trust", "*");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls", "false");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userMail, senha);
            }
        });

        session.setDebug(true);

        Address[] toUser = InternetAddress.parse(emailDestino);
        Message message = new MimeMessage(session);

        message.setFrom(new InternetAddress(userMail, "Jarmison - Dev Pleno Java", "UTF-8"));
        message.setRecipients(Message.RecipientType.TO, toUser);

        message.setSubject(assunto);
        message.setContent(mensagem,"text/html; charset=utf-8");

        Transport.send(message);
    }
}
