package com.yurileader.uninter.sosnotificacao.service;

import org.springframework.stereotype.Service;

@Service
public class SmsService {

    public static final String ACCOUNT_SID = "SEU_ACCOUNT_SID";
    public static final String AUTH_TOKEN = "SEU_AUTH_TOKEN";

//    static {
//        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
//    }
//
//    public void enviarSms(String numero, String mensagem) {
//        Message sms = Message.creator(
//                new com.twilio.type.PhoneNumber(numero),
//                new com.twilio.type.PhoneNumber("SEU_NUMERO_TWILIO"),
//                mensagem
//        ).create();
//
//        System.out.println("Mensagem enviada com ID: " + sms.getSid());
//    }
}
