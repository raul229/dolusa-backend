package com.dolusa.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "whatsapp_envios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WhatsappEnvio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_qr", nullable = false)
    private QRAcceso qrAcceso;

    @Column(length = 15)
    private String telefono;

    @Column(columnDefinition = "TEXT")
    private String mensaje;

    private String estado;

    private Integer intentos = 0;

    @Column(name = "fecha_envio")
    private LocalDateTime fechaEnvio;
}
