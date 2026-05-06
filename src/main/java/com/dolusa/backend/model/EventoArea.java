package com.dolusa.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "evento_area")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventoArea {
    @EmbeddedId
    private EventoAreaId id;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("idEvento")
    @JoinColumn(name = "id_evento", nullable = false)
    private Evento evento;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("idArea")
    @JoinColumn(name = "id_area", nullable = false)
    private Area area;

    @Column(name = "precio_especial", precision = 8, scale = 2)
    private BigDecimal precioEspecial;
}
