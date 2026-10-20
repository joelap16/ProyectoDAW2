import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';

import { AuthService } from '../../../services/auth/auth.service';
import { RegisterRequest } from '../../../model/register-request';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule],
  templateUrl: './register.html',
  styleUrl: './register.scss',
})
export class Register implements OnInit {
  form!: FormGroup;

  mensaje = '';
  error = '';
  enviando = false;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router,
    private cd: ChangeDetectorRef,
  ) {}

  ngOnInit(): void {
    this.form = this.fb.group({
      nombre: ['', Validators.required],

      apellido: ['', Validators.required],

      email: ['', [Validators.required, Validators.email]],

      password: ['', [Validators.required, Validators.minLength(6)]],
    });
  }

  registrar(): void {
    if (this.enviando) {
      return;
    }

    this.mensaje = '';
    this.error = '';

    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    this.enviando = true;

    const request: RegisterRequest = {
      name: this.form.value.nombre,
      apellido: this.form.value.apellido,
      email: this.form.value.email,
      password: this.form.value.password,
      rol: 'USUARIO',
    };

    this.authService.register(request).subscribe({
      next: (respuesta: string) => {
        if (respuesta === 'Usuario registrado exitosamente.') {
          this.mensaje = respuesta;
          this.form.reset();
          this.cd.detectChanges();

          setTimeout(() => {
            this.router.navigate(['/auth/login']);
          }, 3000);
        } else {
          this.error = respuesta;
          this.enviando = false;
          this.cd.detectChanges();
        }
      },

      error: (err: any) => {
        console.error(err);

        this.error = err.error || 'No se pudo registrar el usuario.';

        this.enviando = false;
        this.cd.detectChanges();
      },
    });
  }
}