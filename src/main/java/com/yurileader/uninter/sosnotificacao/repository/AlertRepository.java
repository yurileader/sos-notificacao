package com.yurileader.uninter.sosnotificacao.repository;

import com.yurileader.uninter.sosnotificacao.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlertRepository extends JpaRepository<Usuario, Long> {
}
