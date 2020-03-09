import { Injectable } from '@angular/core';
import {Alumno} from "../models/alumno";
import {CommonService} from "./common.service";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {BASE_ENDPOINT} from "../config/app";

@Injectable({
  providedIn: 'root'
})
export class AlumnoService extends CommonService<Alumno> {

  // sobreescribimos elm baseURL de la clase padre

  protected baseURL = BASE_ENDPOINT + '/alumnos';

  constructor(http: HttpClient) {
    super(http);
  }

  public crearConFoto(alumno: Alumno, archivo: File): Observable<Alumno>{
    const url = `${this.baseURL}/crear-con-foto`;
    const formData = new FormData();
    formData.append('archivo', archivo);
    formData.append('nombre', alumno.nombre);
    formData.append('apellido', alumno.apellido);
    formData.append('email', alumno.email);
    return this.http.post<Alumno>(url, formData);
  }

  public editarConFoto(alumno: Alumno, archivo: File): Observable<Alumno>{
    const url = `${this.baseURL}/editar-con-foto/${alumno.id}`;
    const formData = new FormData();
    formData.append('archivo', archivo);
    formData.append('nombre', alumno.nombre);
    formData.append('apellido', alumno.apellido);
    formData.append('email', alumno.email);
    return this.http.put<Alumno>(url, formData);
  }

  public filtrarPorNombre(nombre: string): Observable<Alumno[]> {
    const url = `${this.baseURL}/filtrar/${nombre}`;
    return this.http.get<Alumno[]>(url);
  }

  /*private header: HttpHeaders = new HttpHeaders({'Content-Type': 'application/json'});

  constructor(private http: HttpClient) { }

  public listar(): Observable<Alumno[]> {
    return this.http.get<Alumno[]>(this.baseURL)
  }

  public listarPaginas(page: string, size: string): Observable<any> {
    const url = `${this.baseURL}/pagina`;
    const params = new HttpParams()
                          .set('page', page)
                          .set('size', size);
    return this.http.get<any[]>(url, {params: params})
  }

  public ver(id: number): Observable<Alumno> {
    const url = `${this.baseURL}/${id}`;
    return this.http.get<Alumno>(url);
  }

  public crear(alumno: Alumno): Observable<Alumno> {
    const url = `${this.baseURL}`;
    return this.http.post<Alumno>(url, alumno, {headers: this.header});
  }

  public editar(alumno: Alumno): Observable<Alumno> {
    const url = `${this.baseURL}/${alumno.id}`;
    return this.http.put<Alumno>(url, alumno, {headers: this.header});
  }

  public eliminar(id: number): Observable<void> {
    const url = `${this.baseURL}/${id}`;
    return this.http.delete<void>(url);
  }*/
}
