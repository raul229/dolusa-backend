package com.dolusa.backend.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReservaCreateRequest {
    @NotNull
    @JsonProperty("id_cliente")
    private Integer idCliente;

    @NotNull
    @JsonProperty("id_evento")
    private Integer idEvento;

    @NotNull
    @JsonProperty("id_area")
    private Integer idArea;

    @NotNull
    @JsonProperty("id_usuario")
    private Integer idUsuario;

    @NotNull
    @Min(1)
    @JsonProperty("cantidad_personas")
    private Integer cantidadPersonas;

    private String observacion;
}
