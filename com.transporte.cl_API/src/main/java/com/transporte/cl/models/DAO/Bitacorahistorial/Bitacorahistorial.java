package com.transporte.cl.models.DAO.Bitacorahistorial;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Bitacorahistorial {
    private Long id;
    private String actividad;
    private String idControlFlota;
    private String fecha;
    private String responsable;
    private String comentarios;
}
