package com.transporte.cl.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "centrocostos")
@Data
public class CentroCosto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 20)
    private String nombre;
    private Boolean estado;
    public boolean isEstado() {
        return estado;
    }
}
