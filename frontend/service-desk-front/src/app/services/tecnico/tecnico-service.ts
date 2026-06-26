import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { environment } from '../../environments/environment';
import { Tecnico } from '../../model/tecnico/tecnico';

import { Ticket } from '../../model/ticket/ticket';

@Injectable({
  providedIn: 'root'
})
export class TecnicoService {

  private apiUrl = environment.apiUrl + '/api/admin/tecnicos';

  private tecnicoApiUrl = environment.apiUrl + '/api/tecnico/tickets';

  constructor(private http: HttpClient) {}

  obtenerTecnicos(): Observable<Tecnico[]> {
    return this.http.get<Tecnico[]>(this.apiUrl);
  }

  asignarCategoria(
    tecnicoId: number,
    categoriaId: number
  ): Observable<Tecnico> {

    return this.http.put<Tecnico>(
      `${this.apiUrl}/${tecnicoId}/categoria?categoriaId=${categoriaId}`,
      {}
    );

  }

  obtenerMisTickets(
    estado?: string,
    categoria?: string
  ): Observable<Ticket[]> {

    let params: any = {};

    if (estado) {
      params.estado = estado;
    }

    if (categoria) {
      params.categoria = categoria;
    }

    return this.http.get<Ticket[]>(
      `${this.tecnicoApiUrl}/mis`,
      { params }
    );
  }

  obtenerTicketPorId(idTicket: number): Observable<Ticket> {
    return this.http.get<Ticket>(
     `${this.tecnicoApiUrl}/${idTicket}`
      );
  }

  atenderTicket(
    idTicket: number,
    estado: string,
    comentario: string
  ): Observable<Ticket> {

    return this.http.put<Ticket>(
      `${this.tecnicoApiUrl}/${idTicket}/estado`,
      {
        estado,
        comentario
      }
    );
  }

} 