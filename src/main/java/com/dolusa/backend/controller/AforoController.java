package com.dolusa.backend.controller;

import com.dolusa.backend.model.AforoLog;
import com.dolusa.backend.service.AforoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/aforo")
@RequiredArgsConstructor
public class AforoController {
    private final AforoService aforoService;

    @PostMapping("/registrar")
    public ResponseEntity<String> registrarAforo(
            @RequestParam Long eventoId, 
            @RequestParam Long zonaId, 
            @RequestParam Integer personas) {
        aforoService.registrarAforo(eventoId, zonaId, personas);
        return ResponseEntity.ok("Aforo registrado correctamente");
    }
}
