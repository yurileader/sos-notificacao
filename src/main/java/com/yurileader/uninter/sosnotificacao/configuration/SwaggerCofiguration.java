package com.yurileader.uninter.sosnotificacao.configuration;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerCofiguration {

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("sms-notification-api")
                .pathsToMatch("/alertas/**")
                .build();
    }
}
