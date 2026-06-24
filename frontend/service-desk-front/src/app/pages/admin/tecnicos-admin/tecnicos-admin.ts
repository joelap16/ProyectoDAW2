import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { TecnicoService } from '../../../services/tecnico/tecnico-service';
import { CategoriaService } from '../../../services/categoria/categoria-service';

import { Tecnico } from '../../../model/tecnico/tecnico';
import { Categoria } from '../../../model/categoria/categoria';

@Component({
  selector: 'app-tecnicos-admin',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './tecnicos-admin.html',
  styleUrl: './tecnicos-admin.scss'
})
export class TecnicosAdmin implements OnInit {

  tecnicos: Tecnico[] = [];

  categorias: Categoria[] = [];

  constructor(
    private tecnicoService: TecnicoService,
    private categoriaService: CategoriaService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {

    this.cargarTecnicos();

    this.cargarCategorias();

  }

  cargarTecnicos(): void {

    this.tecnicoService.obtenerTecnicos()

      .subscribe({

        next: (data) => {

          this.tecnicos = data;

          this.cdr.detectChanges();

        },

        error: (err) => {

          console.error(err);

        }

      });

  }

  cargarCategorias(): void {

    this.categoriaService.obtenerCategorias()

      .subscribe({

        next: (data) => {

          this.categorias = data;

          this.cdr.detectChanges();

        },

        error: (err) => {

          console.error(err);

        }

      });

  }

  asignarCategoria(
    tecnicoId: number,
    categoriaId: number
  ): void {

    this.tecnicoService
      .asignarCategoria(tecnicoId, categoriaId)

      .subscribe({

        next: () => {

          // Recargar tabla
          this.cargarTecnicos();

          this.cdr.detectChanges();

        },

        error: (err) => {

          console.error(err);

        }

      });

  }

}