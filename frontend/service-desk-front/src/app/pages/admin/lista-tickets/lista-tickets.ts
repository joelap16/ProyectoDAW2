import { Component, OnInit } from '@angular/core';

import { TicketService } from '../../../services/ticket/ticket-service';
import { Ticket } from '../../../model/ticket/ticket';

import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-lista-tickets',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './lista-tickets.html',
  styleUrl: './lista-tickets.scss'
})
export class ListaTickets implements OnInit {

  tickets: Ticket[] = [];

  constructor(private ticketService: TicketService) {}

  ngOnInit(): void {

    console.log("ngOnInit ejecutado");

    this.cargarTickets();

  }

  cargarTickets() {

    console.log("Cargando tickets...");

    this.ticketService.obtenerTickets()

      .subscribe({

        next: (data) => {

          console.log("Respuesta del backend:", data);

          this.tickets = data;

          console.log("Tickets asignados:", this.tickets);

        },

        error: (err) => {

          console.error(err);

        }

      });

  }

}