import { Component, OnInit } from '@angular/core';
import { RouterOutlet, RouterLink, RouterLinkActive, Router } from '@angular/router';
import { AuthService } from '../../services/auth/auth.service';

@Component({
  selector: 'app-tecnico-layout',
  standalone: true,
  imports: [RouterOutlet, RouterLink, RouterLinkActive],
  templateUrl: './tecnico-layout.html',
  styleUrl: './tecnico-layout.scss'
})
export class TecnicoLayout implements OnInit {
  usuarioActual: string = '';
  inicial: string = '';

  constructor(private authService: AuthService, private router: Router) {}

  ngOnInit(): void {
    const username = this.authService.getUsername();
    this.usuarioActual = username || 'Técnico';
    this.inicial = this.usuarioActual.charAt(0).toUpperCase();
  }

  cerrarSesion() { this.authService.cerrarSesion(); this.router.navigate(['/auth/login']); }
}
