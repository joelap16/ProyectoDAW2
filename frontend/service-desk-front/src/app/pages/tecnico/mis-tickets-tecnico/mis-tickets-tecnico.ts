import { Component, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';

import { Ticket } from '../../../model/ticket/ticket';
import { Categoria } from '../../../model/categoria/categoria';

import { TecnicoService } from '../../../services/tecnico/tecnico-service';
import { CategoriaService } from '../../../services/categoria/categoria-service';

@Component({
  selector: 'app-mis-tickets-tecnico',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    RouterModule
  ],
  templateUrl: './mis-tickets-tecnico.html',
  styleUrl: './mis-tickets-tecnico.scss'
})
export class MisTicketsTecnicoComponent {

  tickets: Ticket[] = [];

  categorias: Categoria[] = [];

  cargando = true;
  error = '';

  filtroEstado = '';
  filtroCategoria = '';

  constructor(
    private tecnicoService: TecnicoService,
    private categoriaService: CategoriaService,
    private cd: ChangeDetectorRef
  ) {}

  ngOnInit(): void {

    this.cargarCategorias();
    this.cargarTickets();

  }

  cargarCategorias(): void {

    this.categoriaService.obtenerCategorias().subscribe({

      next: (data: Categoria[]) => {

        this.categorias = data;

        this.cd.detectChanges();

      },

      error: (err: any) => {

        console.error(err);

      }

    });

  }

  cargarTickets(): void {

    this.cargando = true;
    this.error = '';

    this.tecnicoService
      .obtenerMisTickets(
        this.filtroEstado,
        this.filtroCategoria
      )
      .subscribe({

        next: (data: Ticket[]) => {

          this.tickets = data;
          this.cargando = false;

          this.cd.detectChanges();

        },

        error: (err: any) => {

          console.error(err);

          this.error = 'No se pudieron cargar los tickets.';
          this.cargando = false;

          this.cd.detectChanges();

        }

      });

  }

  aplicarFiltros(): void {

    this.cargarTickets();

  }

  limpiarFiltros(): void {

    this.filtroEstado = '';
    this.filtroCategoria = '';

    this.cargarTickets();

  }

}