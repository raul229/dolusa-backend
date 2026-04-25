package com.dolusa.backend.service;

import com.dolusa.backend.model.*;
import com.dolusa.backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AforoService {
    private final AforoLogRepository aforoLogRepository;

    public void registrarAforo(Long eventoId, Long zonaId, Integer personasDentro) {
        AforoLog log = new AforoLog();
        // Aquí se asignarían las entidades Evento y Zona buscando sus IDs
        log.setPersonasDentro(personasDentro);
        log.setRegistradoEn(LocalDateTime.now());
        
        // Ejemplo de alerta si llega al 90% (suponiendo capacidad de zona de 100)
        if (personasDentro >= 90) {
            log.setAlerta(true);
            log.setPorcentaje(new BigDecimal("90.00"));
        }

        aforoLogRepository.save(log);
    }
}
