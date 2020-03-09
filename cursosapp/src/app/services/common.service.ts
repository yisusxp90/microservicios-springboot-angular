import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {Observable} from "rxjs";
import {Generic} from "../models/generic";

export abstract class CommonService<E extends Generic> {

  protected baseURL: string;
  protected header: HttpHeaders = new HttpHeaders({'Content-Type': 'application/json'});

  constructor(protected http: HttpClient) { }

  public listar(): Observable<E[]> {
    return this.http.get<E[]>(this.baseURL)
  }

  public listarPaginas(page: string, size: string): Observable<any> {
    const url = `${this.baseURL}/pagina`;
    const params = new HttpParams()
                          .set('page', page)
                          .set('size', size);
    return this.http.get<any[]>(url, {params: params})
  }

  public ver(id: number): Observable<E> {
    const url = `${this.baseURL}/${id}`;
    return this.http.get<E>(url);
  }

  public crear(e: E): Observable<E> {
    const url = `${this.baseURL}`;
    return this.http.post<E>(url, e, {headers: this.header});
  }

  public editar(e: E): Observable<E> {
    const url = `${this.baseURL}/${e.id}`;
    return this.http.put<E>(url, e, {headers: this.header});
  }

  public eliminar(id: number): Observable<void> {
    const url = `${this.baseURL}/${id}`;
    return this.http.delete<void>(url);
  }
}
