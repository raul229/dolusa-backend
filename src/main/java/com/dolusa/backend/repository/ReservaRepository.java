package com.dolusa.backend.repository;

import com.dolusa.backend.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservaRepository extends JpaRepository<Reserva, Integer> {
    List<Reserva> findAllByOrderByCreadoEnDesc();
    List<Reserva> findByEvento_IdEventoOrderByCreadoEnDesc(Integer idEvento);
}
