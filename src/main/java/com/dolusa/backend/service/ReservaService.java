package com.dolusa.backend.service;

import com.dolusa.backend.model.*;
import com.dolusa.backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservaService {
    private final ReservaRepository reservaRepository;
    private final EstadoReservaRepository estadoReservaRepository;
    private final HistorialReservaRepository historialReservaRepository;

    private final ClienteRepository clienteRepository;
    private final EventoRepository eventoRepository;
    private final AreaRepository areaRepository;
    private final UsuarioSistemaRepository usuarioSistemaRepository;

    public List<Reserva> listar() {
        return reservaRepository.findAllByOrderByCreadoEnDesc();
    }

    public List<Reserva> listarPorEvento(Integer idEvento) {
        return reservaRepository.findByEvento_IdEventoOrderByCreadoEnDesc(idEvento);
    }

    public Reserva obtener(Integer idReserva) {
        return reservaRepository.findById(idReserva).orElse(null);
    }

    @Transactional
    public Integer crear(Reserva input) {
        // Load required refs to ensure FK integrity
        Cliente cliente = clienteRepository.findById(input.getCliente().getIdCliente())
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));
        Evento evento = eventoRepository.findById(input.getEvento().getIdEvento())
                .orElseThrow(() -> new IllegalArgumentException("Evento no encontrado"));
        Area area = areaRepository.findById(input.getArea().getIdArea())
                .orElseThrow(() -> new IllegalArgumentException("Area no encontrada"));
        UsuarioSistema usuario = usuarioSistemaRepository.findById(input.getUsuario().getIdUsuario())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        EstadoReserva pendiente = estadoReservaRepository.findByNombre("pendiente")
                .orElseThrow(() -> new IllegalStateException("Estado 'pendiente' no existe"));

        Reserva r = new Reserva();
        r.setCliente(cliente);
        r.setEvento(evento);
        r.setArea(area);
        r.setUsuario(usuario);
        r.setEstado(pendiente);
        r.setCantidadPersonas(input.getCantidadPersonas() == null ? (short) 1 : input.getCantidadPersonas());
        r.setObservacion(input.getObservacion());
        r.setCreadoEn(LocalDateTime.now());

        Reserva saved = reservaRepository.save(r);

        // Audit initial state
        HistorialReserva h = new HistorialReserva();
        h.setReserva(saved);
        h.setEstadoAnterior(null);
        h.setEstadoNuevo(pendiente);
        h.setUsuario(usuario);
        h.setFechaCambio(LocalDateTime.now());
        h.setNota("Estado inicial");
        historialReservaRepository.save(h);

        return saved.getIdReserva();
    }

    @Transactional
    public boolean cambiarEstado(Integer idReserva, Integer idEstadoNuevo) {
        Reserva r = reservaRepository.findById(idReserva).orElse(null);
        if (r == null) return false;

        EstadoReserva nuevo = estadoReservaRepository.findById(idEstadoNuevo)
                .orElseThrow(() -> new IllegalArgumentException("Estado no encontrado"));

        EstadoReserva anterior = r.getEstado();
        r.setEstado(nuevo);
        reservaRepository.save(r);

        HistorialReserva h = new HistorialReserva();
        h.setReserva(r);
        h.setEstadoAnterior(anterior);
        h.setEstadoNuevo(nuevo);
        h.setUsuario(r.getUsuario());
        h.setFechaCambio(LocalDateTime.now());
        h.setNota("Cambio de estado via API");
        historialReservaRepository.save(h);

        return true;
    }

    @Transactional
    public boolean eliminar(Integer idReserva) {
        Reserva r = reservaRepository.findById(idReserva).orElse(null);
        if (r == null) return false;
        if (r.getEstado() == null || r.getEstado().getNombre() == null) {
            throw new IllegalStateException("Estado de reserva invalido");
        }
        if (!"pendiente".equalsIgnoreCase(r.getEstado().getNombre())) {
            throw new IllegalStateException("Solo se puede eliminar una reserva pendiente");
        }
        reservaRepository.deleteById(idReserva);
        return true;
    }
}
