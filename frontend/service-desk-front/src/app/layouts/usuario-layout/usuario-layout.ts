import { Component } from '@angular/core';
import { Router, RouterLink, RouterOutlet } from '@angular/router';
import { AuthService } from '../../services/auth/auth.service';

@Component({
  selector: 'app-usuario-layout',
  standalone: true,
  imports: [RouterOutlet, RouterLink],
  templateUrl: './usuario-layout.html',
  styleUrl: './usuario-layout.scss',
})
export class UsuarioLayout {

  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  cerrarSesion() {

    this.authService.cerrarSesion();

    this.router.navigate(['/auth/login']);

  }
  
}
