package com.dolusa.backend.controller;

import com.dolusa.backend.service.ReporteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/reportes")
@RequiredArgsConstructor
public class ReporteController {
    private final ReporteService reporteService;

    // PDF: GET /api/reportes/resumen?evento={id}
    @GetMapping("/resumen")
    public ResponseEntity<Map<String, Object>> resumen(@RequestParam("evento") Integer idEvento) {
        return ResponseEntity.ok(reporteService.resumen(idEvento));
    }
}
