package com.dolusa.backend.service;

import com.dolusa.backend.model.UsuarioSistema;
import com.dolusa.backend.repository.UsuarioSistemaRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService implements UserDetailsService {

    private final UsuarioSistemaRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioSistemaRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UsuarioSistema usuario = repository.findByUsuario(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + usuario.getRol().name());
        return User.builder()
                .username(usuario.getUsuario())
                .password(usuario.getPassword())
                .authorities(List.of(authority))
                .accountExpired(false)
                .accountLocked(!usuario.getActivo())
                .credentialsExpired(false)
                .disabled(!usuario.getActivo())
                .build();
    }

    // Helper to create a user with encoded password
    public UsuarioSistema createUsuario(UsuarioSistema u) {
        // Business validation: unique username
        if (repository.findByUsuario(u.getUsuario()).isPresent()) {
            throw new IllegalArgumentException("El nombre de usuario ya existe");
        }
        u.setPassword(passwordEncoder.encode(u.getPassword()));
        return repository.save(u);
    }

    public java.util.List<UsuarioSistema> listAll() {
        return repository.findAll();
    }

    public UsuarioSistema updateUsuario(Integer id, UsuarioSistema u) {
        UsuarioSistema existing = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        // if username changed, check uniqueness
        if (!existing.getUsuario().equals(u.getUsuario()) && repository.findByUsuario(u.getUsuario()).isPresent()) {
            throw new IllegalArgumentException("El nombre de usuario ya existe");
        }
        existing.setNombre(u.getNombre());
        existing.setUsuario(u.getUsuario());
        if (u.getPassword() != null && !u.getPassword().isEmpty()) {
            existing.setPassword(passwordEncoder.encode(u.getPassword()));
        }
        existing.setRol(u.getRol());
        existing.setActivo(u.getActivo());
        return repository.save(existing);
    }

    public void deleteUsuario(Integer id) {
        UsuarioSistema existing = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        existing.setActivo(false);
        repository.save(existing);
    }

    public UsuarioSistema findById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    public UsuarioSistema updateUsuario(UsuarioSistema u) {
        // if username changed, ensure uniqueness
        repository.findByUsuario(u.getUsuario()).ifPresent(existing -> {
            if (!existing.getIdUsuario().equals(u.getIdUsuario())) {
                throw new IllegalArgumentException("El nombre de usuario ya existe");
            }
        });
        // if password looks like plain text (not bcrypt) encode it
        String pw = u.getPassword();
        if (pw != null && !(pw.startsWith("$2a$") || pw.startsWith("$2b$") || pw.startsWith("$2y$"))) {
            u.setPassword(passwordEncoder.encode(pw));
        }
        return repository.save(u);
    }

    public boolean deleteUsuario(Integer id) {
        if (!repository.existsById(id)) return false;
        repository.deleteById(id);
        return true;
    }
}
