package com.transporte.cl.service;

import com.transporte.cl.models.Bitacora;

import java.util.List;

public interface BitacoraService {

    List<Object[]> getByidControlFlota(String idControlFlota);
    Bitacora createBitacora(Bitacora bitacora);

}
