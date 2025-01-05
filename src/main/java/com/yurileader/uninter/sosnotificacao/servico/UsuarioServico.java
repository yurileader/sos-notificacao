package com.yurileader.uninter.sosnotificacao.servico;

import com.yurileader.uninter.sosnotificacao.conversor.UsuarioMapper;
import com.yurileader.uninter.sosnotificacao.dto.UsuarioDTO;
import com.yurileader.uninter.sosnotificacao.entidade.Usuario;
import com.yurileader.uninter.sosnotificacao.excecao.ValidacaoException;
import com.yurileader.uninter.sosnotificacao.repositorio.UsuarioRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UsuarioServico {

    private final UsuarioRepositorio repositorio;

    public void criarUsuario(UsuarioDTO usuarioDTO) {
        validarNome(usuarioDTO.getNome());
        validarCelular(usuarioDTO.getTelefone());
        Usuario entity = UsuarioMapper.INSTANCE.toEntity(usuarioDTO);
        entity.setReceberNotificacao(Boolean.TRUE);
        repositorio.save(entity);
    }

    public void desativarUsuario(Long id) {
        Usuario usuario = repositorio.findById(id)
                .orElseThrow(() -> new ValidacaoException("Usuário não encontrado"));

        if(Boolean.TRUE.equals(usuario.getReceberNotificacao())){
            usuario.setReceberNotificacao(false);
            repositorio.save(usuario);
        }

        repositorio.deleteById(usuario.getId());
    }


    private void validarNome(String nome) {
        String regex = "^[\\p{L} .'-]+$";
         if (nome == null || !nome.matches(regex)) {
             throw new ValidacaoException("Nome inválido");
         }
    }

    private void validarCelular(String telefone) {
        String padraoBrasil = "^\\+55\\d{2}9\\d{8}$";

        if (telefone != null && !telefone.startsWith("55")) {
            telefone = "55" + telefone;
        }

        if (telefone == null || !telefone.matches(padraoBrasil)) {
             throw new ValidacaoException("Número de telefone celular inválido");
        }
    }
}
