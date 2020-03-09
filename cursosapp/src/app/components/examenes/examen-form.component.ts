import { Component, OnInit } from '@angular/core';
import {CommonFormComponent} from "../common-form.component";
import {Examen} from "../../models/examen";
import {ExamenService} from "../../services/examen.service";
import {ActivatedRoute, Router} from "@angular/router";
import {Asignatura} from "../../models/asignatura";
import {Pregunta} from "../../models/pregunta";
import Swal from "sweetalert2";

@Component({
  selector: 'app-examen-form',
  templateUrl: './examen-form.component.html',
  styleUrls: ['./examen-form.component.css']
})
export class ExamenFormComponent extends CommonFormComponent<Examen, ExamenService> implements OnInit {

  asignaturasPadre: Asignatura[] = [];
  asignaturasHija: Asignatura[] = [];
  errorPreguntas: string;

  constructor(service: ExamenService,
              router: Router,
              route: ActivatedRoute) {
    super(service, router, route);
    this.titulo = 'Crear Examen';
    this.model = new Examen();
    this.ruta = '/examenes';
    this.nombreModel = Examen.name;
  }

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      const id: number = +params.get('id');
      if(id){
        this.service.ver(id).subscribe(model => this.model = model);
        this.titulo = 'Editar ' + this.nombreModel;
        // cargar asignaturas hija en editar
        this.service.findAllAsignaturas().subscribe(asignaturas => {
          this.asignaturasHija = asignaturas
            .filter(a => a.padre && a.padre.id === this.model.asignaturaPadre.id);
        });
      }
    });
    // cargar asignaturas padre
    this.service.findAllAsignaturas()
      .subscribe(asignaturas => {
        this.asignaturasPadre = asignaturas.filter(a => !a.padre);
      });
  }

  public crear(): void {
    if(this.model.preguntas.length === 0){
      //Swal.fire('Error Preguntas', 'Examen debe teber preguntas', 'error');
      this.errorPreguntas = 'Examen debe teber preguntas';
      return;
    }
    this.errorPreguntas = undefined;
    this.eliminarPreguntasVacias();
    super.crear();
  }

  public editar(): void {
    if(this.model.preguntas.length === 0){
      //Swal.fire('Error Preguntas', 'Examen debe teber preguntas', 'error');
      this.errorPreguntas = 'Examen debe teber preguntas';
      return;
    }
    this.errorPreguntas = undefined;
    this.eliminarPreguntasVacias();
    super.editar();
  }

  cargarHijos(): void {
    this.asignaturasHija = this.model.asignaturaPadre ? this.model.asignaturaPadre.hijos : [];
  }

  compararAsignatura(a1: Asignatura, a2: Asignatura): boolean {
    if(a1 === undefined && a2 === undefined){
      return true;
    }
    return (a1 === null || a2 === null || a1 === undefined || a2 === undefined) ? false : a1.id === a2.id;
  }

  agregarPregunta(): void {
    this.model.preguntas.push(new Pregunta());
  }

  asignarTexto(pregunta: Pregunta, event: any): void {
    pregunta.texto = event.target.value;
  }

  eliminarPregunta(pregunta): void {
    this.model.preguntas = this.model.preguntas.filter(p => p.texto !== pregunta.texto)
  }

  eliminarPreguntasVacias(){
    // != chequea null y undefined
    this.model.preguntas = this.model.preguntas.filter(p => p.texto != null && p.texto.length > 0);
  }
}
