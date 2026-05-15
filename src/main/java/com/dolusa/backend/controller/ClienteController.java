package com.dolusa.backend.controller;

import com.dolusa.backend.controller.dto.ClienteUpsertRequest;
import com.dolusa.backend.model.Cliente;
import com.dolusa.backend.service.ClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
public class ClienteController {
    private final ClienteService clienteService;

    @GetMapping
    public ResponseEntity<?> listar(@RequestParam(value = "dni", required = false) String dni) {
        if (dni != null) {
            Cliente c = clienteService.buscarPorDni(dni);
            return c == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(c);
        }
        List<Cliente> lista = clienteService.listar();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> obtener(@PathVariable Integer id) {
        Cliente c = clienteService.obtener(id);
        return c == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(c);
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> crear(@Valid @RequestBody ClienteUpsertRequest req) {
        Cliente c = new Cliente();
        c.setNombre(req.getNombre());
        c.setApellido(req.getApellido());
        c.setDni(req.getDni());
        c.setCelular(req.getCelular());
        c.setEmail(req.getEmail());
        Integer id = clienteService.crear(c);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("id_cliente", id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> actualizar(@PathVariable Integer id, @Valid @RequestBody ClienteUpsertRequest req) {
        Cliente c = new Cliente();
        c.setNombre(req.getNombre());
        c.setApellido(req.getApellido());
        c.setDni(req.getDni());
        c.setCelular(req.getCelular());
        c.setEmail(req.getEmail());
        boolean ok = clienteService.actualizar(id, c);
        return ResponseEntity.ok(Map.of("actualizado", ok));
    }
}
