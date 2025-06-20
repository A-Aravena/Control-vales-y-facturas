import { HttpClient } from '@angular/common/http';
import { Observable, Subject } from 'rxjs';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import baserUrl from './helper';



@Injectable({
  providedIn: 'root'
})
export class AuthService {
  public loginStatusSubjec = new Subject<boolean>();

  constructor(private http: HttpClient,private router: Router) { }

  signIn(username: string, password: string): Observable<any> {
    const signInData = { username, password };
    return this.http.post<any>(`${baserUrl}/auth/signin`, signInData);
  }

  signUp(data: any): Observable<any> {
    return this.http.post<any>(`${baserUrl}/auth/signup`, data);
  }

  updateUser(id: number, data: any): Observable<any> {
    
    return this.http.put<any>(`${baserUrl}/auth/update/${id}`, data);
  }

  selectUser(): Observable<any> {
    return this.http.get<any>(`${baserUrl}/auth/select`);
  }

  getAllUsers(): Observable<any> {
    return this.http.get<any>(`${baserUrl}/auth/usuariosFull`);
  }

  getMailPriorityUser(correo: string): Observable<any> {
    return this.http.get<any>(`${baserUrl}/auth/prioridad/${correo}`);
  }

  uploadImage(photo: File, username: string): Observable<any> {
    const formData = new FormData();
    formData.append('photo', photo);
    formData.append('username', username);
    return this.http.post<any>(`${baserUrl}/auth/upload`, formData);
  }

  loadImage(username: string): Observable<any> {
    return this.http.get<any>(`${baserUrl}/auth/imagen/${username}`);
  }

  removeImage(username: string): Observable<void> {
    return this.http.delete<void>(`${baserUrl}/auth/eliminarImagen/${username}`);
  }

  isAuthenticated(): boolean {
    const accessToken = localStorage.getItem('accessToken');
    return !!accessToken;
  }

  logout() {
    localStorage.removeItem('prioridad');
    localStorage.removeItem('accessToken');
    localStorage.removeItem('username');
    localStorage.removeItem('nombres');
    localStorage.removeItem('email');
    localStorage.removeItem('roles');
    localStorage.removeItem('prioridadProceso');
    this.router.navigate(['/']);
  }

}
