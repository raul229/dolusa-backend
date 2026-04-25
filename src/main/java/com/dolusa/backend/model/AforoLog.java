package com.dolusa.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "aforo_logs")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AforoLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_evento", nullable = false)
    private Evento evento;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_zona", nullable = false)
    private Zona zona;

    @Column(name = "personas_dentro")
    private Integer personasDentro;

    @Column(precision = 5, scale = 2)
    private BigDecimal porcentaje;

    private boolean alerta;

    @Column(name = "registrado_en")
    private LocalDateTime registradoEn = LocalDateTime.now();
}
