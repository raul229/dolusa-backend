package com.dolusa.backend.controller;

import com.dolusa.backend.model.Rol;
import com.dolusa.backend.service.UsuarioService;
import com.dolusa.backend.repository.RolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RolController {
    private final RolRepository rolRepository;

    @GetMapping
    public ResponseEntity<List<Rol>> listarRoles() {
        return ResponseEntity.ok(rolRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<Rol> crearRol(@RequestBody Rol rol) {
        return ResponseEntity.ok(rolRepository.save(rol));
    }
}
