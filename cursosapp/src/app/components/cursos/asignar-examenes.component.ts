import {Component, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {CursoService} from "../../services/curso.service";
import {ExamenService} from "../../services/examen.service";
import {Curso} from "../../models/curso";
import {FormControl} from "@angular/forms";
import {Examen} from "../../models/examen";
import {flatMap, map} from "rxjs/operators";
import {MatAutocompleteSelectedEvent} from "@angular/material/autocomplete";
import Swal from "sweetalert2";
import {MatTableDataSource} from "@angular/material/table";
import {MatPaginator} from "@angular/material/paginator";
import {Alumno} from "../../models/alumno";

@Component({
  selector: 'app-asignar-examenes',
  templateUrl: './asignar-examenes.component.html',
  styleUrls: ['./asignar-examenes.component.css']
})
export class AsignarExamenesComponent implements OnInit {

  curso: Curso;
  autocompleteControl = new FormControl(); // atributo del input
  examenesFiltrados : Examen[] = [];
  examenesAsignar: Examen[] = [];
  mostrarColumnas = ['nombre', 'asignatura', 'eliminar'];
  examenes: Examen[] = [];
  mostrarColumnasExamenesAsignados: string[] = ['id', 'nombre', 'asignaturas', 'eliminar'];
  tavIndex = 0;

  dataSource: MatTableDataSource<Examen>;
  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;

  constructor(private route: ActivatedRoute,
              private router: Router,
              private cursoService: CursoService,
              private examenService: ExamenService) { }

  ngOnInit(): void {

    this.route.paramMap.subscribe(params => {
      const id:number = +params.get('id');
      this.cursoService.ver(id).subscribe(curso => {
        this.curso = curso;
        this.examenes = curso.examenes;
        this.iniciarPaginador();
      });
    });
    this.autocompleteControl.valueChanges.pipe(
      map(value => typeof value === 'string' ? value : value.nombre),
      flatMap(value => value ? this.examenService.filtrarPorNombre(value): [])
    ).subscribe(examenes => this.examenesFiltrados = examenes);
  }

  private iniciarPaginador(): void {
    this.dataSource = new MatTableDataSource<Examen>(this.examenes);
    this.dataSource.paginator = this.paginator;
    this.paginator._intl.itemsPerPageLabel = 'Registros por pagina:';
    this.paginator._intl.getRangeLabel = (page: number, pageSize: number, length: number) => {
      return `${this.paginator.pageIndex + 1} de ${this.paginator.getNumberOfPages()}`;
    };
  }

  mostrarNombre(examen?: Examen): string {
    return examen ? examen.nombre : '';
  }

  seleccionarExamen(event: MatAutocompleteSelectedEvent): void {
    const examen = event.option.value as Examen;

    if(!this.existe(examen.id)){
      this.examenesAsignar = this.examenesAsignar.concat(examen);
    }else {
      Swal.fire('Error', `El examen ${examen.nombre} ya esta asignado al curso`, 'error');
    }
    this.autocompleteControl.setValue("");
    event.option.deselect();
    event.option.focus();

  }

  private existe(id: number): boolean {
    let existe = false;
    this.examenesAsignar.concat(this.examenes)
      .forEach(e => {
        if(id === e.id){
          existe = true;
        }
      });
    return existe;
  }

  eliminarExamenDeAsignar(examen: Examen): void {
    this.examenesAsignar = this.examenesAsignar.filter(e => examen.id !== e.id);
  }

  asignar(): void {
    this.cursoService.asignarExamenes(this.curso, this.examenesAsignar).subscribe(curso => {
      this.examenes = this.examenes.concat(this.examenesAsignar);
      this.examenesAsignar = [];
      this.tavIndex = 2;
      this.iniciarPaginador();
      Swal.fire('Asignados', `Examenes asignados con exito al curso ${curso.nombre}`, 'success');
    });
  }

  eliminarExamen(examen: Examen): void{


    Swal.fire({
      title: 'Eliminar...',
      text: `Esta seguro que desea eliminar el examen: ${examen.nombre}?`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Confirmar!'
    }).then((result) => {
      if (result.value) {
        this.cursoService.eliminarExamen(this.curso, examen)
          .subscribe(curso => {
            this.examenes = this.examenes.filter(e => e.id !== examen.id);
            this.iniciarPaginador();
            Swal.fire('Eliminado', `Examen: ${examen.nombre} ha sido eliminado con exito.`, 'success');
          });
      }
    });
  }
}
