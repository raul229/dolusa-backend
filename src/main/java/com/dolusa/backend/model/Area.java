package com.dolusa.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "area")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Area {
    public enum EstadoArea {activa, inactiva}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_area")
    private Integer idArea;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_tipo", nullable = false)
    private TipoArea tipo;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false)
    private Byte piso;

    @Column(nullable = false)
    private Short capacidad;

    @Column(name = "precio_base", nullable = false, precision = 8, scale = 2)
    private BigDecimal precioBase;

    @Column(nullable = false)
    private Boolean reservable;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoArea estado;
}
