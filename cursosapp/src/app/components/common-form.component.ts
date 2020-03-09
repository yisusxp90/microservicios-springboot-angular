import { OnInit } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import Swal from 'sweetalert2';
import {CommonService} from "../services/common.service";
import {Generic} from "../models/generic";

export abstract class CommonFormComponent<E extends Generic, S extends CommonService<E>> implements OnInit {

  titulo: string;
  model: E;
  error: any;
  protected nombreModel: string;
  protected ruta: string;

  constructor(protected service: S,
              protected router: Router,
              protected route: ActivatedRoute) { }

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      const id: number = +params.get('id');
      if(id){
        this.service.ver(id).subscribe(model => this.model = model);
        this.titulo = 'Editar ' + this.nombreModel;
      }
    });
  }

  crear() {
    this.service.crear(this.model).subscribe(model => {
      Swal.fire('Registro Exitoso.', `${this.nombreModel} ${model.nombre} creado exitosamente.`, 'success');
      this.router.navigate([this.ruta]);
    }, e => {
        if(e.status === 400){
          this.error = e.error;
        }
    });
  }

  editar() {
    this.service.editar(this.model).subscribe(model => {
      Swal.fire('Modificacion Exitosa.', `${this.nombreModel} ${model.nombre} editado correctamente.`, 'success');
      this.router.navigate([this.ruta]);
    }, e => {
      if(e.status === 400){
        this.error = e.error;
      }
    });
  }

}
