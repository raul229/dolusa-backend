package com.dolusa.backend.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReservaEstadoUpdateRequest {
    @NotNull
    @JsonProperty("id_estado")
    private Integer idEstado;
}
