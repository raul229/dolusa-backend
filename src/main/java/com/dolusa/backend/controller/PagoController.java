package com.dolusa.backend.controller;

import com.dolusa.backend.controller.dto.PagoCreateRequest;
import com.dolusa.backend.controller.dto.PagoEstadoUpdateRequest;
import com.dolusa.backend.model.Pago;
import com.dolusa.backend.service.PagoService;
import com.dolusa.backend.service.ReporteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/pagos")
@RequiredArgsConstructor
public class PagoController {
    private final PagoService pagoService;
    private final ReporteService reporteService;

    @PostMapping
    public ResponseEntity<Map<String, Object>> registrar(@Valid @RequestBody PagoCreateRequest req) {
        Integer idPago = pagoService.registrarPago(req.getIdReserva(), req.getIdMetodo(), req.getMonto());
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("id_pago", idPago));
    }

    // PDF: GET /api/pagos/{id_reserva}
    @GetMapping("/{idReserva}")
    public ResponseEntity<Pago> obtenerPorReserva(@PathVariable Integer idReserva) {
        Pago p = pagoService.obtenerPorReserva(idReserva);
        return p == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(p);
    }

    // PDF: PUT /api/pagos/{id}/estado
    @PutMapping("/{id}/estado")
    public ResponseEntity<Map<String, Object>> actualizarEstado(
            @PathVariable Integer id,
            @Valid @RequestBody PagoEstadoUpdateRequest body
    ) {
        boolean ok = pagoService.actualizarEstado(id, body.getEstadoPago());
        return ResponseEntity.ok(Map.of("actualizado", ok));
    }

    // PDF: GET /api/pagos/recaudacion?evento={id}
    @GetMapping("/recaudacion")
    public ResponseEntity<Map<String, Object>> recaudacion(@RequestParam("evento") Integer idEvento) {
        return ResponseEntity.ok(reporteService.recaudacionPorEvento(idEvento));
    }
}
