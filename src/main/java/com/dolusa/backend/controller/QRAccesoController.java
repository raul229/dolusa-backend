package com.dolusa.backend.controller;

import com.dolusa.backend.model.QRAcceso;
import com.dolusa.backend.service.QRAccesoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/qr")
@RequiredArgsConstructor
public class QRAccesoController {
    private final QRAccesoService qrService;

    @PostMapping("/validar")
    public ResponseEntity<QRAcceso> validarAcceso(
            @RequestParam String codigo, 
            @RequestParam Long staffId) {
        return ResponseEntity.ok(qrService.validarAcceso(codigo, staffId));
    }
}
