import {Component, OnInit, ViewChild} from '@angular/core';
import {Curso} from "../../models/curso";
import {ActivatedRoute} from "@angular/router";
import {CursoService} from "../../services/curso.service";
import {AlumnoService} from "../../services/alumno.service";
import {Alumno} from "../../models/alumno";
import {SelectionModel} from "@angular/cdk/collections";
import Swal from "sweetalert2";
import {MatTableDataSource} from "@angular/material/table";
import {MatPaginator} from "@angular/material/paginator";

@Component({
  selector: 'app-asignar-alumnos',
  templateUrl: './asignar-alumnos.component.html',
  styleUrls: ['./asignar-alumnos.component.css']
})
export class AsignarAlumnosComponent implements OnInit {

  curso: Curso;
  alumnosAsignar: Alumno[] = [];
  alumnosAsignados: Alumno[] = [];
  mostrarColumnas: string[] = ['seleccion', 'nombre', 'apellido'];
  mostrarColumnasAlumnosAsignados: string[] = ['id', 'nombre', 'apellido', 'email', 'eliminar'];
  tavIndex = 0;
  seleccion: SelectionModel<Alumno> = new SelectionModel<Alumno>(true, []);
  idCurso: number;

  dataSource: MatTableDataSource<Alumno>;
  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;

  constructor(private route: ActivatedRoute,
              private cursoService: CursoService,
              private alumnoService: AlumnoService) { }

  ngOnInit(): void {

    this.route.paramMap.subscribe(params => {
      this.idCurso = +params.get('id');
      this.obtenerInfoCurso();
      this.obtenerAlumnos();
    });
  }

  obtenerInfoCurso() {
    this.cursoService.ver(this.idCurso).subscribe(curso => {
      this.curso = curso;
      this.alumnosAsignados = this.curso.alumnos;
      this.iniciarPaginador();
    });
  }

  iniciarPaginador(): void {
    console.log(this.paginator);
    this.dataSource = new MatTableDataSource<Alumno>(this.alumnosAsignados);
    this.dataSource.paginator = this.paginator;
    this.paginator._intl.itemsPerPageLabel = 'Registros por pagina:';
    this.paginator._intl.getRangeLabel = (page: number, pageSize: number, length: number) => {
      return `${this.paginator.pageIndex + 1} de ${this.paginator.getNumberOfPages()}`;
    };
  }

  filtrarAlumnosPorNombre(nombre: any): void {
    nombre = nombre !== undefined ? nombre.trim() : '';
    if(nombre !== ''){
      this.alumnoService.filtrarPorNombre(nombre).subscribe(alumnos => {
        this.alumnosAsignar = alumnos.filter(a => {
          let filtrar = true;
          this.curso.alumnos.forEach(ca => {
            if(a.id === ca.id){
              filtrar = false;
            }
          });
          return filtrar;
        });
      });
    }else{
      this.obtenerAlumnos();
    }
  }

  private obtenerAlumnos() {
    this.alumnoService.listar().subscribe(alumnos => {
      this.alumnosAsignar = alumnos.filter(a => {
        let filtrar = true;
        this.curso.alumnos?.forEach(ca => {
          if(a.id === ca.id){
            filtrar = false;
          }
        });
        return filtrar;
      });
    });
  }

  estanTodosSeleccionados(): boolean {
    const seleccionados = this.seleccion.selected.length;
    const numeroAlumnos = this.alumnosAsignar.length;
    return (seleccionados == numeroAlumnos);
  }

  seleccionarDesSeleccionarTodos(): void {
    this.estanTodosSeleccionados() ? this.seleccion.clear(): this.alumnosAsignar.forEach(a => this.seleccion.select(a));
  }

  asignar(): void {
    this.cursoService.asignarAlumnos(this.curso, this.seleccion.selected).subscribe(c => {
      Swal.fire('Asignados:', `Alumnos asignados con exito al curso ${this.curso.nombre}`, 'success');
      // mergeamos los alumnos q ya estaban asignados con los que se acaban de asignar
      this.alumnosAsignados = this.alumnosAsignados.concat(this.seleccion.selected);
      // para q se cambie de tav y muestre e tab de alumnos seleccionados
      this.tavIndex = 2;
      // mergeamos los ya asignados al curso para poder comparar en obtenerAumnos()
      this.curso.alumnos = this.curso.alumnos.concat(this.seleccion.selected);
      // refrescamos la tabla con valores de aumnos no asignados
      this.obtenerAlumnos();
      this.iniciarPaginador();
      this.seleccion.clear();
    }, e => {
      if(e.status === 500){
        const mensaje = e.error.message as string;
        if(mensaje.indexOf('ConstraintViolationException') > -1){
          Swal.fire('Error asignando alumno', 'No se puede asignar este alumno ya que esta asociado en otro curso', 'error')
        }
      }
    });
  }

  eliminaralumno(alumno: Alumno): void{


    Swal.fire({
      title: 'Eliminar...',
      text: `Esta seguro que desea eliminar a ${alumno.nombre}?`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Confirmar!'
    }).then((result) => {
      if (result.value) {
        this.cursoService.eliminarAlumno(this.curso, alumno)
          .subscribe(curso => {
            this.alumnosAsignados = this.alumnosAsignados.filter(a => a.id !== alumno.id);
            this.obtenerInfoCurso();
            this.obtenerAlumnos();
            Swal.fire('Eliminado', `Alumno ${alumno.nombre} eliminado con exito.`, 'success');
          });
      }
    });
  }

}
