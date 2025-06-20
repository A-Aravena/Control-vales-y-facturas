package com.transporte.cl.models.DAO.ControlFlota;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class FiltroCliente {

    private BigDecimal conformado;
    private BigDecimal incompleto;
    private BigDecimal nopresentado;


}
