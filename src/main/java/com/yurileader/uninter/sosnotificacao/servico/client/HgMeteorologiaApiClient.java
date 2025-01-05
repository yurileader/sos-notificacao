package com.yurileader.uninter.sosnotificacao.servico.client;

import com.yurileader.uninter.sosnotificacao.dto.AlertaMeteorologicoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "meteorologiaClient", url = "${weather.api.url}" )
public interface HgMeteorologiaApiClient {


    @GetMapping
    AlertaMeteorologicoDTO buscarAlertas(
            @RequestParam("key") String key,
            @RequestParam("city_name") String cityName
    );
}
