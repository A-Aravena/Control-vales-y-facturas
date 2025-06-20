import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css']
})
export class SidebarComponent implements OnInit {
  username = localStorage.getItem('username');
  roles: string[] = JSON.parse(localStorage.getItem('roles') || '[]');
  constructor() { }

  ngOnInit(): void {
  }

  hasRole(role: string): boolean {
    return this.roles.includes(role);
  }

}
