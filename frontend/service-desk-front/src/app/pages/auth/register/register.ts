import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';

import { AuthService } from '../../../services/auth/auth.service';
import { RegisterRequest } from '../../../model/register-request';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    RouterModule
  ],
  templateUrl: './register.html',
  styleUrl: './register.scss'
})
export class Register implements OnInit {

  form!: FormGroup;

  mensaje = '';
  error = '';

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {

    this.form = this.fb.group({

      nombre: [
        '',
        Validators.required
      ],

      apellido: [
        '',
        Validators.required
      ],

      email: [
        '',
        [
          Validators.required,
          Validators.email
        ]
      ],

      password: [
        '',
        [
          Validators.required,
          Validators.minLength(6)
        ]
      ]

    });

  }

  registrar(): void {

    this.mensaje = '';
    this.error = '';

    if (this.form.invalid) {

      this.form.markAllAsTouched();
      return;

    }

    const request: RegisterRequest = {

      name: this.form.value.nombre,
      apellido: this.form.value.apellido,
      email: this.form.value.email,
      password: this.form.value.password,

      // Siempre se registra como usuario
      rol: 'USUARIO'

    };

    this.authService.register(request).subscribe({

      next: (respuesta: string) => {

        this.mensaje = respuesta;

        this.form.reset();

        setTimeout(() => {

          this.router.navigate(['/auth/login']);

        }, 1500);

      },

      error: (err: any) => {

        console.error(err);

        this.error =
          err.error ||
          'No se pudo registrar el usuario.';

      }

    });

  }

}