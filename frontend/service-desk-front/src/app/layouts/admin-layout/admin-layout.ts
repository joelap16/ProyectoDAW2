import { Component } from '@angular/core';
import { RouterOutlet, RouterLink, Router } from '@angular/router';
import { AuthService } from '../../services/auth/auth.service';


@Component({
  selector: 'app-admin-layout',
  standalone: true,
  imports: [RouterOutlet, RouterLink],
  templateUrl: './admin-layout.html',
  styleUrl: './admin-layout.scss'
})

export class AdminLayout {

  constructor(

    private authService: AuthService,
    private router: Router

  ) {}

  cerrarSesion() {

    this.authService.cerrarSesion()

    this.router.navigate(['/auth/login']);

  }

}
