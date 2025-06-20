package com.transporte.cl.service;

import com.transporte.cl.models.CentroCosto;

import java.util.List;
import java.util.Optional;

public interface CentroCostoService {
    List<CentroCosto> getAll();
    CentroCosto getById(Long id);
    CentroCosto create(CentroCosto centroCosto);
    CentroCosto update(Long id, CentroCosto centroCosto);
    CentroCosto updateEstado(Long id);
    List<CentroCosto> getEnable();
}