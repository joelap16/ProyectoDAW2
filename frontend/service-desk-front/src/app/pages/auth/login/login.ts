import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { AuthService } from '../../../services/auth/auth.service';
import { Router, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [ReactiveFormsModule,
    RouterModule,
    CommonModule
  ],  
  templateUrl: './login.html',
  styleUrl: './login.scss',
})
export class Login {

  loginForm: FormGroup;

  constructor(private fb: FormBuilder, private authService: AuthService, private router: Router){
    this.loginForm = this.fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });

  }

  iniciarSesion() {

  if (this.loginForm.invalid) {
    return;
  }

  this.authService.login(this.loginForm.value)      

    .subscribe({

      next: (token) => {
        
        this.authService.guardarToken(token);

        // Guardar token y mostrarlo
        console.log('Token recibido: ', token)

        // Probar métodos del AuthService
        console.log('Payload:', this.authService.getDecodedToken());

        console.log('Rol:',
          this.authService.getRol());

        console.log('Usuario:',
          this.authService.getUsername());

        console.log('Token expirado:',
          this.authService.isTokenExpired());

        console.log('Autenticado:',
          this.authService.isLoggedIn());

          // Redireccion a los dashboards por rol
        
        const rol = this.authService.getRol();

        if (rol === 'ADMINISTRADOR') {

        this.router.navigate(['/admin/dashboard']);

        }

        else if (rol === 'TECNICO') {

        this.router.navigate(['/tecnico/dashboard']);

        }     

        else if (rol === 'USUARIO') {

        this.router.navigate(['/usuario/dashboard']);

        }
        
      },

      error: (err) => {

        console.error('Error de autenticación', err);

      }

    });
    
  } 

}
