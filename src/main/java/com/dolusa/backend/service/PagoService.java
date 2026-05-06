package com.dolusa.backend.service;

import com.dolusa.backend.model.*;
import com.dolusa.backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PagoService {
    private final PagoRepository pagoRepository;
    private final ReservaRepository reservaRepository;
    private final MetodoPagoRepository metodoPagoRepository;
    private final EstadoReservaRepository estadoReservaRepository;

    @Transactional
    public Integer registrarPago(Integer idReserva, Integer idMetodo, BigDecimal monto) {
        Reserva reserva = reservaRepository.findById(idReserva)
                .orElseThrow(() -> new IllegalArgumentException("Reserva no encontrada"));

        if (pagoRepository.findByReserva_IdReserva(idReserva).isPresent()) {
            throw new IllegalStateException("La reserva ya tiene un pago registrado");
        }

        MetodoPago metodo = metodoPagoRepository.findById(idMetodo)
                .orElseThrow(() -> new IllegalArgumentException("Metodo de pago no encontrado"));

        Pago p = new Pago();
        p.setReserva(reserva);
        p.setMetodo(metodo);
        p.setMonto(monto);
        p.setEstadoPago(Pago.EstadoPago.completado);
        p.setComprobante(generarComprobante());
        p.setFechaPago(LocalDateTime.now());
        Pago saved = pagoRepository.save(p);

        // Confirmar reserva (equivalente al JDBC: id_estado = 2)
        EstadoReserva confirmada = estadoReservaRepository.findByNombre("confirmada")
                .orElseThrow(() -> new IllegalStateException("Estado 'confirmada' no existe"));
        reserva.setEstado(confirmada);
        reservaRepository.save(reserva);

        return saved.getIdPago();
    }

    public Pago obtenerPorReserva(Integer idReserva) {
        return pagoRepository.findByReserva_IdReserva(idReserva).orElse(null);
    }

    public BigDecimal recaudacionPorEvento(Integer idEvento) {
        return pagoRepository.recaudacionPorEvento(idEvento);
    }

    public List<Object[]> resumenPorMetodo(Integer idEvento) {
        return pagoRepository.resumenPorMetodo(idEvento);
    }

    private String generarComprobante() {
        // Better uniqueness than currentTimeMillis
        return "DOL-" + UUID.randomUUID();
    }
}
