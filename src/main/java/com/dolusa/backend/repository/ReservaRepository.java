package com.dolusa.backend.repository;

import com.dolusa.backend.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReservaRepository extends JpaRepository<Reserva, Integer> {
    List<Reserva> findAllByOrderByCreadoEnDesc();
    List<Reserva> findByEvento_IdEventoOrderByCreadoEnDesc(Integer idEvento);

    @Query("""
        SELECT COUNT(r) > 0
        FROM Reserva r
        WHERE r.evento.idEvento = :idEvento
          AND r.area.idArea = :idArea
          AND r.estado.nombre <> 'cancelada'
        """)
    boolean existeReservaActivaParaAreaEnEvento(@Param("idEvento") Integer idEvento, @Param("idArea") Integer idArea);
}
