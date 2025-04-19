import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { UsuarioLoginDTO } from '../interfaces/usuarioLoginDTO';
import { UsuarioAutenticadoDTO } from '../interfaces/usuarioAutenticadoDTO';
import { PaisDTO } from '../interfaces/paisDTO';
import { Pais } from '../../models/Pais';

@Injectable({
  providedIn: 'root'
})
export class PaisService {

  constructor(private http: HttpClient) { }

  listarPaises(): Observable<Pais[]> {
    return this.http.get<Pais[]>(`http://localhost:8080/pais/listar`);
  }
  
  criarPais(paisData: Pais): Observable<Pais> {
    return this.http.post<Pais>(`http://localhost:8080/pais/salvar`, paisData);
  }

  pesquisarPaises(filtro: string): Observable<PaisDTO[]> {
    return this.http.get<PaisDTO[]>(`http://localhost:8080/pais/pesquisar?filtro=${filtro}`);
  }

  excluirPais(paisId: number): Observable<boolean> {
    return this.http.get<boolean>(`http://localhost:8080/pais/excluir?id=${paisId}`);
  }
}
