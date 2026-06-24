import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

import { Ticket } from '../../model/ticket/ticket';
import { TicketCreate } from '../../model/ticket/ticket-create';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class TicketService {

  private adminUrl = environment.apiUrl + '/api/admin/tickets';
  private usuarioUrl = environment.apiUrl + '/api/usuario/tickets';
  private tecnicoUrl = environment.apiUrl + '/api/tecnico/tickets';

  constructor(private http: HttpClient) {}

  // =========================
  // ADMIN
  // =========================

  obtenerTicketsAdmin(): Observable<Ticket[]> {
    return this.http.get<Ticket[]>(this.adminUrl);
  }

  crearTicketAdmin(ticket: TicketCreate): Observable<Ticket> {
    return this.http.post<Ticket>(this.adminUrl, ticket);
  }

  eliminarTicket(id: number): Observable<string> {
    return this.http.delete(`${this.adminUrl}/${id}`, { responseType: 'text' as const });
  }

  asignarTecnico(idTicket: number, idTecnico: number): Observable<Ticket> {
    return this.http.put<Ticket>(
      `${this.adminUrl}/${idTicket}/tecnico/${idTecnico}`,
      {}
    );
  }

  cambiarEstado(idTicket: number, nuevoEstado: string): Observable<Ticket> {
    return this.http.put<Ticket>(
      `${this.adminUrl}/${idTicket}/estado?nuevoEstado=${nuevoEstado}`,
      {}
    );
  }

  // =========================
  // USUARIO
  // =========================

  crearTicketUsuario(ticket: TicketCreate): Observable<Ticket> {
    return this.http.post<Ticket>(this.usuarioUrl, ticket);
  }

  obtenerMisTicketsUsuario(): Observable<Ticket[]> {
    return this.http.get<Ticket[]>(`${this.usuarioUrl}/mis`);
  }

  obtenerTicketUsuarioPorId(idTicket: number): Observable<Ticket> {
    return this.http.get<Ticket>(`${this.usuarioUrl}/${idTicket}`);
  }

  // =========================
  // TECNICO
  // =========================

  obtenerMisTicketsTecnico(estado?: string, categoria?: string): Observable<Ticket[]> {
    let params = new HttpParams();

    if (estado) {
      params = params.set('estado', estado);
    }

    if (categoria) {
      params = params.set('categoria', categoria);
    }

    return this.http.get<Ticket[]>(`${this.tecnicoUrl}/mis`, { params });
  }

  obtenerTicketTecnicoPorId(idTicket: number): Observable<Ticket> {
    return this.http.get<Ticket>(`${this.tecnicoUrl}/${idTicket}`);
  }

  atenderTicketTecnico(
    idTicket: number,
    dto: { estado: string; comentario: string }
  ): Observable<Ticket> {
    return this.http.put<Ticket>(`${this.tecnicoUrl}/${idTicket}/estado`, dto);
  }
}