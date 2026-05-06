package com.dolusa.backend.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PagoCreateRequest {
    @NotNull
    @JsonProperty("id_reserva")
    private Integer idReserva;

    @NotNull
    @JsonProperty("id_metodo")
    private Integer idMetodo;

    @NotNull
    private BigDecimal monto;
}
