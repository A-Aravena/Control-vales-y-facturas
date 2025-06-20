package com.transporte.cl.service.impl;

import com.transporte.cl.models.Bitacora;
import com.transporte.cl.repository.BitacoraRepository;
import com.transporte.cl.service.BitacoraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.List;

@Service
public class BitacoraServiceImpl implements BitacoraService {
    @Autowired
    private BitacoraRepository bitacoraRepository;

    @Override
    public List<Object[]> getByidControlFlota(String idControlFlota) {
        return bitacoraRepository.getHistorialBitacora(idControlFlota);
    }

    @Override
    public Bitacora createBitacora(Bitacora bitacora) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        bitacora.setFecha(LocalDateTime.now().format(formatter));
        return bitacoraRepository.save(bitacora);
    }
}
