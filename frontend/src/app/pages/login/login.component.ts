import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { CardModule } from 'primeng/card';
import { CarouselModule } from 'primeng/carousel';
import { InputGroupModule } from 'primeng/inputgroup';
import { InputGroupAddonModule } from 'primeng/inputgroupaddon';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { InputTextModule } from 'primeng/inputtext';
import { ButtonModule } from 'primeng/button';
import { TabsModule } from 'primeng/tabs';
import { FloatLabelModule } from 'primeng/floatlabel';
import { PasswordModule } from 'primeng/password';
import { Router } from '@angular/router';
import { PanelModule } from 'primeng/panel';
import { ToastModule } from 'primeng/toast';
import { MessageService } from 'primeng/api';
import { UsuarioService } from '../../shared/services/usuario.service';
import { UsuarioLoginDTO } from '../../shared/interfaces/usuarioLoginDTO';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  imports: [
    CardModule,
    CarouselModule,
    CommonModule,
    InputGroupModule,
    InputGroupAddonModule,
    InputTextModule,
    ButtonModule,
    TabsModule,
    FloatLabelModule,
    PasswordModule,
    ReactiveFormsModule,
    FormsModule,
    PanelModule,
    ToastModule],
    providers: [MessageService]
})
export class LoginComponent implements OnInit {

  formGroup: FormGroup;

  loginData: UsuarioLoginDTO | undefined;

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private usuarioService: UsuarioService,
    private messageService: MessageService
  ) {
    this.formGroup = this.fb.group({
      login:[''],
      senha: ['']
    });
  }

  ngOnInit() {
    if(localStorage.getItem('token')) {
      console.log(localStorage.getItem('token'));
      this.router.navigate(['/paises'])
    }
  }

  onLoginSubmit() {
    this.loginData = undefined;
    this.loginData = {
      login: this.formGroup.get('login')?.value,
      senha: this.formGroup.get('senha')?.value
    }
    
    this.usuarioService.autenticar(this.loginData).subscribe({
      next: (value) => {
        if(value["autenticado"] === false) {
          this.messageService.add({ severity: 'error', summary: 'Erro', detail: 'Usuário ou senha incorretos' });
        } else {
          this.messageService.add({ severity: 'info', summary: 'Sucesso', detail: 'Logado com sucesso' });
          localStorage.setItem('token', value['token']);
          localStorage.setItem('administrador', value['administrador'].toString());
          localStorage.setItem('usuario', value['nome'].toString());
          this.router.navigate(['/inicial']);
        }
      },
      error: (err) => {
        this.messageService.add({ severity: 'error', summary: 'Erro', detail: err.error.error });
      },
    });
  }

}
