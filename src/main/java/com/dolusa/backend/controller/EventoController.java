package com.dolusa.backend.controller;

import com.dolusa.backend.controller.dto.EventoAreasAssignRequest;
import com.dolusa.backend.controller.dto.EventoUpsertRequest;
import com.dolusa.backend.model.Area;
import com.dolusa.backend.model.Evento;
import com.dolusa.backend.model.EventoArea;
import com.dolusa.backend.service.EventoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/eventos")
@RequiredArgsConstructor
public class EventoController {
    private final EventoService eventoService;

    @GetMapping
    public ResponseEntity<List<Evento>> listar() {
        return ResponseEntity.ok(eventoService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Evento> obtener(@PathVariable Integer id) {
        Evento e = eventoService.obtener(id);
        return e == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(e);
    }

    @GetMapping("/{id}/areas")
    public ResponseEntity<List<EventoArea>> areas(@PathVariable Integer id) {
        return ResponseEntity.ok(eventoService.areasPorEvento(id));
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> crear(@Valid @RequestBody EventoUpsertRequest req) {
        Evento e = new Evento();
        e.setNombre(req.getNombre());
        e.setFecha(req.getFecha());
        e.setHoraInicio(req.getHoraInicio());
        e.setHoraFin(req.getHoraFin());
        e.setDescripcion(req.getDescripcion());
        e.setEstado(req.getEstado());
        Integer id = eventoService.crear(e);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("id_evento", id));
    }

    // PDF: POST /api/eventos/{id}/areas
    @PostMapping("/{id}/areas")
    public ResponseEntity<Map<String, Object>> asignarAreas(@PathVariable Integer id, @Valid @RequestBody EventoAreasAssignRequest req) {
        List<EventoArea> items = req.getAreas().stream().map(it -> {
            EventoArea ea = new EventoArea();
            ea.setArea(new Area(it.getIdArea(), null, null, null, null, null, null, null));
            ea.setPrecioEspecial(it.getPrecioEspecial());
            return ea;
        }).toList();
        eventoService.asignarAreas(id, items);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("asignado", true));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> actualizar(@PathVariable Integer id, @Valid @RequestBody EventoUpsertRequest req) {
        Evento e = new Evento();
        e.setNombre(req.getNombre());
        e.setFecha(req.getFecha());
        e.setHoraInicio(req.getHoraInicio());
        e.setHoraFin(req.getHoraFin());
        e.setDescripcion(req.getDescripcion());
        e.setEstado(req.getEstado());
        boolean ok = eventoService.actualizar(id, e);
        return ResponseEntity.ok(Map.of("actualizado", ok));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> cancelar(@PathVariable Integer id) {
        boolean ok = eventoService.cancelar(id);
        return ResponseEntity.ok(Map.of("cancelado", ok));
    }
}
