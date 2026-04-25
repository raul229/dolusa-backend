package com.dolusa.backend.service;

import com.dolusa.backend.model.*;
import com.dolusa.backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;

    public Usuario crearUsuario(Usuario usuario) {
        // Aquí se debería encriptar la contraseña usando BCrypt
        return usuarioRepository.save(usuario);
    }

    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }
}
