import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { TicketService } from '../../../services/ticket/ticket-service';
import { TecnicoService } from '../../../services/tecnico/tecnico-service';
import { CategoriaService } from '../../../services/categoria/categoria-service';

import { Ticket } from '../../../model/ticket/ticket';
import { Tecnico } from '../../../model/tecnico/tecnico';
import { Categoria } from '../../../model/categoria/categoria';

interface FilaCarga {
  nombre: string;
  categoria: string;
  total: number;
  abiertos: number;
  enProgreso: number;
  resueltos: number;
}

interface FilaCategoria {
  categoria: string;
  total: number;
  abiertos: number;
  enProgreso: number;
  resueltos: number;
}

@Component({
  selector: 'app-reportes',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './reportes.html',
  styleUrl: './reportes.scss'
})
export class ReportesComponent implements OnInit {

  todosTickets: Ticket[] = [];
  ticketsFiltrados: Ticket[] = [];
  tecnicos: Tecnico[] = [];
  categorias: Categoria[] = [];
  cargaTecnicos: FilaCarga[] = [];
  resumenPorCategoria: FilaCategoria[] = [];

  filtroEstado = '';
  filtroCategoria = '';
  cargando = true;

  constructor(
    private ticketService: TicketService,
    private tecnicoService: TecnicoService,
    private categoriaService: CategoriaService,
    private cd: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.categoriaService.obtenerCategorias().subscribe({
     next: (data) => {

      this.categorias = data;

      this.cd.detectChanges();

    },
    error: (err) => {

      console.error(err);

      this.cd.detectChanges();

    }
  });

    this.tecnicoService.obtenerTecnicos().subscribe({
      next: (data) => {

        this.tecnicos = data;

        this.calcularCargaTecnicos();

        this.cd.detectChanges();

      },

    error: (err) => {

      console.error(err);

      this.cd.detectChanges();

    }

  });

    this.ticketService.obtenerTicketsAdmin().subscribe({

      next: (data) => {

        this.todosTickets = data;
        this.ticketsFiltrados = [...data];

        this.calcularResumenCategoria();
        this.calcularCargaTecnicos();

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

  aplicarFiltros(): void {

    this.ticketsFiltrados = this.todosTickets.filter(t => {

      const okEstado =
        !this.filtroEstado ||
        t.estado === this.filtroEstado;

      const okCat =
        !this.filtroCategoria ||
        t.categoria === this.filtroCategoria;

      return okEstado && okCat;

    });

    this.calcularResumenCategoria();

    this.calcularCargaTecnicos();

    this.cd.detectChanges();

  }

  limpiarFiltros(): void {
    this.filtroEstado = '';
    this.filtroCategoria = '';
    this.ticketsFiltrados = [...this.todosTickets];
    this.calcularResumenCategoria();
    this.calcularCargaTecnicos();

    this.cd.detectChanges();
  }

  contarPorEstado(estado: string): number {
    return this.ticketsFiltrados.filter(t => t.estado === estado).length;
  }

  calcularResumenCategoria(): void {
    const mapa = new Map<string, FilaCategoria>();
    for (const t of this.ticketsFiltrados) {
      const cat = t.categoria || 'SIN CATEGORÍA';
      if (!mapa.has(cat)) {
        mapa.set(cat, { categoria: cat, total: 0, abiertos: 0, enProgreso: 0, resueltos: 0 });
      }
      const fila = mapa.get(cat)!;
      fila.total++;
      if (t.estado === 'ABIERTO') fila.abiertos++;
      else if (t.estado === 'EN_PROGRESO') fila.enProgreso++;
      else if (t.estado === 'RESUELTO') fila.resueltos++;
    }
    this.resumenPorCategoria = Array.from(mapa.values())
      .sort((a, b) => b.total - a.total);
  }

  calcularCargaTecnicos(): void {
    if (!this.tecnicos.length) return;
    this.cargaTecnicos = this.tecnicos.map(tec => {
      const misTickets = this.ticketsFiltrados.filter(t => t.tecnico === tec.nombreCompleto);
      return {
        nombre: tec.nombreCompleto,
        categoria: tec.categoria,
        total: misTickets.length,
        abiertos: misTickets.filter(t => t.estado === 'ABIERTO').length,
        enProgreso: misTickets.filter(t => t.estado === 'EN_PROGRESO').length,
        resueltos: misTickets.filter(t => t.estado === 'RESUELTO').length
      };
    }).sort((a, b) => b.total - a.total);
  }

  barraProgreso(total: number): number {
    const max = Math.max(...this.cargaTecnicos.map(t => t.total), 1);
    return Math.round((total / max) * 100);
  }

  colorBarra(total: number): string {
    const max = Math.max(...this.cargaTecnicos.map(t => t.total), 1);
    const pct = total / max;
    if (pct > 0.75) return '#c0392b';
    if (pct > 0.4) return '#e67e22';
    return '#1a8a3e';
  }

  // ========================
  // EXPORTAR PDF
  // ========================

  private async getPDF(): Promise<any> {
    const { jsPDF } = await import('jspdf');
    const autoTable = (await import('jspdf-autotable')).default;
    return { jsPDF, autoTable };
  }

  private headerPDF(doc: any, titulo: string): number {
    doc.setFillColor(30, 58, 95);
    doc.rect(0, 0, 210, 28, 'F');
    doc.setTextColor(255, 255, 255);
    doc.setFontSize(16);
    doc.setFont('helvetica', 'bold');
    doc.text('Service Desk — Reporte', 14, 12);
    doc.setFontSize(11);
    doc.setFont('helvetica', 'normal');
    doc.text(titulo, 14, 20);
    doc.setTextColor(0, 0, 0);
    const fecha = new Date().toLocaleDateString('es-PE', { day:'2-digit', month:'2-digit', year:'numeric' });
    doc.setFontSize(9);
    doc.text('Generado: ' + fecha, 150, 24);
    return 36;
  }

  async exportarResumenPDF(): Promise<void> {
    const { jsPDF, autoTable } = await this.getPDF();
    const doc = new jsPDF();
    const y = this.headerPDF(doc, 'Resumen Estadístico');

    doc.setFontSize(11);
    doc.setFont('helvetica', 'bold');
    doc.text('Totales generales', 14, y);

    const totales = [
      ['Total tickets', String(this.ticketsFiltrados.length)],
      ['Abiertos', String(this.contarPorEstado('ABIERTO'))],
      ['En Progreso', String(this.contarPorEstado('EN_PROGRESO'))],
      ['Resueltos', String(this.contarPorEstado('RESUELTO'))],
    ];

    autoTable(doc, {
      startY: y + 4,
      head: [['Indicador', 'Cantidad']],
      body: totales,
      theme: 'grid',
      headStyles: { fillColor: [30, 58, 95] },
      columnStyles: { 1: { halign: 'center', fontStyle: 'bold' } },
      margin: { left: 14 },
      tableWidth: 100
    });

    const y2 = (doc as any).lastAutoTable.finalY + 10;
    doc.setFont('helvetica', 'bold');
    doc.text('Detalle por categoría', 14, y2);

    autoTable(doc, {
      startY: y2 + 4,
      head: [['Categoría', 'Total', 'Abiertos', 'En Progreso', 'Resueltos']],
      body: this.resumenPorCategoria.map(f => [f.categoria, f.total, f.abiertos, f.enProgreso, f.resueltos]),
      theme: 'striped',
      headStyles: { fillColor: [30, 58, 95] },
      margin: { left: 14 }
    });

    doc.save('reporte-resumen.pdf');
  }

  async exportarTecnicosPDF(): Promise<void> {
    const { jsPDF, autoTable } = await this.getPDF();
    const doc = new jsPDF();
    const y = this.headerPDF(doc, 'Carga de Trabajo por Técnico');

    autoTable(doc, {
      startY: y,
      head: [['Técnico', 'Categoría', 'Total', 'Abiertos', 'En Progreso', 'Resueltos']],
      body: this.cargaTecnicos.map(t => [t.nombre, t.categoria, t.total, t.abiertos, t.enProgreso, t.resueltos]),
      theme: 'striped',
      headStyles: { fillColor: [30, 58, 95] },
      margin: { left: 14 }
    });

    doc.save('reporte-tecnicos.pdf');
  }

  async exportarTicketsPDF(): Promise<void> {
    const { jsPDF, autoTable } = await this.getPDF();
    const doc = new jsPDF({ orientation: 'landscape' });
    const y = this.headerPDF(doc, 'Listado de Tickets');

    const filtrosActivos = [];
    if (this.filtroEstado) filtrosActivos.push('Estado: ' + this.filtroEstado);
    if (this.filtroCategoria) filtrosActivos.push('Categoría: ' + this.filtroCategoria);
    if (filtrosActivos.length) {
      doc.setFontSize(9);
      doc.setTextColor(100, 100, 100);
      doc.text('Filtros: ' + filtrosActivos.join(' | '), 14, y - 4);
      doc.setTextColor(0, 0, 0);
    }

    autoTable(doc, {
      startY: y,
      head: [['ID', 'Título', 'Categoría', 'Estado', 'Usuario', 'Técnico', 'Fecha']],
      body: this.ticketsFiltrados.map(t => [
        '#' + t.id,
        t.titulo,
        t.categoria,
        t.estado,
        t.usuario,
        t.tecnico || '—',
        t.fechaCreacion ? new Date(t.fechaCreacion).toLocaleDateString('es-PE') : '—'
      ]),
      theme: 'striped',
      headStyles: { fillColor: [30, 58, 95] },
      margin: { left: 14 },
      styles: { fontSize: 9 }
    });

    doc.save('reporte-tickets.pdf');
  }

  // ========================
  // EXPORTAR EXCEL
  // ========================

  private async getXLSX(): Promise<any> {
    return await import('xlsx');
  }

  private descargarExcel(wb: any, XLSX: any, nombre: string): void {
    XLSX.writeFile(wb, nombre);
  }

  async exportarResumenExcel(): Promise<void> {
    const XLSX = await this.getXLSX();
    const wb = XLSX.utils.book_new();

    // Hoja totales
    const wsTotales = XLSX.utils.aoa_to_sheet([
      ['Indicador', 'Cantidad'],
      ['Total tickets', this.ticketsFiltrados.length],
      ['Abiertos', this.contarPorEstado('ABIERTO')],
      ['En Progreso', this.contarPorEstado('EN_PROGRESO')],
      ['Resueltos', this.contarPorEstado('RESUELTO')],
    ]);
    XLSX.utils.book_append_sheet(wb, wsTotales, 'Totales');

    // Hoja por categoría
    const dataCat = [
      ['Categoría', 'Total', 'Abiertos', 'En Progreso', 'Resueltos'],
      ...this.resumenPorCategoria.map(f => [f.categoria, f.total, f.abiertos, f.enProgreso, f.resueltos])
    ];
    const wsCat = XLSX.utils.aoa_to_sheet(dataCat);
    XLSX.utils.book_append_sheet(wb, wsCat, 'Por Categoría');

    this.descargarExcel(wb, XLSX, 'reporte-resumen.xlsx');
  }

  async exportarTecnicosExcel(): Promise<void> {
    const XLSX = await this.getXLSX();
    const wb = XLSX.utils.book_new();

    const data = [
      ['Técnico', 'Categoría', 'Total', 'Abiertos', 'En Progreso', 'Resueltos'],
      ...this.cargaTecnicos.map(t => [t.nombre, t.categoria, t.total, t.abiertos, t.enProgreso, t.resueltos])
    ];
    const ws = XLSX.utils.aoa_to_sheet(data);
    XLSX.utils.book_append_sheet(wb, ws, 'Carga Técnicos');

    this.descargarExcel(wb, XLSX, 'reporte-tecnicos.xlsx');
  }

  async exportarTicketsExcel(): Promise<void> {
    const XLSX = await this.getXLSX();
    const wb = XLSX.utils.book_new();

    const data = [
      ['ID', 'Título', 'Categoría', 'Estado', 'Usuario', 'Técnico', 'Fecha Creación'],
      ...this.ticketsFiltrados.map(t => [
        t.id,
        t.titulo,
        t.categoria,
        t.estado,
        t.usuario,
        t.tecnico || '',
        t.fechaCreacion ? new Date(t.fechaCreacion).toLocaleDateString('es-PE') : ''
      ])
    ];
    const ws = XLSX.utils.aoa_to_sheet(data);
    XLSX.utils.book_append_sheet(wb, ws, 'Tickets');

    this.descargarExcel(wb, XLSX, 'reporte-tickets.xlsx');
  }
}
