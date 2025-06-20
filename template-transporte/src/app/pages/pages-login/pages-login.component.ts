import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/services/auth.service';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-pages-login',
  templateUrl: './pages-login.component.html',
  styleUrls: ['./pages-login.component.css']
})
export class PagesLoginComponent {
  username!: string;
  password!: string;

  constructor(private authService: AuthService, private router: Router) { }

  signIn() {
   
    this.authService.signIn(this.username, this.password).subscribe(
      response => {
        // Store data in local storage
        localStorage.setItem('accessToken', response.accessToken);
        localStorage.setItem('username', response.username);
        localStorage.setItem('nombres', response.nombres);
        localStorage.setItem('email', response.email);
        localStorage.setItem('id', response.id);
        localStorage.setItem('roles', JSON.stringify(response.roles));
        localStorage.setItem('empresa', response.empresa);
        
      
        this.router.navigate(['/dashboard']);
        // Handle navigation based on roles
        //if (response.roles.includes('ROLE_MODERATOR')) {
          // Navigate to moderator page
        //} else {
          // Navigate to user page
        //}
      },
      (error: any) => {
        Swal.fire({
          title: 'Error de inicio de sesión',
          text: 'Usuario y/o contraseña incorrecta.',
          icon: 'error',
          confirmButtonText: 'OK'
        });
      }
    );
  }
}
