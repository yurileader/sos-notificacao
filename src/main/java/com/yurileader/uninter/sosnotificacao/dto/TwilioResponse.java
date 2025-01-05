package com.yurileader.uninter.sosnotificacao.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TwilioResponse {

    @JsonProperty(namespace = "error_message")
    private String mensagemErro;

    @JsonProperty(namespace = "error_code")
    private String codigoErro;

    @JsonProperty(namespace = "body")
    private String mensagem;

}
