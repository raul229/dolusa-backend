package com.dolusa.backend.service;

import com.dolusa.backend.model.*;
import com.dolusa.backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservaService {
    private final ReservaRepository reservaRepository;
    private final MesaRepository mesaRepository;
    private final EventoRepository eventoRepository;
    private final UsuarioRepository usuarioRepository;

    @Transactional
    public Reserva crearReserva(Reserva reserva) {
        // 1. Validar Usuario activo
        Usuario usuario = usuarioRepository.findById(reserva.getUsuario().getId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        if (!usuario.isActivo()) throw new RuntimeException("Usuario inactivo");

        // 2. Validar Evento activo y Aforo
        Evento evento = eventoRepository.findById(reserva.getEvento().getId())
                .orElseThrow(() -> new RuntimeException("Evento no encontrado"));
        if (!evento.isActivo()) throw new RuntimeException("Evento no disponible");

        // 3. Validar Disponibilidad de Mesa (Lógica simplificada)
        // En la vida real, aquí buscaríamos si la mesa tiene otra reserva el mismo día/hora
        Mesa mesa = mesaRepository.findById(reserva.getMesa().getId())
                .orElseThrow(() -> new RuntimeException("Mesa no encontrada"));
        
        if ("OCUPADO".equalsIgnoreCase(mesa.getEstado())) {
            throw new RuntimeException("La mesa ya está marcada como ocupada");
        }

        // 4. Calcular total automáticamente basado en el cover del evento y num personas
        BigDecimal total = evento.getPrecioCover().multiply(BigDecimal.valueOf(reserva.getNumPersonas()));
        reserva.setTotalPago(total);

        return reservaRepository.save(reserva);
    }

    public List<Reserva> listarReservas() {
        return reservaRepository.findAll();
    }
}
