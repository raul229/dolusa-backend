package com.dolusa.backend.repository;

import com.dolusa.backend.model.UsuarioSistema;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioSistemaRepository extends JpaRepository<UsuarioSistema, Integer> {
    Optional<com.dolusa.backend.model.UsuarioSistema> findByUsuario(String usuario);
}
