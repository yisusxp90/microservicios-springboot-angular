import { Injectable } from '@angular/core';
import {CommonService} from "./common.service";
import {Curso} from "../models/curso";
import {HttpClient} from "@angular/common/http";
import {BASE_ENDPOINT} from "../config/app";
import {Alumno} from "../models/alumno";
import {Observable} from "rxjs";
import {Examen} from "../models/examen";

@Injectable({
  providedIn: 'root'
})
export class CursoService extends CommonService<Curso> {

  // sobreescribimos elm baseURL de la clase padre
  protected baseURL = BASE_ENDPOINT + '/cursos';

  constructor(http: HttpClient) {
    super(http);
  }

  asignarAlumnos(curso: Curso, alumnos: Alumno[]): Observable<Curso>{
    const url = `${this.baseURL}/asignar-alumnos/${curso.id}`;
    return this.http.put<Curso>(url, alumnos, {headers: this.header});
  }

  eliminarAlumno(curso: Curso, alumno: Alumno): Observable<Curso>{
    const url = `${this.baseURL}/eliminar-alumnos/${curso.id}`;
    return this.http.put<Curso>(url, alumno, {headers: this.header});
  }

  asignarExamenes(curso: Curso, examenes: Examen[]): Observable<Curso>{
    const url = `${this.baseURL}/asignar-examen/${curso.id}`;
    return this.http.put<Curso>(url, examenes, {headers: this.header});
  }

  eliminarExamen(curso: Curso, examen: Examen): Observable<Curso>{
    const url = `${this.baseURL}/eliminar-examen/${curso.id}`;
    return this.http.put<Curso>(url, examen, {headers: this.header});
  }

  obtenerCursoPorAlumnoId(alumno: Alumno): Observable<Curso> {
    const url = `${this.baseURL}/alumno/${alumno.id}`;
    return this.http.get<Curso>(url);
  }
}
