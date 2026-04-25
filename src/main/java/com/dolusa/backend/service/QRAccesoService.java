package com.dolusa.backend.service;

import com.dolusa.backend.model.*;
import com.dolusa.backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QRAccesoService {
    private final QRAccesoRepository qrRepository;
    private final UsuarioRepository usuarioRepository;

    @Transactional
    public QRAcceso validarAcceso(String codigoQr, Long staffId) {
        QRAcceso qr = qrRepository.findByCodigoQr(codigoQr)
                .orElseThrow(() -> new RuntimeException("Código QR no válido"));

        if ("VALIDADO".equalsIgnoreCase(qr.getEstado())) {
            throw new RuntimeException("Este código ya fue utilizado");
        }

        Usuario staff = usuarioRepository.findById(staffId)
                .orElseThrow(() -> new RuntimeException("Staff no encontrado"));

        qr.setEstado("VALIDADO");
        qr.setFechaUso(LocalDateTime.now());
        qr.setStaff(staff);

        return qrRepository.save(qr);
    }
}
