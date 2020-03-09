import { Injectable } from '@angular/core';
import {BASE_ENDPOINT} from "../config/app";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {Respuesta} from "../models/respuesta";
import {Alumno} from "../models/alumno";
import {Examen} from "../models/examen";

@Injectable({
  providedIn: 'root'
})
export class RespuestaService{

  protected baseURL = BASE_ENDPOINT + '/respuestas';
  private header: HttpHeaders = new HttpHeaders({'Content-Type': 'application/json'});

  constructor(private http: HttpClient) {
  }

  public crear(respuestas: Respuesta[]): Observable<Respuesta[]> {
    const url = `${this.baseURL}`;
    return this.http.post<Respuesta[]>(url, respuestas, {headers: this.header});
  }

  public obtenerRespuestasPorAlumnoPorExamen(alumno: Alumno, examen: Examen): Observable<Respuesta[]> {
    const url = `${this.baseURL}/alumno/${alumno.id}/${examen.id}`;
    return this.http.get<Respuesta[]>(url);
  }


}
