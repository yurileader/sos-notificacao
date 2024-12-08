package com.yurileader.uninter.sosnotificacao.service;

import com.yurileader.uninter.sosnotificacao.dto.AlertaMeteorologicoDTO;
import com.yurileader.uninter.sosnotificacao.dto.PrevisaoMeteorologicoDTO;
import com.yurileader.uninter.sosnotificacao.dto.UsuarioDTO;
import com.yurileader.uninter.sosnotificacao.service.client.HgMeteorologiaApiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AlertaMeteorologicoServico {

    private static final double LIMITE_CHUVA_MM = 50.0;

    @Value("${weather.api.key}")
    private String apiKey;

    private final HgMeteorologiaApiClient client;


    public void checarAlertas(String cidade) {
        AlertaMeteorologicoDTO alertas = client.getAlertas(apiKey, cidade);

        Optional<PrevisaoMeteorologicoDTO> max = alertas.getResults().getForecast().stream()
                .filter(previsaoDia -> previsaoDia.getRain() > LIMITE_CHUVA_MM)
                .max((p1, p2) -> Double.compare(p1.getRain(), p2.getRain()));

        // Verifica se é necessário emitir alerta
        if (max.isPresent()) {

            PrevisaoMeteorologicoDTO previsao = max.get();
            String msg = String.format(
                    "⚠️ Alerta de Chuva Severa! \n" +
                            "Data: %s\n" +
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
            // Implementar lógica de notificação aqui (ex: SMS/WhatsApp).
        } else {
            System.out.println("✔️ Sem alertas de tempo severo para " + cidade + ".");
        }
    }

    public void criarUsuario(UsuarioDTO usuarioDTO) {

    }

    public void desativarUsuario(Long id) {

    }
}
