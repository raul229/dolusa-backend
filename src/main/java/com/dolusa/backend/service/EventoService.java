package com.dolusa.backend.service;

import com.dolusa.backend.model.*;
import com.dolusa.backend.repository.AreaRepository;
import com.dolusa.backend.repository.EventoAreaRepository;
import com.dolusa.backend.repository.EventoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventoService {
    private final EventoRepository eventoRepository;
    private final AreaRepository areaRepository;
    private final EventoAreaRepository eventoAreaRepository;

    public List<Evento> listar() {
        return eventoRepository.findAll();
    }

    public Evento obtener(Integer id) {
        return eventoRepository.findById(id).orElse(null);
    }

    @Transactional
    public Integer crear(Evento e) {
        e.setIdEvento(null);
        if (e.getEstado() == null) e.setEstado(Evento.EstadoEvento.programado);
        e.setCreadoEn(LocalDateTime.now());
        return eventoRepository.save(e).getIdEvento();
    }

    @Transactional
    public boolean actualizar(Integer id, Evento data) {
        Evento e = eventoRepository.findById(id).orElse(null);
        if (e == null) return false;

        e.setNombre(data.getNombre());
        e.setFecha(data.getFecha());
        e.setHoraInicio(data.getHoraInicio());
        e.setHoraFin(data.getHoraFin());
        e.setDescripcion(data.getDescripcion());
        e.setEstado(data.getEstado());
        eventoRepository.save(e);
        return true;
    }

    @Transactional
    public boolean cancelar(Integer id) {
        Evento e = eventoRepository.findById(id).orElse(null);
        if (e == null) return false;
        e.setEstado(Evento.EstadoEvento.cancelado);
        eventoRepository.save(e);
        return true;
    }

    public List<EventoArea> areasPorEvento(Integer idEvento) {
        return eventoAreaRepository.findByEvento_IdEvento(idEvento);
    }

    @Transactional
    public void asignarAreas(Integer idEvento, List<EventoArea> items) {
        Evento evento = eventoRepository.findById(idEvento)
                .orElseThrow(() -> new IllegalArgumentException("Evento no encontrado"));

        for (EventoArea item : items) {
            Integer idArea = item.getArea().getIdArea();
            Area area = areaRepository.findById(idArea)
                    .orElseThrow(() -> new IllegalArgumentException("Area no encontrada: " + idArea));

            EventoArea ea = new EventoArea();
            ea.setEvento(evento);
            ea.setArea(area);
            ea.setId(new EventoAreaId(idEvento, idArea));
            ea.setPrecioEspecial(item.getPrecioEspecial());
            eventoAreaRepository.save(ea);
        }
    }
}
