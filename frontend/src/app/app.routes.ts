import { Routes } from '@angular/router';
import { LoginComponent } from './pages/login/login.component';
import { PaisesComponent } from './pages/paises/paises.component';

export const routes: Routes = [
   {
      path: '',
      component: LoginComponent
   },
   {
      path: 'login',
      component: LoginComponent
   },
   {
      path: 'paises',
      component: PaisesComponent
   }
];
