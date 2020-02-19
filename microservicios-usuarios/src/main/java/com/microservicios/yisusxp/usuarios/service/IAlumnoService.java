package com.microservicios.yisusxp.usuarios.service;

import com.microservicios.yisusxp.commons.model.Alumno;
import com.microservicios.yisusxp.commons.service.IGenericService;

import java.util.List;

public interface IAlumnoService  extends IGenericService<Alumno> {
    List<Alumno> findByNombreOrApellido(String termino);
}
