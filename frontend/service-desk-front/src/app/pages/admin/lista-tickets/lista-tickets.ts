import { Component, OnInit } from '@angular/core';

import { TicketService } from '../../../services/ticket/ticket-service';
import { Ticket } from '../../../model/ticket/ticket';

@Component({
  selector: 'app-lista-tickets',
  standalone: true,
  imports: [],
  templateUrl: './lista-tickets.html',
  styleUrl: './lista-tickets.scss'
})
export class ListaTickets implements OnInit {

  tickets: Ticket[] = [];

  constructor(private ticketService: TicketService) {}

  ngOnInit(): void {

    this.cargarTickets();

  }

  cargarTickets() {

    this.ticketService.obtenerTickets()

      .subscribe({

        next: (data) => {

          this.tickets = data;

          console.log(this.tickets);

        },

        error: (err) => {

          console.error(err);

        }

      });

  }

}