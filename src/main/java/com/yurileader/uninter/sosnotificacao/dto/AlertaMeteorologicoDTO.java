package com.yurileader.uninter.sosnotificacao.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AlertaMeteorologicoDTO {

    private String by;
    private boolean validKey;
    private ResultadoMeteorologicoDTO results;
    private double executionTime;
    private boolean fromCache;


}
