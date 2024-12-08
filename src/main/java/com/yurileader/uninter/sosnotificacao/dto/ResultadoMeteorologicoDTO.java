package com.yurileader.uninter.sosnotificacao.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ResultadoMeteorologicoDTO {

    private int temp;
    private String date;
    private String time;
    private String conditionCode;
    private String description;
    private String currently;
    private String cid;
    private String city;
    private String imgId;
    private int humidity;
    private double cloudiness;
    private double rain;
    private String windSpeedy;
    private int windDirection;
    private String windCardinal;
    private String sunrise;
    private String sunset;
    private String moonPhase;
    private String conditionSlug;
    private String cityName;
    private String timezone;
    private List<PrevisaoMeteorologicoDTO> forecast;
    private String cref;
}
