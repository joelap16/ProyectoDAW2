import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { LoginRequest } from '../../model/login-request';
// JWT
import { jwtDecode } from 'jwt-decode';
import { JwtPayload } from '../../model/jwt-payload';

import { environment } from '../../environments/environment';

import { RegisterRequest } from '../../model/register-request';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private apiUrl = environment.apiUrl + '/auth';

  constructor(private http: HttpClient) {}

  login(data: LoginRequest): Observable<string> {

    return this.http.post(
      `${this.apiUrl}/token`,
      data,
      { responseType: 'text' }
    );

  }

  // GUARDAR TOKEN
  guardarToken(token: string): void {
    localStorage.setItem('token', token);
  }

  // OBTENER TOKEN
  obtenerToken(): string | null {
    return localStorage.getItem('token');
  }

  // CERRAR SESIÓN
  cerrarSesion(): void {
    localStorage.removeItem('token');
  }

  // DECODIFICAR TOKEN
  getDecodedToken(): JwtPayload | null {

    const token = this.obtenerToken();

    if (!token) {
      return null;
    }

    return jwtDecode<JwtPayload>(token);

  }

  // OBTENER ROL
  getRol(): string | null {

    const decoded = this.getDecodedToken();

    return decoded?.rol || null;

  }

  // OBTENER CORREO DEL USUARIO
  getUsername(): string | null {

    const decoded = this.getDecodedToken();

    return decoded?.sub || null;

  }

  // VERIFICAR SI EL TOKEN EXPIRÓ
  isTokenExpired(): boolean {

    const decoded = this.getDecodedToken();

    if (!decoded) {
      return true;
    }

    const expirationDate = decoded.exp * 1000;

    return Date.now() > expirationDate;

  }

  // VERIFICAR SI EL USUARIO ESTÁ AUTENTICADO
  isLoggedIn(): boolean {

    const token = this.obtenerToken();

    return token !== null && !this.isTokenExpired();

  }

  // REGISTER
  register(data: RegisterRequest): Observable<string> {

    return this.http.post(
      `${this.apiUrl}/register`,
      data,
    { responseType: 'text' }
  );

}

}