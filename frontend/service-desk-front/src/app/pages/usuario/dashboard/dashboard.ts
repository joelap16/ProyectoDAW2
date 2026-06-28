import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

import { TicketService } from '../../../services/ticket/ticket-service';
import { Ticket } from '../../../model/ticket/ticket';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.scss',
})
export class DashboardUsuario implements OnInit {

  total = 0;
  abiertos = 0;
  enProgreso = 0;
  resueltos = 0;

  cargando = true;

  ultimosTickets: Ticket[] = [];

  // Gráfico de dona
  donutGradient = 'conic-gradient(#eef1f7 0% 100%)';

  estadosLegend: {
    label: string;
    color: string;
    value: number;
    pct: number;
  }[] = [];

  constructor(
    private ticketService: TicketService,
    private cd: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.cargarDashboard();
  }

  cargarDashboard(): void {

    this.cargando = true;

    this.ticketService.obtenerMisTicketsUsuario().subscribe({

      next: (tickets) => {

        this.total = tickets.length;

        this.abiertos =
          tickets.filter(t => t.estado === 'ABIERTO').length;

        this.enProgreso =
          tickets.filter(t => t.estado === 'EN_PROGRESO').length;

        this.resueltos =
          tickets.filter(t => t.estado === 'RESUELTO').length;

        this.ultimosTickets =
          tickets.slice(-5).reverse();

        this.calcularDona();

        this.cargando = false;

        this.cd.detectChanges();
      },

      error: (err) => {

        console.error(err);

        this.cargando = false;

        this.cd.detectChanges();
      }

    });

  }

  private calcularDona(): void {

    const total = this.total || 1;

    const data = [
      {
        label: 'Abiertos',
        color: '#1a6fc4',
        value: this.abiertos
      },
      {
        label: 'En Progreso',
        color: '#e0972c',
        value: this.enProgreso
      },
      {
        label: 'Resueltos',
        color: '#1a8a3e',
        value: this.resueltos
      }
    ];

    let acumulado = 0;
    const stops: string[] = [];

    data.forEach(d => {

      const pct = (d.value / total) * 100;

      const inicio = acumulado;
      const fin = acumulado + pct;

      stops.push(`${d.color} ${inicio}% ${fin}%`);

      acumulado = fin;

    });

    this.donutGradient = this.total > 0
      ? `conic-gradient(${stops.join(', ')})`
      : `conic-gradient(#eef1f7 0% 100%)`;

    this.estadosLegend = data.map(d => ({
      ...d,
      pct: Math.round((d.value / total) * 100)
    }));

  }

}