import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { Router } from '@angular/router';
import { catchError, switchMap, take } from 'rxjs/operators';
import { throwError } from 'rxjs';
import { UsuarioService } from '../services/usuario.service';

export const JwtInterceptor: HttpInterceptorFn = (req, next) => {
  const authService = inject(UsuarioService);
  const token = localStorage.getItem('token');
  const router = inject(Router);

  if (req.url.includes('/usuario/renovar-ticket')) {
    return next(req);
  }

  const modifiedReq = token
    ? req.clone({ setHeaders: { Authorization: `Bearer ${token}` } })
    : req;

  return next(modifiedReq).pipe(
    catchError((error) => {
      if (error.status === 401) {
        return authService.renovarToken().pipe(
          switchMap((novoToken: string) => {
            if (novoToken) {
              localStorage.setItem('token', novoToken);
              const newReq = req.clone({ setHeaders: { Authorization: `Bearer ${novoToken}` } });
              return next(newReq);
            } else {
              authService.logout();
              router.navigate(['/login']);
              return throwError(() => error);
            }
          }),
          catchError(() => {
            authService.logout();
            router.navigate(['/login']);
            return throwError(() => error);
          })
        );
      }

      return throwError(() => error);
    })
  );
};
