import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { UsuarioService } from '../../../services/usuario/usuario-service';
import { Usuario } from '../../../model/usuario/usuario';

@Component({
  selector: 'app-usuarios-admin',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './usuarios-admin.html',
  styleUrl: './usuarios-admin.scss'
})
export class UsuariosAdmin implements OnInit {

  usuarios: Usuario[] = [];

  editandoId: number | null = null;

  usuarioEditando: Usuario | null = null;

  constructor(
    private usuarioService: UsuarioService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.cargarUsuarios();
  }

  cargarUsuarios(): void {
    this.usuarioService.obtenerUsuarios().subscribe({
      next: (data) => {
        this.usuarios = data;
        this.cdr.detectChanges();
      },
      error: (err) => {
        console.error(err);
      }
    });
  }

  editarUsuario(usuario: Usuario): void {
    this.editandoId = usuario.id;

    this.usuarioEditando = {
      ...usuario,
      rolId: usuario.rolId
    };
  }

  cancelarEdicion(): void {
    this.editandoId = null;
    this.usuarioEditando = null;
  }

  guardarCambios(): void {
    if (!this.usuarioEditando) return;

    if (this.usuarioEditando.rolId == null) {
      console.error('rolId no llegó en el usuario editado');
      return;
    }

    console.log("ANTES DE ENVIAR:", this.usuarioEditando);

    const payload = {
      nombre: this.usuarioEditando.nombre,
      apellido: this.usuarioEditando.apellido,
      email: this.usuarioEditando.email,
      rolId: this.usuarioEditando.rolId
    };

    this.usuarioService.editarUsuario(this.usuarioEditando.id, payload)
      .subscribe({
        next: (usuarioActualizado) => {
          const index = this.usuarios.findIndex(
            u => u.id === usuarioActualizado.id
          );

          if (index !== -1) {
            this.usuarios[index] = usuarioActualizado;
            this.usuarios = [...this.usuarios];
          }

          this.editandoId = null;
          this.usuarioEditando = null;
          this.cdr.detectChanges();
        },
        error: (err) => {
          console.error("ERROR EDITANDO:", err);
        }
      });
  }

  esEditando(usuario: Usuario): boolean {
    return this.editandoId === usuario.id;
  }
}