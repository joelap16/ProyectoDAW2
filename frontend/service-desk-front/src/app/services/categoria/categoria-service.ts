import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class CategoriaService {

  private apiUrl = environment.apiUrl + '/api/categorias';

  constructor(private http: HttpClient) {}

  obtenerCategorias(): Observable<string[]> {
    return this.http.get<string[]>(this.apiUrl);
  }

}