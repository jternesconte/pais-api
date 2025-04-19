import { Injectable } from '@angular/core';
import { UsuarioLoginDTO } from '../interfaces/usuarioLoginDTO';
import { map, Observable } from 'rxjs';
import { UsuarioAutenticadoDTO } from '../interfaces/usuarioAutenticadoDTO';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {

  constructor(private http: HttpClient) { }

  autenticar(loginData: UsuarioLoginDTO): Observable<UsuarioAutenticadoDTO> {
    return this.http.post<UsuarioAutenticadoDTO>(`http://localhost:8080/usuario/autenticar`, loginData);
  }

  renovarToken(): Observable<string> {
    const tokenAtual = localStorage.getItem('token');
    return this.http.get(`http://localhost:8080/usuario/renovar-ticket?token=${tokenAtual}`, { responseType: 'text' });
  }

  logout() {
    localStorage.removeItem('token');
    localStorage.removeItem('admin');
    localStorage.removeItem('usuario');
  }
}
