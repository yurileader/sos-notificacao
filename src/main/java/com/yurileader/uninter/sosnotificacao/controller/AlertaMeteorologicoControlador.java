package com.yurileader.uninter.sosnotificacao.controller;


import com.yurileader.uninter.sosnotificacao.dto.UsuarioDTO;
import com.yurileader.uninter.sosnotificacao.service.AlertaMeteorologicoServico;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("alertas")
@RequiredArgsConstructor
public class AlertaMeteorologicoControlador {

    private final AlertaMeteorologicoServico servico;

    @PostMapping
    private ResponseEntity<Void> salvarUsuario(UsuarioDTO usuarioDTO) {

        servico.criarUsuario(usuarioDTO);

        return ResponseEntity.ok().build();
    }

    @PutMapping
    private ResponseEntity<Void> desativarUsuario(Long id) {

        servico.desativarUsuario(id);

        return ResponseEntity.ok().build();
    }

}
