package com.transporte.cl.models.DAO;

public class SolicitudDetalleDTO {
    private String solicitante;
    private String motivo;
    private Long solicitudes;
    private Long cantidad;
    private Long aprobado;
    private Long rechazado;
    private Long pendiente;

    public SolicitudDetalleDTO() {
    }

    public SolicitudDetalleDTO(String solicitante, String motivo, Long solicitudes, Long cantidad, Long aprobado, Long rechazado, Long pendiente) {
        this.solicitante = solicitante;
        this.motivo = motivo;
        this.solicitudes = solicitudes;
        this.cantidad = cantidad;
        this.aprobado = aprobado;
        this.rechazado = rechazado;
        this.pendiente = pendiente;
    }

    public String getSolicitante() {
        return solicitante;
    }

    public void setSolicitante(String solicitante) {
        this.solicitante = solicitante;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public Long getSolicitudes() {
        return solicitudes;
    }

    public void setSolicitudes(Long solicitudes) {
        this.solicitudes = solicitudes;
    }

    public Long getCantidad() {
        return cantidad;
    }

    public void setCantidad(Long cantidad) {
        this.cantidad = cantidad;
    }

    public Long getAprobado() {
        return aprobado;
    }

    public void setAprobado(Long aprobado) {
        this.aprobado = aprobado;
    }

    public Long getRechazado() {
        return rechazado;
    }

    public void setRechazado(Long rechazado) {
        this.rechazado = rechazado;
    }

    public Long getPendiente() {
        return pendiente;
    }

    public void setPendiente(Long pendiente) {
        this.pendiente = pendiente;
    }
}
