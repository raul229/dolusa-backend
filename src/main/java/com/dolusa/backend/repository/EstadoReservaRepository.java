package com.dolusa.backend.repository;

import com.dolusa.backend.model.EstadoReserva;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EstadoReservaRepository extends JpaRepository<EstadoReserva, Integer> {
    Optional<EstadoReserva> findByNombre(String nombre);
}
