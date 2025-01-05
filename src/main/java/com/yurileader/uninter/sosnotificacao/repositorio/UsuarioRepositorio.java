package com.yurileader.uninter.sosnotificacao.repositorio;

import com.yurileader.uninter.sosnotificacao.entidade.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {
}
