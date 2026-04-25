package com.dolusa.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "qr_accesos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QRAcceso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_reserva", nullable = false)
    private Reserva reserva;

    @Column(name = "codigo_qr", nullable = false, length = 255)
    private String codigoQr;

    private String estado;

    @Column(name = "fecha_gen")
    private LocalDateTime fechaGen = LocalDateTime.now();

    @Column(name = "fecha_uso")
    private LocalDateTime fechaUso;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_staff")
    private Usuario staff;
}
