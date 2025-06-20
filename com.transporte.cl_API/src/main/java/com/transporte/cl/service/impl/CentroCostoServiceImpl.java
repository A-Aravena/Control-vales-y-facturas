package com.transporte.cl.service.impl;

import com.transporte.cl.models.CentroCosto;
import com.transporte.cl.repository.CentroCostoRepository;
import com.transporte.cl.service.CentroCostoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CentroCostoServiceImpl implements CentroCostoService {

    @Autowired
    private CentroCostoRepository centroCostoRepository;

    @Override
    public List<CentroCosto> getAll() {
        return centroCostoRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    @Override
    public CentroCosto getById(Long id) {
        return centroCostoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Centro de costo no encontrado"));
    }

    @Override
    public CentroCosto create(CentroCosto centroCosto) {
        centroCosto.setEstado(true);
        return centroCostoRepository.save(centroCosto);
    }

    @Override
    public CentroCosto update(Long id, CentroCosto centroCosto) {
        CentroCosto costoUpdate = centroCostoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Centro de costo no encontrado"));
        costoUpdate.setNombre(centroCosto.getNombre());
        return centroCostoRepository.save(costoUpdate);
    }

    @Override
    public CentroCosto updateEstado(Long id) {
        CentroCosto costoUpdate = centroCostoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Centro de costo no encontrado"));
        costoUpdate.setEstado(!costoUpdate.isEstado());
        return centroCostoRepository.save(costoUpdate);
    }

    @Override
    public List<CentroCosto> getEnable() {
        return centroCostoRepository.getEnable();
    }
}
