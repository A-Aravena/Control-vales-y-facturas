package com.transporte.cl.models.DAO.ControlFlota;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class Pendiente {
    private String tiempodemora;
    private BigDecimal cantidad;
}
