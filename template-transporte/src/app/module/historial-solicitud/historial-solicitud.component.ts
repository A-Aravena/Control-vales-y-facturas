import { Component, OnInit, ElementRef } from '@angular/core';
import { Observable } from 'rxjs';
import { AuthService } from 'src/app/services/auth.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-historial-solicitud',
  templateUrl: './historial-solicitud.component.html',
  styleUrls: ['./historial-solicitud.component.css']
})
export class HistorialSolicitudComponent implements OnInit {
  solicitudes: any[] = [];
  empleados: any[] = [];
  roles: string[] = JSON.parse(localStorage.getItem('roles') || '[]');
  
  inicio!: any;
  fin!: Date;
  idmodal!: any;
  constructor() { }

  ngOnInit(): void {
  }

  getHistorial() {

  }
  hasRole(role: string): boolean {
    return this.roles.includes(role);
  }


}
