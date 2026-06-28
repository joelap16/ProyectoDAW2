import { Component, ChangeDetectorRef } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { AuthService } from '../../../services/auth/auth.service';
import { Router, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [ReactiveFormsModule, RouterModule, CommonModule],
  templateUrl: './login.html',
  styleUrl: './login.scss',
})
export class Login {

  loginForm: FormGroup;

  errorMsg = '';
  cargando = false;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router,
    private cd: ChangeDetectorRef
  ) {

    this.loginForm = this.fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });

  }

  iniciarSesion(): void {

    if (this.loginForm.invalid) {
      return;
    }

    this.cargando = true;
    this.errorMsg = '';

    this.authService.login(this.loginForm.value).subscribe({

      next: (token) => {

        this.authService.guardarToken(token);

        const rol = this.authService.getRol();

        if (rol === 'ADMINISTRADOR') {

          this.router.navigate(['/admin/dashboard']);

        } else if (rol === 'TECNICO') {

          this.router.navigate(['/tecnico/dashboard']);

        } else if (rol === 'USUARIO') {

          this.router.navigate(['/usuario/dashboard']);

        }

        this.cargando = false;

        this.cd.detectChanges();

      },

      error: () => {

        this.errorMsg = 'Credenciales incorrectas. Intenta nuevamente.';
        this.cargando = false;

        this.cd.detectChanges();

      }

    });

  }

}