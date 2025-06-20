package com.transporte.cl.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Bitacora")
@Data
public class Bitacora {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fecha;
    private String responsable;
    private String actividad;
    private String idControlFlota;
    private String comentarios;

}
