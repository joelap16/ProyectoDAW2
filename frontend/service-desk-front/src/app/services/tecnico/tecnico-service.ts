import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { environment } from '../../environments/environment';
import { Tecnico } from '../../model/tecnico/tecnico';

@Injectable({
  providedIn: 'root'
})
export class TecnicoService {

  private apiUrl = environment.apiUrl + '/api/admin/tecnicos';

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

  

} 