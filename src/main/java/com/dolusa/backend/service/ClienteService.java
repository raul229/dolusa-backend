package com.dolusa.backend.service;

import com.dolusa.backend.model.Cliente;
import com.dolusa.backend.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteService {
    private final ClienteRepository clienteRepository;

    public List<Cliente> listar() {
        return clienteRepository.findAll();
    }

    public Cliente obtener(Integer id) {
        return clienteRepository.findById(id).orElse(null);
    }

    public Cliente buscarPorDni(String dni) {
        if (dni == null || dni.isBlank()) return null;
        return clienteRepository.findByDni(dni.trim()).orElse(null);
    }

    @Transactional
    public Integer crear(Cliente c) {
        if (c.getDni() == null || c.getDni().trim().length() != 8) {
            throw new IllegalArgumentException("DNI invalido");
        }
        if (clienteRepository.findByDni(c.getDni().trim()).isPresent()) {
            throw new IllegalStateException("Ya existe un cliente con ese DNI");
        }
        c.setIdCliente(null);
        c.setDni(c.getDni().trim());
        c.setCreadoEn(LocalDateTime.now());
        return clienteRepository.save(c).getIdCliente();
    }

    @Transactional
    public boolean actualizar(Integer id, Cliente data) {
        Cliente c = clienteRepository.findById(id).orElse(null);
        if (c == null) return false;

        // DNI se mantiene como identificador de negocio (y unique)
        c.setNombre(data.getNombre());
        c.setApellido(data.getApellido());
        c.setCelular(data.getCelular());
        c.setEmail(data.getEmail());
        clienteRepository.save(c);
        return true;
    }
}
