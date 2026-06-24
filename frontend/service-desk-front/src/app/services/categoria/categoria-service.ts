import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { Observable } from 'rxjs';

import { environment } from '../../environments/environment';
import { Categoria } from '../../model/categoria/categoria';

@Injectable({
  providedIn: 'root'
})
export class CategoriaService {

  private apiUrl = environment.apiUrl + '/api/categorias/detalle';

  constructor(private http: HttpClient) { }

  obtenerCategorias(): Observable<Categoria[]> {

    return this.http.get<Categoria[]>(this.apiUrl);

  }

}