package com.yurileader.uninter.sosnotificacao.conversor;

import com.yurileader.uninter.sosnotificacao.dto.UsuarioDTO;
import com.yurileader.uninter.sosnotificacao.entidade.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UsuarioMapper {
    UsuarioMapper INSTANCE = Mappers.getMapper(UsuarioMapper.class);

    UsuarioDTO toDTO(Usuario usuario);

    Usuario toEntity(UsuarioDTO usuarioDTO);
}
