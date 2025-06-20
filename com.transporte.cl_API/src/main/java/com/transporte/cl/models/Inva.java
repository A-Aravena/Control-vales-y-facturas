package com.transporte.cl.models;

import jakarta.persistence.*;
import lombok.Data;



@Entity
@Table(name = "Inva")
@Data
public class Inva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 20)
    private String estado;
    private String dt;
    private String nroEntrega;
    private String nroLegal;
    private String nroFactura;
    private String faFatReal;
    private String confPOD;
    private String lote;
    private String transportista;
    private String ctaProv;
    private String qFact;
    private String vNFact;
    private String destMerc;
    private String descSolicitante;
    private String poblacion;
    private String region;
    private String condPago;
    private String oVta;
    private String conDiferencias;
    private String estadofinal;
    private String facturaURL;
    private String valePalletURL;



}
