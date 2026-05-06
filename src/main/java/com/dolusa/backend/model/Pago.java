package com.dolusa.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "pago")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pago {
    public enum EstadoPago {pendiente, completado, anulado}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pago")
    private Integer idPago;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_reserva", nullable = false, unique = true)
    private Reserva reserva;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_metodo", nullable = false)
    private MetodoPago metodo;

    @Column(nullable = false, precision = 8, scale = 2)
    private BigDecimal monto;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_pago", nullable = false)
    private EstadoPago estadoPago;

    @Column(nullable = false, length = 50)
    private String comprobante;

    @Column(name = "fecha_pago", nullable = false)
    private LocalDateTime fechaPago;
}
