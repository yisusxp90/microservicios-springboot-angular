package com.microservicios.yisusxp.cursos.service;

import com.microservicios.yisusxp.commons.service.IGenericService;
import com.microservicios.yisusxp.cursos.models.Curso;

import java.util.List;

public interface ICursoService extends IGenericService<Curso> {

    Curso findCursoByAlumnoId(Long id);

    Iterable<Long> obtenerExamenesIdsConRespuestasAlumno(Long alumnoId);

}
