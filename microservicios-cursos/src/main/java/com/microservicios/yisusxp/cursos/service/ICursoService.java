package com.microservicios.yisusxp.cursos.service;

import com.microservicios.yisusxp.commons.model.Alumno;
import com.microservicios.yisusxp.commons.service.IGenericService;
import com.microservicios.yisusxp.cursos.models.Curso;

public interface ICursoService extends IGenericService<Curso> {

    Curso findCursoByAlumnoId(Long id);

    Iterable<Long> obtenerExamenesIdsConRespuestasAlumno(Long alumnoId);

    Iterable<Alumno> obtenerAlumnosPorCurso(Iterable<Long> ids);

    void eliminarCursoAlumnoPorId(Long id);
}
