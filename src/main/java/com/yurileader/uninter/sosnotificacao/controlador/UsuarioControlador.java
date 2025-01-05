package com.yurileader.uninter.sosnotificacao.controlador;


import com.yurileader.uninter.sosnotificacao.dto.UsuarioDTO;
import com.yurileader.uninter.sosnotificacao.servico.UsuarioServico;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("alertas")
@RequiredArgsConstructor
public class UsuarioControlador {

    private final UsuarioServico servico;

    @PostMapping
    private ResponseEntity<Void> salvarUsuario(@RequestBody UsuarioDTO usuarioDTO) {

        servico.criarUsuario(usuarioDTO);

        return ResponseEntity.ok().build();
    }

    @PutMapping
    private ResponseEntity<Void> desativarUsuario(Long id) {

        servico.desativarUsuario(id);

        return ResponseEntity.ok().build();
    }

}
