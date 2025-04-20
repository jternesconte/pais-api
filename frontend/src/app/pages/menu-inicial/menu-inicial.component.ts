import { Component, OnInit } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { ButtonModule } from 'primeng/button';
import { CardModule } from 'primeng/card';

@Component({
  selector: 'app-menu-inicial',
  templateUrl: './menu-inicial.component.html',
  styleUrls: ['./menu-inicial.component.css'],
  imports: [ButtonModule, CardModule, RouterModule],
})
export class MenuInicialComponent implements OnInit {

  constructor(
    private router: Router
  ) { }

  ngOnInit() {
    if(!localStorage.getItem('token')) {
      this.router.navigate(['/login']);
    }
  }

  abrirSwagger() {
    window.open('http://localhost:8080/swagger-ui.html', '_blank');
  }

  abrirBanco() {
    window.open('http://localhost:8080/h2-console', '_blank');
  }

}
