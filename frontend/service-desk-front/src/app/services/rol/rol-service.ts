import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { environment } from '../../environments/environment';
import { Rol } from '../../model/rol/rol';

@Injectable({
  providedIn: 'root'
})
export class RolService {

  private apiUrl = environment.apiUrl + '/api/admin/roles';

  constructor(private http: HttpClient) {}

  obtenerRoles(): Observable<Rol[]> {
    return this.http.get<Rol[]>(this.apiUrl);
  }
}