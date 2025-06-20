import { Component, OnInit, ElementRef } from '@angular/core';
import { AuthService } from 'src/app/services/auth.service';
import { ClienteEmpresa, FacturaService} from 'src/app/services/factura.service';
import Swal from 'sweetalert2';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-usuario',
  templateUrl: './usuario.component.html',
  styleUrls: ['./usuario.component.css']
})
export class UsuarioComponent implements OnInit {

  clientesAll$!: Observable<ClienteEmpresa[]>;
  empresaAll$!: Observable<ClienteEmpresa[]>;
  usuarios: any[] = [];
  id!: any;
  nombres!: string;
  username!: string;
  email!: string;
  notificacion: boolean = false;
  role!: any;
  empresa!: any;
 
 
  password!: string;
  editing!: boolean;

  constructor(private elementRef: ElementRef, private authService: AuthService, private facturaServices:FacturaService) { }

  ngOnInit(): void {
    var s = document.createElement("script");
    s.type = "text/javascript";
    s.src = "../assets/js/main.js";
    this.elementRef.nativeElement.appendChild(s);
    this.empresaAll$ = this.facturaServices.getEmpresa();
    

    this.facturaServices.getEmpresa().subscribe(
      response => {
        console.log(response);
      },
      error => {
        console.log(error);
      }
    );


    this.getUsuarios();
  }

  getUsuarios() {
    this.authService.getAllUsers().subscribe(
      response => {
        this.usuarios = response;
      },
      error => {
        console.log(error);
      }
    );
  }

  openModal(row: any) {
    if (row) {
      this.editing = true;
      this.id = row.id;
      this.nombres = row.nombres;
      this.email = row.email;
      this.username = row.username;
      this.password = row.password;
      this.empresa = row.empresa;
    } else {
      this.editing = false;
      this.id = null;
      this.nombres = '';
      this.email = '';
      this.username = '';
      this.password = '';
      this.empresa = '';
    }
  }


  save() {

      if (this.editing) {
      const updateUser: any = {
        id: this.id,
        username: this.username,
        email: this.email,
        nombres: this.nombres,
        notificacion: this.notificacion,
        password: this.password,
        role: [this.role],
        empresa: this.empresa
      };

      this.authService.updateUser(updateUser.id, updateUser)
        .subscribe(
          updatedMail => {
            this.getUsuarios();
            this.closeModal();
            Swal.fire({
              title: 'Información actualizada.',
              text: 'Los datos se han actualizado con éxito.',
              icon: 'success',
              confirmButtonText: 'OK'
            });
          },
          error => {
            console.log(error);
            Swal.fire({
              title: 'Error',
              text: 'Ha ocurrido un error al actualizar los datos.',
              icon: 'error',
              confirmButtonText: 'OK'
            });
          }
        );
    } else {
      const createUser: any = {
        id: 0,
        username: this.username,
        email: this.email,
        nombres: this.nombres,
        notificacion: this.notificacion,
        password: this.password,
        role: [this.role],
        empresa: this.empresa
      };

      if(this.password.length>=6) {

        this.authService.signUp(createUser)
        .subscribe(
          createdMail => {
            this.getUsuarios();
            this.closeModal();
            Swal.fire({
              title: 'Información guardada.',
              text: 'Los datos se han guardado con éxito.',
              icon: 'success',
              confirmButtonText: 'OK'
            });
          },
          error => {
            console.log(error);
            Swal.fire({
              title: 'Error',
              text: 'Ha ocurrido un error al guardar los datos.',
              icon: 'error',
              confirmButtonText: 'OK'
            });
          }
        );
      } else {
        Swal.fire({
          title: 'Error',
          text: 'Contraseña debe ser mayor o igual a 6 caracteres.',
          icon: 'error',
          confirmButtonText: 'OK'
        });
      }
    }
  }


  closeModal() {
    this.editing = false;
  }

  deleteUsuario(element?: any) {
    /*console.log(element);
    Swal.fire({
      title: 'Eliminar!',
      text: '¿Está seguro de que desea eliminar este registro?',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Eliminar',
      cancelButtonText: 'Cancelar'
    }).then((result) => {
      if (result.isConfirmed) {
        this.mailService.deleteMail(element.id).subscribe(response => {
          this.getMails();
          console.log(response);
        });
        Swal.fire('Eliminado!', 'Registro eliminado.', 'success');
      }
    });*/
  }
}
