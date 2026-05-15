package com.dolusa.backend.controller.dto;

import com.dolusa.backend.model.Evento;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class EventoUpsertRequest {
    @NotBlank
    private String nombre;

    @NotNull
    private LocalDate fecha;

    @NotNull
    @JsonProperty("hora_inicio")
    private LocalTime horaInicio;

    @NotNull
    @JsonProperty("hora_fin")
    private LocalTime horaFin;

    private String descripcion;

    private Evento.EstadoEvento estado;
}
