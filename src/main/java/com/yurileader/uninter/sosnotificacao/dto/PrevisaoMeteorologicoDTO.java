package com.yurileader.uninter.sosnotificacao.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class PrevisaoMeteorologicoDTO {

    private String date;
    private String weekday;
    private int max;
    private int min;
    private int humidity;
    private double cloudiness;
    private double rain;
    private int rainProbability;
    private String windSpeedy;
    private String sunrise;
    private String sunset;
    private String moonPhase;
    private String description;
    private String condition;
}
