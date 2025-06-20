import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-users-profile',
  templateUrl: './users-profile.component.html',
  styleUrls: ['./users-profile.component.css']
})
export class UsersProfileComponent implements OnInit {
  username!: string;
  nombres!: string;

  constructor(private http: HttpClient, private authService: AuthService) { }

  ngOnInit(){
    this.username = localStorage.getItem('username')!;
    this.nombres = localStorage.getItem('nombres')!;
  }

}
