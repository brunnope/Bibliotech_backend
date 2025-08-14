package com.bibliotech.bibliotech.service.notificacao;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EmailService{

    @Value("${email.usuario}")
    private String usuario;

    @Value("${email.senha}")
    private String senha;

    @Value("${email.host:smtp.gmail.com}")
    private String hostName;

    @Value("${email.port:587}")
    private int port;

    public void enviarEmail(Mensagem mensagem) throws EmailException {

        SimpleEmail email = new SimpleEmail();

        email.setHostName(hostName);
        email.setSmtpPort(port);
        email.setAuthenticator(new DefaultAuthenticator(usuario, senha));
        email.setStartTLSEnabled(true);

        email.setFrom(usuario);
        email.setSubject(mensagem.getTitulo());
        email.setMsg(mensagem.getConteudo());
        email.addTo(mensagem.getDestinatario());

        email.send();
    }

}