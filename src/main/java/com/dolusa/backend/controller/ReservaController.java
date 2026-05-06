package com.dolusa.backend.controller;

import com.dolusa.backend.controller.dto.ReservaCreateRequest;
import com.dolusa.backend.controller.dto.ReservaEstadoUpdateRequest;
import com.dolusa.backend.model.*;
import com.dolusa.backend.service.ReservaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reservas")
@RequiredArgsConstructor
public class ReservaController {
    private final ReservaService reservaService;

    @GetMapping
    public ResponseEntity<List<Reserva>> listar() {
        return ResponseEntity.ok(reservaService.listar());
    }

    @GetMapping("/evento/{idEvento}")
    public ResponseEntity<List<Reserva>> listarPorEvento(@PathVariable Integer idEvento) {
        return ResponseEntity.ok(reservaService.listarPorEvento(idEvento));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reserva> obtener(@PathVariable Integer id) {
        Reserva r = reservaService.obtener(id);
        return r == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(r);
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> crear(@Valid @RequestBody ReservaCreateRequest req) {
        Reserva r = new Reserva();
        r.setCliente(new Cliente(req.getIdCliente(), null, null, null, null, null, null));
        r.setEvento(new Evento(req.getIdEvento(), null, null, null, null, null, null, null));
        r.setArea(new Area(req.getIdArea(), null, null, null, null, null, null, null));
        r.setUsuario(new UsuarioSistema(req.getIdUsuario(), null, null, null, null, null));
        r.setCantidadPersonas(req.getCantidadPersonas().shortValue());
        r.setObservacion(req.getObservacion());

        Integer id = reservaService.crear(r);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("id_reserva", id));
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<Map<String, Object>> cambiarEstado(
            @PathVariable Integer id,
            @Valid @RequestBody ReservaEstadoUpdateRequest body
    ) {
        boolean ok = reservaService.cambiarEstado(id, body.getIdEstado());
        return ResponseEntity.ok(Map.of("actualizado", ok));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> eliminar(@PathVariable Integer id) {
        boolean ok = reservaService.eliminar(id);
        return ResponseEntity.ok(Map.of("eliminado", ok));
    }
}
