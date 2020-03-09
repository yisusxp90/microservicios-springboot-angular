import {Component, OnInit, ViewChild} from '@angular/core';
import {Alumno} from "../../models/alumno";
import {Curso} from "../../models/curso";
import {AlumnoService} from "../../services/alumno.service";
import {CursoService} from "../../services/curso.service";
import {Examen} from "../../models/examen";
import {ActivatedRoute, Router} from "@angular/router";
import {MatTableDataSource} from "@angular/material/table";
import {MatPaginator} from "@angular/material/paginator";
import {MatDialog} from "@angular/material/dialog";
import {ResponderExamenModalComponent} from "./responder-examen-modal.component";
import {RespuestaService} from "../../services/respuesta.service";
import {Respuesta} from "../../models/respuesta";
import Swal from "sweetalert2";
import {VerExamenModalComponent} from "./ver-examen-modal.component";

@Component({
  selector: 'app-responder-examen',
  templateUrl: './responder-examen.component.html',
  styleUrls: ['./responder-examen.component.css']
})
export class ResponderExamenComponent implements OnInit {

  alumno: Alumno;
  curso: Curso;
  examenes: Examen[];
  mostrarColumnasExamenes = ["id", "nombre", "asignaturas", "preguntas", "responder", "ver"];

  dataSource: MatTableDataSource<Examen>;
  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;

  constructor(private route: ActivatedRoute,
              private router: Router,
              private alumnoService: AlumnoService,
              private cursoService: CursoService,
              public dialog: MatDialog,
              private respuestaService: RespuestaService) { }

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      const id:number = +params.get('id');
      this.alumnoService.ver(id).subscribe(alumno => {
        this.alumno = alumno;
        this.cursoService.obtenerCursoPorAlumnoId(this.alumno).subscribe(curso => {
          this.curso = curso;
          this.examenes = (curso && curso.examenes) ? curso.examenes : [];
          if(this.examenes) this.iniciarPaginador();
        });
      });
    });
  }

  iniciarPaginador(): void {
    this.dataSource = new MatTableDataSource<Examen>(this.examenes);
    this.dataSource.paginator = this.paginator;
    console.log(this.paginator);
    this.paginator._intl.itemsPerPageLabel = 'Registros por pagina:';
    this.paginator._intl.getRangeLabel = (page: number, pageSize: number, length: number) => {
      return `${this.paginator.pageIndex + 1} de ${this.paginator.getNumberOfPages()}`;
    };
  }

  responderExamen(examen: Examen): void {
    const modalRef = this.dialog.open(ResponderExamenModalComponent, {
      width: '750px',
      data: { curso: this.curso, examen: examen, alumno: this.alumno}
    });
    modalRef.afterClosed().subscribe((respuestasMap: Map<number, Respuesta>) => {
      if(respuestasMap){
        const respuestas = Array.from(respuestasMap.values());
        this.respuestaService.crear(respuestas).subscribe(rs => {
          examen.respondido = true;
          Swal.fire('Enviadas', 'Preguntas respondidas con exito', 'success');
        });
      }
    });
  }

  verExamen(examen: Examen): void {
    this.respuestaService.obtenerRespuestasPorAlumnoPorExamen(this.alumno, examen)
      .subscribe(rs => {
        const modalRef = this.dialog.open(VerExamenModalComponent, {
          width: '750px',
          data: { curso: this.curso, examen: examen, respuestas: rs}
        });
        modalRef.afterClosed().subscribe(() => {
          console.log('Modal ver examen cerrdo');
        });
      });
  }


}
