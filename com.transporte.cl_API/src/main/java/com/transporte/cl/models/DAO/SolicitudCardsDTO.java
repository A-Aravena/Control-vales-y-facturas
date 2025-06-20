package com.transporte.cl.models.DAO;

public class SolicitudCardsDTO {
    private Long solicitudes;
    private Long aprobado;
    private Long rechazado;
    private Long pendiente;

    public SolicitudCardsDTO() {
    }

    public SolicitudCardsDTO(Long solicitudes, Long aprobado, Long rechazado, Long pendiente) {
        this.solicitudes = solicitudes;
        this.aprobado = aprobado;
        this.rechazado = rechazado;
        this.pendiente = pendiente;
    }

    public Long getSolicitudes() {
        return solicitudes;
    }

    public void setSolicitudes(Long solicitudes) {
        this.solicitudes = solicitudes;
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
