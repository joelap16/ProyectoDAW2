import { Component, OnInit } from '@angular/core';
import { RouterOutlet, RouterLink, RouterLinkActive, Router } from '@angular/router';
import { AuthService } from '../../services/auth/auth.service';

@Component({
  selector: 'app-admin-layout',
  standalone: true,
  imports: [RouterOutlet, RouterLink, RouterLinkActive],
  templateUrl: './admin-layout.html',
  styleUrl: './admin-layout.scss'
})
export class AdminLayout implements OnInit {
  usuarioActual: string = '';
  inicial: string = '';

  constructor(private authService: AuthService, private router: Router) {}

  ngOnInit(): void {
    const username = this.authService.getUsername();
    this.usuarioActual = username || 'Administrador';
    this.inicial = this.usuarioActual.charAt(0).toUpperCase();
  }

  cerrarSesion() { this.authService.cerrarSesion(); this.router.navigate(['/auth/login']); }
}
