import { Component, OnInit } from '@angular/core';
import { RouterOutlet, RouterLink, RouterLinkActive, Router } from '@angular/router';
import { AuthService } from '../../services/auth/auth.service';

@Component({
  selector: 'app-usuario-layout',
  standalone: true,
  imports: [RouterOutlet, RouterLink, RouterLinkActive],
  templateUrl: './usuario-layout.html',
  styleUrl: './usuario-layout.scss'
})
export class UsuarioLayout implements OnInit {
  usuarioActual: string = '';
  inicial: string = '';

  constructor(private authService: AuthService, private router: Router) {}

  ngOnInit(): void {
    const username = this.authService.getUsername();
    this.usuarioActual = username || 'Usuario';
    this.inicial = this.usuarioActual.charAt(0).toUpperCase();
  }

  cerrarSesion() { this.authService.cerrarSesion(); this.router.navigate(['/auth/login']); }
}
