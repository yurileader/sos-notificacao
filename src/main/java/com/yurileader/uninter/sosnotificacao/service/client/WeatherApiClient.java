package com.yurileader.uninter.sosnotificacao.service.client;

import com.yurileader.uninter.sosnotificacao.entity.WeatherData;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "weather-api", url = "https://api.meteo.example.com")
public interface WeatherApiClient {
    @GetMapping("/forecast")
    WeatherData getForecast(@RequestParam("region") String region);
}
