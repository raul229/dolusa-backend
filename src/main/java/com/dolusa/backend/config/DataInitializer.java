package com.dolusa.backend.config;

import com.dolusa.backend.model.UsuarioSistema;
import com.dolusa.backend.repository.UsuarioSistemaRepository;
import com.dolusa.backend.service.UsuarioService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UsuarioSistemaRepository usuarioRepo;
    private final UsuarioService usuarioService;

    public DataInitializer(UsuarioSistemaRepository usuarioRepo, UsuarioService usuarioService) {
        this.usuarioRepo = usuarioRepo;
        this.usuarioService = usuarioService;
    }

    @Override
    public void run(String... args) throws Exception {
        // Create default admin if not present
        String defaultUsuario = "admin";
        usuarioRepo.findByUsuario(defaultUsuario).ifPresentOrElse(existing -> {
            String pw = existing.getPassword();
            // If stored password does not look like BCrypt, re-encode it
            if (pw == null || !(pw.startsWith("$2a$") || pw.startsWith("$2b$") || pw.startsWith("$2y$"))) {
                existing.setPassword("admin123"); // plain password to be encoded by createUsuario
                usuarioService.createUsuario(existing);
                System.out.println("Updated existing user 'admin' password to BCrypt encoding (default: admin123)");
            }
        }, () -> {
            UsuarioSistema admin = new UsuarioSistema();
            admin.setNombre("Administrador");
            admin.setUsuario(defaultUsuario);
            admin.setPassword("admin123"); // will be encoded by createUsuario
            admin.setRol(UsuarioSistema.RolUsuario.admin);
            admin.setActivo(true);
            usuarioService.createUsuario(admin);
            System.out.println("Created default admin user 'admin' with password 'admin123'");
        });
    }
}
