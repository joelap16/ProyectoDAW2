import { CommonModule } from '@angular/common';
import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TicketService } from '../../../services/ticket/ticket-service';
import { Ticket } from '../../../model/ticket/ticket';

@Component({
  selector: 'app-mis-tickets',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './mis-tickets.html',
  styleUrl: './mis-tickets.scss'
})
export class MisTicketsComponent implements OnInit {
  tickets: Ticket[] = [];
  isLoading = false;
  errorMessage = '';

  constructor(
    private ticketService: TicketService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.cargarTickets();
  }

  cargarTickets(): void {
    this.isLoading = true;
    this.errorMessage = '';
    this.cdr.detectChanges();

    this.ticketService.obtenerMisTicketsUsuario().subscribe({
      next: (data: Ticket[]) => {
        this.tickets = data;
        this.isLoading = false;
        this.cdr.detectChanges();
      },
      error: (err: any) => {
        this.isLoading = false;
        this.errorMessage =
          err?.error?.message || 'No se pudieron cargar tus tickets.';
        this.cdr.detectChanges();
      }
    });
  }

  trackByTicketId(index: number, item: Ticket): number {
    return item.id;
  }
}