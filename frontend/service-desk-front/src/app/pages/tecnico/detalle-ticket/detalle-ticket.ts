import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';

import { Ticket } from '../../../model/ticket/ticket';
import { TecnicoService } from '../../../services/tecnico/tecnico-service';

@Component({
  selector: 'app-detalle-ticket',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './detalle-ticket.html',
  styleUrl: './detalle-ticket.scss'
})
export class DetalleTicketComponent implements OnInit {
  ticket!: Ticket;
  cargando = true;
  error = '';
  comentario = '';
  estado = '';
  mensajeExito = '';
  mensajeError = '';

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private tecnicoService: TecnicoService,
    private cd: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.cargarTicket(id);
  }

  cargarTicket(id: number): void {
    this.cargando = true;
    this.error = '';

    this.tecnicoService.obtenerTicketPorId(id).subscribe({
      next: (data: Ticket) => {
        this.ticket = data;
        this.estado = data.estado === 'ABIERTO' ? 'EN_PROGRESO' : data.estado;
        this.cargando = false;
        this.cd.detectChanges();
      },
      error: () => {
        this.error = 'No se pudo cargar el ticket.';
        this.cargando = false;
        this.cd.detectChanges();
      }
    });
  }

  guardarCambios(): void {
    if (!this.estado) return;

    this.mensajeExito = '';
    this.mensajeError = '';

    this.tecnicoService.atenderTicket(this.ticket.id, this.estado, this.comentario).subscribe({
      next: () => {
        this.mensajeExito = '✅ Ticket actualizado correctamente.';
        this.cd.detectChanges();
        setTimeout(() => {
          this.router.navigate(['/tecnico/mis-tickets-tecnico']);
        }, 1500);
      },
      error: () => {
        this.mensajeError = 'No se pudo actualizar el ticket.';
        this.cd.detectChanges();
      }
    });
  }

  get ticketResuelto(): boolean {
    return this.ticket?.estado === 'RESUELTO';
  }
}
