package com.transporte.cl.service;

import com.transporte.cl.models.Inva;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


public interface InvaService {

    List<Inva> uploadFromExcel(MultipartFile file) throws IOException;
    List<Inva> getAll();
    Inva actualizarPOD(Long id, String ConfPOD);
    Inva updateEstado(Long id,String nroLegal);

}
