import { Injectable } from '@angular/core';
import {CommonService} from "./common.service";
import {Examen} from "../models/examen";
import {HttpClient} from "@angular/common/http";
import {BASE_ENDPOINT} from "../config/app";
import {Observable} from "rxjs";
import {Asignatura} from "../models/asignatura";

@Injectable({
  providedIn: 'root'
})
export class ExamenService extends CommonService<Examen> {

  // sobreescribimos elm baseURL de la clase padre
  protected baseURL = BASE_ENDPOINT + '/examenes';

  constructor(http: HttpClient) {
    super(http);
  }

  public findAllAsignaturas(): Observable<Asignatura[]>{
    const url = `${this.baseURL}/asignaturas`;
    return this.http.get<Asignatura[]>(url);
  }

  public filtrarPorNombre(nombre: string): Observable<Examen[]> {
    const url = `${this.baseURL}/filtrar/${nombre}`;
    return this.http.get<Examen[]>(url);
  }
}
