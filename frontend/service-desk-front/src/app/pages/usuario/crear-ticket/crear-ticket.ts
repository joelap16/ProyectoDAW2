import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';

import { TicketService } from '../../../services/ticket/ticket-service';
import { TicketCreate } from '../../../model/ticket/ticket-create';

@Component({
  selector: 'app-crear-ticket',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule],
  templateUrl: './crear-ticket.html',
  styleUrl: './crear-ticket.scss'
})
export class CrearTicketComponent {
  isLoading = false;
  successMessage = '';
  errorMessage = '';

  form: any;

  constructor(
    private fb: FormBuilder,
    private ticketService: TicketService,
    private router: Router
  ) {
    this.form = this.fb.group({
      titulo: ['', [Validators.required, Validators.maxLength(100)]],
      descripcion: ['', [Validators.required, Validators.maxLength(500)]],
      categoriaId: [null, [Validators.required, Validators.min(1)]]
    });
  }

  get titulo() {
    return this.form.get('titulo');
  }

  get descripcion() {
    return this.form.get('descripcion');
  }

  get categoriaId() {
    return this.form.get('categoriaId');
  }

  enviar(): void {
    this.successMessage = '';
    this.errorMessage = '';

    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    const payload: TicketCreate = {
      titulo: String(this.titulo?.value ?? '').trim(),
      descripcion: String(this.descripcion?.value ?? '').trim(),
      categoriaId: Number(this.categoriaId?.value)
    };

    this.isLoading = true;

    this.ticketService.crearTicketUsuario(payload).subscribe({
      next: () => {
        this.isLoading = false;
        this.successMessage = 'Ticket creado correctamente.';
        this.form.reset();

        setTimeout(() => {
          this.router.navigate(['/usuario/mis-tickets']);
        }, 1200);
      },
      error: (err) => {
        this.isLoading = false;
        this.errorMessage =
          err?.error?.message ||
          'No se pudo crear el ticket. Verifica los datos e intenta nuevamente.';
      }
    });
  }
}