import { HttpClient } from '@angular/common/http';
import { Injectable, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import baserUrl from './helper';

export interface CorreoPrioridad {
  id: number;
  correo: string;
  prioridad: number;
}

export interface SolicitudGerente {
  cantidad: number;
  solicitante: string;
}

@Injectable({
  providedIn: 'root'
})
export class DashboardService{
  solicitudes!: number;
  cantidad!: number;
  aprobado!: number;
  rechazado!: number;
  pendiente!: number;

  constructor(private http: HttpClient) { }

  getsolicitudDia(): Observable<any[]> {
    return this.http.get<any[]>(`${baserUrl}/solicitudingreso/solicitudFecha`);
  }

  getsolicitudGerentes(): Observable<any[]> {
    return this.http.get<any[]>(`${baserUrl}/solicitudingreso/solicitudGerente`);
  }

  calculatePercentage(value: number, total: number): number {
    return (value / total) * 100;
  }

  getsolicitudDetalle(): Observable<CorreoPrioridad> {
    return this.http.get<CorreoPrioridad>(`${baserUrl}/solicitudingreso/solicitudDetalle`);
  }

  getSolicitudCards() {
    return this.http.get<any>(`${baserUrl}/solicitudingreso/solicitudCards`);
  }
}
