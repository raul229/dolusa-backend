package com.dolusa.backend.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class EventoAreasAssignRequest {
    @NotEmpty
    @Valid
    private List<Item> areas;

    @Getter
    @Setter
    public static class Item {
        @NotNull
        @JsonProperty("id_area")
        private Integer idArea;

        @JsonProperty("precio_especial")
        private BigDecimal precioEspecial;
    }
}
