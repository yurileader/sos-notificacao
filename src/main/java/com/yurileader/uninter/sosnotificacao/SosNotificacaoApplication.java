package com.yurileader.uninter.sosnotificacao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SosNotificacaoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SosNotificacaoApplication.class, args);
	}

}
