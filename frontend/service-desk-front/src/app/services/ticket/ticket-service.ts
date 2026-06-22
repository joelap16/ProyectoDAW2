import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { Ticket } from '../../model/ticket/ticket';
import { TicketCreate } from '../../model/ticket/ticket-create';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class TicketService {

  private apiUrl = environment.apiUrl + '/api/admin/tickets';

  constructor(private http: HttpClient) {}

  obtenerTickets(): Observable<Ticket[]> {
    return this.http.get<Ticket[]>(this.apiUrl);
  }

  crearTicket(ticket: TicketCreate): Observable<Ticket> {
    return this.http.post<Ticket>(this.apiUrl, ticket);
  }

  eliminarTicket(id: number): Observable<string> {
    return this.http.delete(`${this.apiUrl}/${id}`, { responseType: 'text' as const });
  }

  asignarTecnico(idTicket: number, idTecnico: number): Observable<Ticket> {
    return this.http.put<Ticket>(
      `${this.apiUrl}/${idTicket}/tecnico/${idTecnico}`,
      {}
    );
  }

  cambiarEstado(idTicket: number, nuevoEstado: string): Observable<Ticket> {
    return this.http.put<Ticket>(
      `${this.apiUrl}/${idTicket}/estado?nuevoEstado=${nuevoEstado}`,
      {}
    );
  }
}