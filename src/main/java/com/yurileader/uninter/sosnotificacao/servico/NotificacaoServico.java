package com.yurileader.uninter.sosnotificacao.servico;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import com.yurileader.uninter.sosnotificacao.repositorio.UsuarioRepositorio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class NotificacaoServico {
    private static final Logger logger = LoggerFactory.getLogger(NotificacaoServico.class);

    @Autowired
    private UsuarioRepositorio repositorio;

    @Value("${sms.api.sid}")
    private  String contaSid;

    @Value("${sms.api.token}")
    private  String authToken;

    @Value("${sms.api.from}")
    private  String remetente;


    public void enviar(String mensagem) {
        repositorio.findAll().parallelStream().forEach(usuario -> {
            String telefoneDestinatario = usuario.getTelefone();
            enviarSms("+" + telefoneDestinatario, mensagem);
//            enviarWhatsapp(telefoneDestinatario, mensagem);
        });
    }

    private void enviarSms(String destinatario, String mensagem) {
        Twilio.init(contaSid, authToken);

        Message message = Message
                .creator(
                        new PhoneNumber(destinatario),
                        new PhoneNumber(remetente),
                        mensagem
                )
                .create();

    }

    private void enviarWhatsapp(String destinatario, String mensagem ) {
        Twilio.init(contaSid, authToken);

        Message message = Message
                .creator(
                        new PhoneNumber("whatsapp:" + destinatario),
                        new PhoneNumber(remetente),
                        mensagem
                )
                .create();

    }
    private void getLog(String destinatario, String causa){
        logger.error("Falha ao enviar notificacao {}: {} ", destinatario, causa);
        System.out.printf("Falha ao enviar notificacao %s: %s ", destinatario, causa);
    }
}
