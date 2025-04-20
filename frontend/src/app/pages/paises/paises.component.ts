import { PaisDTO } from './../../shared/interfaces/paisDTO';
import { Component, OnInit } from '@angular/core';
import { MenubarModule } from 'primeng/menubar';
import { MenuModule } from 'primeng/menu';
import { ButtonModule } from 'primeng/button';
import { TableModule } from 'primeng/table';
import { DialogModule } from 'primeng/dialog';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { FloatLabelModule } from 'primeng/floatlabel';
import { PasswordModule } from 'primeng/password';
import { CommonModule } from '@angular/common';
import { ConfirmationService, MenuItem, MessageService } from 'primeng/api';
import { PaisService } from '../../shared/services/pais.service';
import { UsuarioService } from '../../shared/services/usuario.service';
import { Pais } from '../../models/Pais';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { ToastModule } from 'primeng/toast';
import { Router, RouterModule } from '@angular/router';
import { InputTextModule } from 'primeng/inputtext';
import { InputGroupModule } from 'primeng/inputgroup';
import { InputGroupAddonModule } from 'primeng/inputgroupaddon';


@Component({
  selector: 'app-paises',
  templateUrl: './paises.component.html',
  styleUrls: ['./paises.component.css'],
  imports: [
    CommonModule,
    MenubarModule,
    MenuModule,
    ButtonModule,
    TableModule,
    DialogModule,
    FloatLabelModule,
    PasswordModule,
    ReactiveFormsModule,
    FormsModule,
    ConfirmDialogModule,
    ToastModule,
    InputGroupModule,
    InputGroupAddonModule,
    InputTextModule,
    RouterModule
  ],
  providers: [ConfirmationService, MessageService]
})
export class PaisesComponent implements OnInit {

  usuario: string;
  isAdmin = false;
  paises: PaisDTO[] = [];
  formPais: FormGroup;
  visibleDialog: boolean = false;
  isEditar: boolean = false;
  perfilItems: MenuItem[] = [];
  filtroNome :string = "";

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private messageService: MessageService,
    private paisService: PaisService,
    private usuarioService: UsuarioService,
    private confirmationService: ConfirmationService
  ) {
    this.formPais = this.fb.group({
      id: [0],
      nome:['', [Validators.required]],
      sigla: ['', [Validators.required, Validators.maxLength(2), Validators.minLength(2)]],
      gentilico: ['', [Validators.required]]
    });

    this.isAdmin = localStorage.getItem('administrador') === 'true';
    this.usuario = localStorage.getItem('usuario') || '';
  }

  ngOnInit() {
    this.perfilItems = [
      {
          items: [
            {
              label: 'Logout',
              icon: 'pi pi-sign-out',
              command: () => {
                this.logout();
              }
            }
          ]
      }
    ];
    this.carregarPaises();
  }

  carregarPaises() {
    this.paisService.listarPaises().subscribe({
      next: (data) => {
        this.paises = data;
      },
      error: () => {
        this.messageService.add({ severity: 'error', summary: 'Erro', detail: 'Falha ao carregar países' });
      }
    });
  }

  openForm(pais: Pais | null) {
    if(!pais) {
      this.formPais.reset();
    } else {
      this.isEditar = true;
      this.formPais.get('id')?.setValue(pais.id);
      this.formPais.get('nome')?.setValue(pais.nome);
      this.formPais.get('sigla')?.setValue(pais.sigla);
      this.formPais.get('gentilico')?.setValue(pais.gentilico);
    }
    this.visibleDialog = true;
  }

  deletePais(paisId: number) {
    this.confirmationService.confirm({
      message: "Você tem certeza que deletar o país?",
      header: 'Confirmar',
      closable: true,
      closeOnEscape: true,
      rejectButtonProps: {
          label: 'Cancelar',
          severity: 'secondary',
          outlined: true,
      },
      acceptButtonProps: {
          label: 'Deletar',
      },
      accept: () => {
        this.paisService.excluirPais(paisId).subscribe({
          next: () => {
            this.messageService.add({ severity: 'success', summary: 'Sucesso', detail: 'País Removido' });
            this.carregarPaises();
          },
          error: () => {
            this.messageService.add({ severity: 'error', summary: 'Erro', detail: 'Erro ao deletar país' });
          }
        });
      },
      reject: () => {
          
      },
    });
    
  }

  salvarPais() {
    if (this.formPais.invalid) return;

    const pais: PaisDTO = this.formPais.value;

    let paisRequest: Pais

    if(this.isEditar) {
      paisRequest = {
        id: this.formPais.get('id')?.value,
        nome: pais.nome,
        sigla: pais.sigla,
        gentilico: pais.gentilico
      }
    } else {
      paisRequest = {
        id: 0,
        nome: pais.nome,
        sigla: pais.sigla,
        gentilico: pais.gentilico
      }
    }
    this.paisService.criarPais(paisRequest).subscribe({
      next: (response) => {
        if(response === null) {
          this.messageService.add({ severity: 'error', summary: 'Erro', detail: 'País já existe' });
        } else {
          this.messageService.add({ severity: 'success', summary: 'Sucesso', detail: 'País salvo' });
        }
        this.carregarPaises();
        this.visibleDialog = false;
      },
      error: () => {
        this.messageService.add({ severity: 'error', summary: 'Erro', detail: 'Erro ao salvar país' });
      }
    });
  }

  logout() {
    this.confirmationService.confirm({
      message: "Você tem certeza que deseja sair?",
      header: 'Confirmar',
      closable: true,
      closeOnEscape: true,
      rejectButtonProps: {
          label: 'Cancelar',
          severity: 'secondary',
          outlined: true,
      },
      acceptButtonProps: {
          label: 'Logout',
      },
      accept: () => {
          this.messageService.add({ severity: 'info', summary: 'Confirmed', detail: 'Successfully logout' });
          this.usuarioService.logout();
          this.router.navigate(['/login']);
      },
      reject: () => {
          
      },
    });
  }

  buscarPorNome() {
    if(this.filtroNome.length > 0) {
      this.paisService.pesquisarPaises(this.filtroNome).subscribe({
        next: (data) => {
          this.paises = data;
        },
        error: () => {
          this.messageService.add({ severity: 'error', summary: 'Erro', detail: 'Falha ao carregar países' });
        }
      });
    } else {
      this.carregarPaises();
    } 
  }
}
