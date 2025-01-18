package com.yurileader.uninter.sosnotificacao.controlador;


import com.yurileader.uninter.sosnotificacao.dto.UsuarioDTO;
import com.yurileader.uninter.sosnotificacao.servico.UsuarioServico;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("alertas")
@RequiredArgsConstructor
@CrossOrigin("*")
public class UsuarioControlador {

    private final UsuarioServico servico;

    @PostMapping("/confirmacao")
    public ResponseEntity<String> sendOtp(@RequestBody UsuarioDTO usuarioDTO) {
        try {
            servico.enviarConfirmacao(usuarioDTO.getTelefone());
            return ResponseEntity.ok("Código de verificação enviado para " + usuarioDTO.getTelefone());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao enviar o SMS: " + e.getMessage());
        }
    }

    @PostMapping("/usuario")
    private ResponseEntity<String> salvarUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        usuarioDTO.setTelefone(usuarioDTO.getTelefone().replaceAll("\\D", ""));
        boolean isValid = servico.confirmarNumeroValido(usuarioDTO.getTelefone(), usuarioDTO.getNumeroConfirmacao());

        if (isValid) {
            servico.criarUsuario(usuarioDTO);
            return ResponseEntity.ok("Cadastro concluído com sucesso!");
        } else {
            return ResponseEntity.status(400).body("Código de verificação inválido ou expirado.");
        }
    }

    @PutMapping("/desativar")
    private ResponseEntity<Void> desativarUsuario(@RequestBody UsuarioDTO usuarioDTO) {

        servico.desativarUsuario(usuarioDTO.getTelefone());

        return ResponseEntity.ok().build();
    }

}
