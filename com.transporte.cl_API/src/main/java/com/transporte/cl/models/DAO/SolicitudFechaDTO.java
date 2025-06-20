package com.transporte.cl.models.DAO;

import java.util.Date;

public class SolicitudFechaDTO {
    private Long cantidad;
    private Date dateSolicitud;

    public SolicitudFechaDTO() {
    }

    public SolicitudFechaDTO(Long cantidad, Date dateSolicitud) {
        this.cantidad = cantidad;
        this.dateSolicitud = dateSolicitud;
    }

    public Long getCantidad() {
        return cantidad;
    }

    public void setCantidad(Long cantidad) {
        this.cantidad = cantidad;
    }

    public Date getDateSolicitud() {
        return dateSolicitud;
    }

    public void setDateSolicitud(Date dateSolicitud) {
        this.dateSolicitud = dateSolicitud;
    }
}
