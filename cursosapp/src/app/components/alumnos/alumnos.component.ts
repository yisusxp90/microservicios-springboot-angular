import {Component, OnInit} from '@angular/core';
import {AlumnoService} from "../../services/alumno.service";
import {Alumno} from "../../models/alumno";
import {CommonListarComponent} from "../common-listar.component";
import {BASE_ENDPOINT} from "../../config/app";

@Component({
  selector: 'app-alumnos',
  templateUrl: './alumnos.component.html',
  styleUrls: ['./alumnos.component.css']
})
export class AlumnosComponent extends CommonListarComponent<Alumno, AlumnoService> implements OnInit {

  public baseURL = BASE_ENDPOINT + '/alumnos';
  constructor(alumnoService: AlumnoService) {
    super(alumnoService);
    this.titulo = 'Listado de Alumnos';
    this.nombreModel = Alumno.name;
  }

}
