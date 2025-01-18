package com.yurileader.uninter.sosnotificacao.servico;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import com.yurileader.uninter.sosnotificacao.repositorio.UsuarioRepositorio;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class NotificacaoServico {
    private static final Logger logger = LoggerFactory.getLogger(NotificacaoServico.class);

    @Autowired
    private UsuarioRepositorio repositorio;

    private final String PREFIXO_BRL = "+55";

    @Value("${sms.api.sid}")
    private  String contaSid;

    @Value("${sms.api.token}")
    private  String authToken;

    @Value("${sms.api.from}")
    private  String remetente;

    @Value("${sms.api.from.wpp}")
    private  String remetenteWpp;

    @PostConstruct
    public void initTwilio() {
        Twilio.init(contaSid, authToken);
    }

    private final Map<String, String> otpStorage = new HashMap<>();


    public void enviarConfirmacao(String destinatario) {
        String otp = String.format("%04d", new Random().nextInt(9000) + 1000);

        otpStorage.put(destinatario, otp);

        String mensagem = "Seu código de confirmação é: " + otp;
         Message
                .creator(
                        new PhoneNumber(PREFIXO_BRL + destinatario),
                        new PhoneNumber(remetente),
                        mensagem
                )
                .create();
    }

    public boolean validarTokenConfirmacao(String phoneNumber, String otp) {
        String storedOtp = otpStorage.get(phoneNumber);
        return storedOtp != null && storedOtp.equals(otp);
    }


    public void enviarAlertas(String mensagem) {
        repositorio.findAll().parallelStream().forEach(usuario -> {
            String telefoneDestinatario = usuario.getTelefone();
            enviarSms(PREFIXO_BRL + telefoneDestinatario, formatarMensagemUsuario(usuario.getNome(), mensagem));
            enviarWhatsapp(telefoneDestinatario, formatarMensagemUsuario(usuario.getNome(), mensagem));
        });
    }


    private void enviarSms(String destinatario, String mensagem) {
        Message message = Message
                .creator(
                        new PhoneNumber(destinatario),
                        new PhoneNumber(remetente),
                        mensagem
                )
                .create();

    }

    private void enviarWhatsapp(String destinatario, String mensagem ) {
        Message message = Message
                .creator(              //Realizo o replace first, porque a Twilio nao aceita o 9 adicional
                        new PhoneNumber("whatsapp:+55" + destinatario.replaceFirst("9", "")),
                        new PhoneNumber(remetenteWpp),
                        mensagem
                )
                .create();

    }
    private void getLog(String destinatario, String causa){
        logger.error("Falha ao enviar notificacao {}: {} ", destinatario, causa);
        System.out.printf("Falha ao enviar notificacao %s: %s ", destinatario, causa);
    }

    private String formatarMensagemUsuario(String usuario, String mensagem) {
        return "Olá " + usuario + "!  " + mensagem;
    }
}
