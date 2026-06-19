package com.dolusa.backend.controller;

import com.dolusa.backend.controller.dto.UsuarioCreateRequest;
import com.dolusa.backend.model.UsuarioSistema;
import com.dolusa.backend.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public ResponseEntity<List<UsuarioSistema>> listar() {
        // For simplicity return entities but without exposing passwords in JSON (JPA entity has password field)
        // We will null the password before returning
        List<UsuarioSistema> list = usuarioService.listAll();
        list.forEach(u -> u.setPassword(null));
        return ResponseEntity.ok(list);
    }

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody UsuarioCreateRequest req) {
        try {
            UsuarioSistema u = new UsuarioSistema();
            u.setNombre(req.nombre());
            u.setUsuario(req.usuario());
            u.setPassword(req.password());
            u.setRol(UsuarioSistema.RolUsuario.valueOf(req.rol()));
            u.setActivo(req.activo());
            UsuarioSistema creado = usuarioService.createUsuario(u);
            creado.setPassword(null);
            return ResponseEntity.status(HttpStatus.CREATED).body(creado);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Integer id, @Valid @RequestBody UsuarioCreateRequest req) {
        try {
            UsuarioSistema u = new UsuarioSistema();
            u.setNombre(req.nombre());
            u.setUsuario(req.usuario());
            u.setPassword(req.password());
            u.setRol(UsuarioSistema.RolUsuario.valueOf(req.rol()));
            u.setActivo(req.activo());
            UsuarioSistema updated = usuarioService.updateUsuario(id, u);
            updated.setPassword(null);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        try {
            usuarioService.deleteUsuario(id);
            return ResponseEntity.ok(Map.of("deleted", true));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @PreAuthorize("hasRole('admin')")
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Integer id, @Valid @RequestBody UsuarioUpdateRequest req) {
        try {
            UsuarioSistema existing = usuarioService.findById(id);
            if (existing == null) return ResponseEntity.notFound().build();
            existing.setNombre(req.nombre());
            existing.setUsuario(req.usuario());
            if (req.password() != null && !req.password().isBlank()) existing.setPassword(req.password());
            existing.setRol(UsuarioSistema.RolUsuario.valueOf(req.rol()));
            existing.setActivo(req.activo());
            UsuarioSistema saved = usuarioService.updateUsuario(existing);
            saved.setPassword(null);
            return ResponseEntity.ok(saved);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @PreAuthorize("hasRole('admin')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        boolean ok = usuarioService.deleteUsuario(id);
        if (!ok) return ResponseEntity.notFound().build();
        return ResponseEntity.ok().build();
    }
}
