package com.dolusa.backend.controller.dto;

import com.dolusa.backend.model.Area;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class AreaUpsertRequest {
    @NotNull
    @JsonProperty("id_tipo")
    private Integer idTipo;

    @NotBlank
    private String nombre;

    @NotNull
    private Byte piso;

    @NotNull
    @Min(1)
    private Short capacidad;

    @NotNull
    @JsonProperty("precio_base")
    private BigDecimal precioBase;

    @NotNull
    private Boolean reservable;

    private Area.EstadoArea estado;
}
