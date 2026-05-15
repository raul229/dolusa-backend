package com.dolusa.backend.controller;

import com.dolusa.backend.controller.dto.AreaUpsertRequest;
import com.dolusa.backend.model.Area;
import com.dolusa.backend.service.AreaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/areas")
@RequiredArgsConstructor
public class AreaController {
    private final AreaService areaService;

    @GetMapping
    public ResponseEntity<List<Area>> listar() {
        return ResponseEntity.ok(areaService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Area> obtener(@PathVariable Integer id) {
        Area a = areaService.obtener(id);
        return a == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(a);
    }

    // PDF: GET /api/areas/{id}/disponibilidad?evento={id}
    @GetMapping("/{id}/disponibilidad")
    public ResponseEntity<Map<String, Object>> disponibilidad(
            @PathVariable Integer id,
            @RequestParam("evento") Integer idEvento
    ) {
        boolean disponible = areaService.disponible(id, idEvento);
        return ResponseEntity.ok(Map.of("disponible", disponible));
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> crear(@Valid @RequestBody AreaUpsertRequest req) {
        Area a = new Area();
        a.setNombre(req.getNombre());
        a.setPiso(req.getPiso());
        a.setCapacidad(req.getCapacidad());
        a.setPrecioBase(req.getPrecioBase());
        a.setReservable(req.getReservable());
        a.setEstado(req.getEstado());
        Integer id = areaService.crear(a, req.getIdTipo());
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("id_area", id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> actualizar(@PathVariable Integer id, @Valid @RequestBody AreaUpsertRequest req) {
        Area a = new Area();
        a.setNombre(req.getNombre());
        a.setPiso(req.getPiso());
        a.setCapacidad(req.getCapacidad());
        a.setPrecioBase(req.getPrecioBase());
        a.setReservable(req.getReservable());
        a.setEstado(req.getEstado());
        boolean ok = areaService.actualizar(id, a, req.getIdTipo());
        return ResponseEntity.ok(Map.of("actualizado", ok));
    }

    // PDF: DELETE desactiva (estado=inactiva)
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> desactivar(@PathVariable Integer id) {
        boolean ok = areaService.desactivar(id);
        return ResponseEntity.ok(Map.of("desactivado", ok));
    }
}
