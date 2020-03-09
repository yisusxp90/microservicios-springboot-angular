import { Component, OnInit } from '@angular/core';
import {Alumno} from "../../models/alumno";
import {AlumnoService} from "../../services/alumno.service";
import {ActivatedRoute, Router} from "@angular/router";
import {CommonFormComponent} from "../common-form.component";
import Swal from "sweetalert2";

@Component({
  selector: 'app-alumnos-form',
  templateUrl: './alumnos-form.component.html',
  styleUrls: ['./alumnos-form.component.css']
})
export class AlumnosFormComponent extends CommonFormComponent<Alumno, AlumnoService> implements OnInit {

  private fotoSeleccionada: File;
  constructor(alumnoService: AlumnoService,
              router: Router,
              route: ActivatedRoute) {
    super(alumnoService, router, route);
    this.model = new Alumno();
    this.titulo = 'Crear Alumnos';
    this.nombreModel = 'Alumno';
    this.ruta = '/alumnos';
  }

  seleccionarFoto(event): void {
    this.fotoSeleccionada = event.target.files[0];
    console.info(this.fotoSeleccionada);
    if(this.fotoSeleccionada.type.indexOf('image') < 0){
      this.fotoSeleccionada = null;
      Swal.fire('Error al seleccionar la foto', 'El archivo debe ser del tipo imagen', 'error');
    }
  }

  // sobreescribimos el metodo
  crear() {
    if(!this.fotoSeleccionada){
      // si no hay foto llamamos al crear del padre
      super.crear;
    } else{
      this.service.crearConFoto(this.model, this.fotoSeleccionada).subscribe(alumno => {
        Swal.fire('Registro Exitoso.', `${this.nombreModel} ${alumno.nombre} creado exitosamente.`, 'success');
        this.router.navigate([this.ruta]);
      }, e => {
        if(e.status === 400){
          this.error = e.error;
        }
      });
    }
  }

  editar() {
    if(!this.fotoSeleccionada){
      // si no hay foto llamamos al crear del padre
      super.editar();
    } else{
      this.service.editarConFoto(this.model, this.fotoSeleccionada).subscribe(alumno => {
        Swal.fire('Modificacion Exitosa..', `${this.nombreModel} ${alumno.nombre} editado exitosamente.`, 'success');
        this.router.navigate([this.ruta]);
      }, e => {
        if(e.status === 400){
          this.error = e.error;
        }
      });
    }
  }

}
