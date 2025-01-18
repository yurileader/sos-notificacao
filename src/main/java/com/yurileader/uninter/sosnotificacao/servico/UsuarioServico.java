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
    private final NotificacaoServico notificacaoServico;

    public void enviarConfirmacao(String destinatario) {
       notificacaoServico.enviarConfirmacao(removerCaracteresTelefone(destinatario));
    }

    public boolean confirmarNumeroValido(String destinatario, String numeroConfirmacao) {
        return notificacaoServico.validarTokenConfirmacao(destinatario, numeroConfirmacao);
    }

    public void criarUsuario(UsuarioDTO usuarioDTO) {
        excluirUsuarioSeExistir(usuarioDTO.getTelefone());

        validarNome(usuarioDTO.getNome());
        validarCelular(usuarioDTO.getTelefone());
        Usuario entity = UsuarioMapper.INSTANCE.toEntity(usuarioDTO);
        entity.setReceberNotificacao(Boolean.TRUE);

        repositorio.save(entity);
    }

    public void desativarUsuario(String telefone) {
        Usuario usuario = repositorio.findByTelefone(removerCaracteresTelefone(telefone))
                .orElseThrow(() -> new ValidacaoException("Usuário não encontrado"));

        if(Boolean.TRUE.equals(usuario.getReceberNotificacao())){
            usuario.setReceberNotificacao(false);
            repositorio.save(usuario);
        }
    }


    private void validarNome(String nome) {
        String regex = "^[\\p{L} .'-]+$";
         if (nome == null || !nome.matches(regex)) {
             throw new ValidacaoException("Nome inválido");
         }
    }

    private void validarCelular(String telefone) {
        String padraoBrasil = "^\\d{2}9\\d{8}$";
        telefone = removerCaracteresTelefone(telefone);


        if (telefone == null || !telefone.matches(padraoBrasil)) {
             throw new ValidacaoException("Número de telefone celular inválido");
        }
    }

    private void excluirUsuarioSeExistir(String telefone) {
        repositorio.findByTelefone(telefone)
                .ifPresent(usuario -> {
                    repositorio.deleteById(usuario.getId());
                });
    }

    private String removerCaracteresTelefone (String telefone){
        return telefone.replaceAll("[^\\d]", "");
    }

}
