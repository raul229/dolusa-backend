package com.dolusa.backend.controller.dto;

import com.dolusa.backend.model.Pago;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PagoEstadoUpdateRequest {
    @NotNull
    @JsonProperty("estado_pago")
    private Pago.EstadoPago estadoPago;
}
