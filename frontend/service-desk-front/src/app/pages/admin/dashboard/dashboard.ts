import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';

import { TicketService } from '../../../services/ticket/ticket-service';
import { UsuarioService } from '../../../services/usuario/usuario-service';
import { TecnicoService } from '../../../services/tecnico/tecnico-service';

import { Ticket } from '../../../model/ticket/ticket';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.scss',
})
export class DashboardAdmin implements OnInit {

  totalTickets = 0;
  abiertos = 0;
  enProgreso = 0;
  resueltos = 0;

  totalUsuarios = 0;
  totalTecnicos = 0;

  ultimosTickets: Ticket[] = [];

  cargando = true;

  // Gráfico de dona
  donutGradient = 'conic-gradient(#eef1f7 0% 100%)';

  estadosLegend: {
    label: string;
    color: string;
    value: number;
    pct: number;
  }[] = [];

  // Barras por categoría
  categorias: {
    nombre: string;
    count: number;
    pct: number;
  }[] = [];

  constructor(
    private ticketService: TicketService,
    private usuarioService: UsuarioService,
    private tecnicoService: TecnicoService,
    private cd: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.cargando = true;

    this.ticketService.obtenerTicketsAdmin().subscribe({
      next: (tickets) => {

        this.totalTickets = tickets.length;

        this.abiertos = tickets.filter(
          t => t.estado === 'ABIERTO'
        ).length;

        this.enProgreso = tickets.filter(
          t => t.estado === 'EN_PROGRESO'
        ).length;

        this.resueltos = tickets.filter(
          t => t.estado === 'RESUELTO'
        ).length;

        this.ultimosTickets = tickets
          .slice(-5)
          .reverse();

        this.calcularDona();
        this.calcularCategorias(tickets);

        this.cargando = false;

        this.cd.detectChanges();
      },

      error: () => {

        this.cargando = false;

        this.cd.detectChanges();
      }
    });

    this.usuarioService.obtenerUsuarios().subscribe({
      next: (data) => {

        this.totalUsuarios = data.length;

        this.cd.detectChanges();
      }
    });

    this.tecnicoService.obtenerTecnicos().subscribe({
      next: (data) => {

        this.totalTecnicos = data.length;

        this.cd.detectChanges();
      }
    });
  }

  private calcularDona(): void {

    const total = this.totalTickets || 1;

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

    this.donutGradient = this.totalTickets > 0
      ? `conic-gradient(${stops.join(', ')})`
      : `conic-gradient(#eef1f7 0% 100%)`;

    this.estadosLegend = data.map(d => ({
      ...d,
      pct: Math.round((d.value / total) * 100)
    }));
  }

  private calcularCategorias(tickets: Ticket[]): void {

    const mapa = new Map<string, number>();

    tickets.forEach(t => {

      const cat = t.categoria || 'Sin categoría';

      mapa.set(
        cat,
        (mapa.get(cat) || 0) + 1
      );
    });

    const max = Math.max(
      ...Array.from(mapa.values()),
      1
    );

    this.categorias = Array.from(mapa.entries())
      .map(([nombre, count]) => ({
        nombre,
        count,
        pct: Math.round((count / max) * 100)
      }))
      .sort((a, b) => b.count - a.count)
      .slice(0, 6);
  }

}