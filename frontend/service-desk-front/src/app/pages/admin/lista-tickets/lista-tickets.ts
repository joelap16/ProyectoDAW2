import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { TicketService } from '../../../services/ticket/ticket-service';
import { Ticket } from '../../../model/ticket/ticket';

import { Tecnico } from '../../../model/tecnico/tecnico';
import { TecnicoService } from '../../../services/tecnico/tecnico-service';

@Component({
  selector: 'app-lista-tickets',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './lista-tickets.html',
  styleUrl: './lista-tickets.scss'
})
export class ListaTickets implements OnInit {

  tickets: Ticket[] = [];
  tecnicos: Tecnico[] = [];

  estados = ['ABIERTO', 'EN_PROGRESO', 'CERRADO'];

  constructor(
    private ticketService: TicketService,
    private tecnicoService: TecnicoService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.cargarTickets();
    this.cargarTecnicos();
  }

  cargarTickets() {
    this.ticketService.obtenerTicketsAdmin().subscribe({
      next: (data) => {
        this.tickets = data;
        this.cdr.detectChanges();
      },
      error: (err) => console.error(err)
    });
  }

  cargarTecnicos() {
    this.tecnicoService.obtenerTecnicos().subscribe({
      next: (data) => {
        this.tecnicos = data;
        this.cdr.detectChanges();
      },
      error: (err) => console.error(err)
    });
  }


  estadosPermitidos(estadoActual: string): string[] {
    switch (estadoActual) {
      case 'ABIERTO':
        return ['ABIERTO', 'EN_PROGRESO'];
      case 'EN_PROGRESO':
        return ['EN_PROGRESO', 'RESUELTO'];
      case 'RESUELTO':
        return ['RESUELTO'];
      default:
        return this.estados;
    }
  }

  cambiarEstado(ticket: Ticket, nuevoEstado: string): void {
    const estadoAnterior = ticket.estado;

    if (nuevoEstado === estadoAnterior) {
      return;
    }

    this.ticketService.cambiarEstado(ticket.id, nuevoEstado).subscribe({
      next: (ticketActualizado) => {
        ticket.estado = ticketActualizado.estado;
        this.cdr.detectChanges();
      },
      error: (err) => {
        ticket.estado = estadoAnterior;
        this.cdr.detectChanges();
        console.error(err);
      }
    });
  }

  eliminarTicket(id: number): void {
    if (!confirm('¿Desea eliminar este ticket?')) {
      return;
    }

    this.ticketService.eliminarTicket(id).subscribe({
      next: () => {
        this.tickets = this.tickets.filter(ticket => ticket.id !== id);
        this.cdr.detectChanges();
      },
      error: (err) => console.error(err)
    });
  }

  asignarTecnico(ticket: Ticket, idTecnico: number): void {
    this.ticketService.asignarTecnico(ticket.id, idTecnico).subscribe({
      next: (ticketActualizado) => {
        const index = this.tickets.findIndex(t => t.id === ticket.id);
        if (index !== -1) {
          this.tickets[index] = ticketActualizado;
          this.cdr.detectChanges();
        }
        this.cdr.detectChanges();
      },
      error: (err) => console.error(err)
    });
  }

}