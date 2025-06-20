package com.transporte.cl.models.DAO;

public class SolicitudGerenteDTO {
    private Long cantidad;

    private String solicitante;

    public SolicitudGerenteDTO(Long cantidad, String solicitante) {
        this.cantidad = cantidad;
        this.solicitante = solicitante;
    }

    public Long getCantidad() {
        return cantidad;
    }

    public void setCantidad(Long cantidad) {
        this.cantidad = cantidad;
    }

    public String getSolicitante() {
        return solicitante;
    }

    public void setSolicitante(String solicitante) {
        this.solicitante = solicitante;
    }
}
