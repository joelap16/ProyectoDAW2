import { Component } from '@angular/core';
import { Router, RouterLink, RouterOutlet } from '@angular/router';
import { AuthService } from '../../services/auth/auth.service';

@Component({
  selector: 'app-tecnico-layout',
  standalone: true,
  imports: [RouterOutlet, RouterLink],
  templateUrl: './tecnico-layout.html',
  styleUrl: './tecnico-layout.scss'
})
export class TecnicoLayout {

  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  cerrarSesion() {

    this.authService.cerrarSesion();

    this.router.navigate(['/auth/login']);

  }

}