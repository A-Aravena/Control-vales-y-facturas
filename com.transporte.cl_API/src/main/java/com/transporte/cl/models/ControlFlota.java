package com.transporte.cl.models;

import jakarta.persistence.*;
import lombok.Data;



@Entity
@Table(name = "ControlFlota")
@Data

public class ControlFlota {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 20)
    private String cliente;
    private String shipment;
    private String tipo;
    private String clienteFinal;
    private String fRetiro;
    private String fDescarga;
    private String comuna;
    private String direccion;
    private String cantPallet;
    private String cantCajas;
    private String tipoCamion;
    private String nombreChofer;
    private String numeroTelefono;
    private String empresa;
    private String patenteCamion;
    private String patenteRampla;
    private String tara;
    private String estado;
    private String observacion;
    private String peonetaCantidad;
    private String repaletizado;
    private String sobreestadia;
    private String almacenamiento;
    private String feriado;
    private String retorno;
    private String redespacho;
    private String reagenda;
    private String estadoFinal;
    private String facturaURL;
    private String valePalletURL;
    private String confpod;

}
