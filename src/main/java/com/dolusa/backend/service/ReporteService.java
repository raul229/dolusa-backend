package com.dolusa.backend.service;

import com.dolusa.backend.model.Evento;
import com.dolusa.backend.repository.EventoRepository;
import com.dolusa.backend.repository.PagoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ReporteService {
    private final PagoRepository pagoRepository;
    private final EventoRepository eventoRepository;

    public Map<String, Object> recaudacionPorEvento(Integer idEvento) {
        Evento evento = eventoRepository.findById(idEvento)
                .orElseThrow(() -> new IllegalArgumentException("Evento no encontrado"));

        BigDecimal total = pagoRepository.recaudacionPorEvento(idEvento);
        long cantidad = pagoRepository.cantidadPagosCompletadosPorEvento(idEvento);

        List<Map<String, Object>> porMetodo = new ArrayList<>();
        for (Object[] row : pagoRepository.resumenPorMetodo(idEvento)) {
            porMetodo.add(Map.of(
                    "metodo", row[0],
                    "cantidad", row[1],
                    "total", row[2]
            ));
        }

        Map<String, Object> out = new HashMap<>();
        out.put("id_evento", evento.getIdEvento());
        out.put("nombre_evento", evento.getNombre());
        out.put("total_recaudado", total);
        out.put("total_reservas", cantidad);
        out.put("por_metodo", porMetodo);
        return out;
    }

    public Map<String, Object> resumen(Integer idEvento) {
        Map<String, Object> out = new HashMap<>();

        List<Map<String, Object>> porArea = new ArrayList<>();
        for (Object[] row : pagoRepository.resumenPorArea(idEvento)) {
            porArea.add(Map.of(
                    "area", row[0],
                    "cantidad", row[1],
                    "total", row[2]
            ));
        }

        List<Map<String, Object>> porMetodo = new ArrayList<>();
        for (Object[] row : pagoRepository.resumenPorMetodo(idEvento)) {
            porMetodo.add(Map.of(
                    "metodo", row[0],
                    "cantidad", row[1],
                    "total", row[2]
            ));
        }

        out.put("por_area", porArea);
        out.put("por_metodo", porMetodo);
        return out;
    }
}
