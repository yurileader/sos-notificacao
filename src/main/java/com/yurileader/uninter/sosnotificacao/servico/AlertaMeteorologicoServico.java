package com.yurileader.uninter.sosnotificacao.servico;

import com.yurileader.uninter.sosnotificacao.dto.AlertaMeteorologicoDTO;
import com.yurileader.uninter.sosnotificacao.dto.PrevisaoMeteorologicoDTO;
import com.yurileader.uninter.sosnotificacao.servico.client.HgMeteorologiaApiClient;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AlertaMeteorologicoServico {
    private static final double LIMITE_CHUVA_MM = 50.0;

    private final HgMeteorologiaApiClient client;
    private final NotificacaoServico notificacaoServico;


    @Value("${weather.api.key}")
    private String apiKey;

    @Value("${weather.api.cidade}")
    private String cidade;

//    @Value("${weather.api.temporizador}")
//    private String temporizador;

    @Scheduled(fixedRateString = "${weather.api.temporizador}") // 30 minutos em milissegundos
    public void checarAlertas() {
        AlertaMeteorologicoDTO alertas = client.buscarAlertas(apiKey, cidade);

        Optional<PrevisaoMeteorologicoDTO> max = alertas.getResults().getForecast().stream()
                .filter(previsaoDia -> previsaoDia.getRain() > LIMITE_CHUVA_MM)
                .max((p1, p2) -> Double.compare(p1.getRain(), p2.getRain()));

        // Verifica se é necessário emitir alerta
        if (max.isPresent()) {

            PrevisaoMeteorologicoDTO previsao = max.get();
            String msg = String.format(
                    "⚠️ Alerta de Chuva Severa! \n" +
                            "Inundações iminentes. Procure abrigo elevado na Data: %s\n" +
                            "Descrição: %s\n" +
                            "Hora de Início: %s\n" +
                            "Hora de Término: %s\n" +
                            "Volume de Chuva: %.2f mm",
                    previsao.getDate(),
                    previsao.getDescription(),
                    previsao.getSunrise(),
                    previsao.getSunset(),
                    previsao.getRain()
            );
            System.out.println(msg);
            notificacaoServico.enviar(msg);
        } else {
            System.out.println("✔️ Sem alertas de tempo severo para " + cidade + ".");
        }
    }

}
