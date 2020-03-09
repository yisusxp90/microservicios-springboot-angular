import {OnInit, ViewChild} from '@angular/core';
import Swal from 'sweetalert2';
import {MatPaginator, PageEvent} from "@angular/material/paginator";
import {Generic} from "../models/generic";
import {CommonService} from "../services/common.service";

// E entity S service
export abstract class CommonListarComponent<E extends Generic, S extends CommonService<E>> implements OnInit {

  titulo: string;
  protected nombreModel: string;
  lista: E[];
  totalRegistros = 0;
  totalPorPagina = 4;
  paginaActual = 0;

  @ViewChild(MatPaginator) paginator: MatPaginator;

  constructor(protected service: S) { }

  ngOnInit(): void {
    this.calcularRangos();
  }

  paginar(event: PageEvent) {
    this.paginaActual = event.pageIndex;
    this.totalPorPagina = event.pageSize;
    this.calcularRangos();
  }

  calcularRangos() {
    this.service.listarPaginas(this.paginaActual.toString(), this.totalPorPagina.toString())
      .subscribe(p => {
        this.lista = p.content as E[];
        this.totalRegistros = p.totalElements as number;
        this.paginator._intl.itemsPerPageLabel = 'Registros por pagina';
      });
  }

  eliminar(e: E): void {

    Swal.fire({
      title: 'Eliminar...',
      text: `Esta seguro que desea eliminar a ${e.nombre}?`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Confirmar!'
    }).then((result) => {
      if (result.value) {
        this.service.eliminar(e.id).subscribe(() => {
          this.calcularRangos();
          Swal.fire('Eliminacion Exitosa.', `${this.nombreModel} ${e.nombre} eliminado exitosamente.`, 'success');
        });
      }
    });
  }

}
