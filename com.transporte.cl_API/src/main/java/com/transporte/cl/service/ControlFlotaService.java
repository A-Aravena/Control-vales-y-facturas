package com.transporte.cl.service;

import com.transporte.cl.models.CentroCosto;
import com.transporte.cl.models.ControlFlota;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ControlFlotaService {

    List<ControlFlota> uploadFromExcel(MultipartFile file) throws IOException;

    List<ControlFlota> getAll();

    List<Object[]> findClienteDistinct();
    List<Object[]> findEmpresaDistinct();

    List<Object[]> getFiltroClienteALL();
    List<Object[]> getControlFlotaInva(String dtp, String clientep ,String nrolegalp, String estadofinalp, String cantCajasP, String descSolicitantep
                                       ,String fafatrealp, String confpodp, String fdescargap, String empresa, String empresa_);

    List<Object[]> getFiltroClienteUnilever(String empresa);

    List<Object[]> getFiltroClienteOtros(String nomclic, String empresa);
    List<Object[]> getPendientes(String clientep, String empresa);


    List<Object[]> getBitacora(String idControlFlota);

    void imagenFacturaURL(String nro_legal, String cliente,MultipartFile imageFile, String usuario) throws IOException;

    void imagenValePalletURL(String nro_legal, String cliente,MultipartFile imageFile,String usuario) throws IOException;

     void actualizarFechaPODinva(String nro_legal, String newPOD) ;
    void actualizarFechaPODCF(String nro_legal, String newPOD) ;
    void Conformarinva(String nro_legal, String usuario);
    void ConformarCF(String nro_legal, String usuario);
    void actualizarestadoinva(String nro_legal);
    void actualizarestadoCF(String nro_legal);

     ControlFlota updateEstado(Long id);


}
