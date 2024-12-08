package com.yurileader.uninter.sosnotificacao.service.client;

import com.yurileader.uninter.sosnotificacao.dto.AlertaMeteorologicoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "hg-weather-api", url = "${weather.api.url}")
public interface HgMeteorologiaApiClient {


    @GetMapping("?key=${key}&city_name=${cidade}")
    AlertaMeteorologicoDTO getAlertas (@RequestParam("key") String key,
                                       @RequestParam("cidade") String cidade);
}
