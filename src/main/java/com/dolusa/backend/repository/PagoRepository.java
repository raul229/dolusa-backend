package com.dolusa.backend.repository;

import com.dolusa.backend.model.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PagoRepository extends JpaRepository<Pago, Integer> {
    Optional<Pago> findByReserva_IdReserva(Integer idReserva);

    @Query("""
        SELECT COALESCE(SUM(p.monto), 0)
        FROM Pago p
        WHERE p.reserva.evento.idEvento = :idEvento
          AND p.estadoPago = com.dolusa.backend.model.Pago.EstadoPago.completado
        """)
    java.math.BigDecimal recaudacionPorEvento(@Param("idEvento") Integer idEvento);

    @Query("""
        SELECT p.metodo.nombre, COUNT(p), COALESCE(SUM(p.monto), 0)
        FROM Pago p
        WHERE p.reserva.evento.idEvento = :idEvento
          AND p.estadoPago = com.dolusa.backend.model.Pago.EstadoPago.completado
        GROUP BY p.metodo.nombre
        """)
    List<Object[]> resumenPorMetodo(@Param("idEvento") Integer idEvento);
}
