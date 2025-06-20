import { Component, OnInit, Inject } from '@angular/core';
import { DOCUMENT } from '@angular/common'
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  username!: string | null;
  nombres!: string | null;
  constructor(@Inject(DOCUMENT) private document: Document, private authService: AuthService) { }

  ngOnInit(){
    this.username = localStorage.getItem('username');
    this.nombres = localStorage.getItem('nombres');
  }
  sidebarToggle()
  {
    //toggle sidebar function
    this.document.body.classList.toggle('toggle-sidebar');
  }

  logout() {
    this.authService.logout();
  }
}
