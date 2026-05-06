package com.dolusa.backend.controller;

import com.dolusa.backend.controller.dto.PagoCreateRequest;
import com.dolusa.backend.model.Pago;
import com.dolusa.backend.service.PagoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/pagos")
@RequiredArgsConstructor
public class PagoController {
    private final PagoService pagoService;

    @PostMapping
    public ResponseEntity<Map<String, Object>> registrar(@Valid @RequestBody PagoCreateRequest req) {
        Integer idPago = pagoService.registrarPago(req.getIdReserva(), req.getIdMetodo(), req.getMonto());
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("id_pago", idPago));
    }

    @GetMapping("/reserva/{idReserva}")
    public ResponseEntity<Pago> obtenerPorReserva(@PathVariable Integer idReserva) {
        Pago p = pagoService.obtenerPorReserva(idReserva);
        return p == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(p);
    }

    @GetMapping("/evento/{idEvento}/recaudacion")
    public ResponseEntity<Map<String, Object>> recaudacionPorEvento(@PathVariable Integer idEvento) {
        BigDecimal total = pagoService.recaudacionPorEvento(idEvento);
        return ResponseEntity.ok(Map.of("total", total));
    }

    @GetMapping("/evento/{idEvento}/resumen-metodo")
    public ResponseEntity<List<Object[]>> resumenPorMetodo(@PathVariable Integer idEvento) {
        return ResponseEntity.ok(pagoService.resumenPorMetodo(idEvento));
    }
}
