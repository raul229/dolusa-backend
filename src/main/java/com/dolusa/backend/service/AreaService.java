package com.dolusa.backend.service;

import com.dolusa.backend.model.Area;
import com.dolusa.backend.model.TipoArea;
import com.dolusa.backend.repository.AreaRepository;
import com.dolusa.backend.repository.ReservaRepository;
import com.dolusa.backend.repository.TipoAreaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AreaService {
    private final AreaRepository areaRepository;
    private final TipoAreaRepository tipoAreaRepository;
    private final ReservaRepository reservaRepository;

    public List<Area> listar() {
        return areaRepository.findAll();
    }

    public Area obtener(Integer id) {
        return areaRepository.findById(id).orElse(null);
    }

    public boolean disponible(Integer idArea, Integer idEvento) {
        if (idEvento == null) throw new IllegalArgumentException("Parametro 'evento' es requerido");
        return !reservaRepository.existeReservaActivaParaAreaEnEvento(idEvento, idArea);
    }

    @Transactional
    public Integer crear(Area a, Integer idTipo) {
        TipoArea tipo = tipoAreaRepository.findById(idTipo)
                .orElseThrow(() -> new IllegalArgumentException("Tipo de area no encontrado"));
        a.setIdArea(null);
        a.setTipo(tipo);
        if (a.getEstado() == null) a.setEstado(Area.EstadoArea.activa);
        return areaRepository.save(a).getIdArea();
    }

    @Transactional
    public boolean actualizar(Integer id, Area data, Integer idTipo) {
        Area a = areaRepository.findById(id).orElse(null);
        if (a == null) return false;

        if (idTipo != null) {
            TipoArea tipo = tipoAreaRepository.findById(idTipo)
                    .orElseThrow(() -> new IllegalArgumentException("Tipo de area no encontrado"));
            a.setTipo(tipo);
        }
        a.setNombre(data.getNombre());
        a.setPiso(data.getPiso());
        a.setCapacidad(data.getCapacidad());
        a.setPrecioBase(data.getPrecioBase());
        a.setReservable(data.getReservable());
        if (data.getEstado() != null) a.setEstado(data.getEstado());
        areaRepository.save(a);
        return true;
    }

    @Transactional
    public boolean desactivar(Integer id) {
        Area a = areaRepository.findById(id).orElse(null);
        if (a == null) return false;
        a.setEstado(Area.EstadoArea.inactiva);
        areaRepository.save(a);
        return true;
    }
}
