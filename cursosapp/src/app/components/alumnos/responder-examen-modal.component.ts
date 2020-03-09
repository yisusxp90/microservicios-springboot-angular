import {Component, Inject, OnInit} from '@angular/core';
import {Examen} from "../../models/examen";
import {Curso} from "../../models/curso";
import {Alumno} from "../../models/alumno";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {Pregunta} from "../../models/pregunta";
import {Respuesta} from "../../models/respuesta";

@Component({
  selector: 'app-responder-examen-modal',
  templateUrl: './responder-examen-modal.component.html',
  styleUrls: ['./responder-examen-modal.component.css']
})
export class ResponderExamenModalComponent implements OnInit {

  curso: Curso;
  examen: Examen;
  alumno: Alumno;
  respuestas: Map<number, Respuesta> = new Map<number, Respuesta>();

  constructor(@Inject(MAT_DIALOG_DATA) public data: any,
              public modalRef: MatDialogRef<ResponderExamenModalComponent>) { }

  ngOnInit(): void {
    this.curso = this.data.curso as Curso;
    this.examen = this.data.examen as Examen;
    this.alumno = this.data.alumno as Alumno;
  }

  cancelar(): void{
    this.modalRef.close();
  }

  responder(pregunta: Pregunta, event: any): void {
    const textoRespuesta = event.target.value as string;
    const respuesta = new Respuesta();
    respuesta.alumno = this.alumno;
    respuesta.pregunta = pregunta;
    const examen = new Examen();
    examen.id = this.examen.id;
    examen.nombre = this.examen.nombre;
    respuesta.pregunta.examen = examen;
    respuesta.texto = textoRespuesta;

    this.respuestas.set(pregunta.id, respuesta);
  }
}
