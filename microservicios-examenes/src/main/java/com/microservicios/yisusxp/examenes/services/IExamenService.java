package com.microservicios.yisusxp.examenes.services;

import com.microservicios.yisusxp.commons.model.Asignatura;
import com.microservicios.yisusxp.commons.model.Examen;
import com.microservicios.yisusxp.commons.service.IGenericService;

import java.util.List;

public interface IExamenService extends IGenericService<Examen> {

    List<Examen> findExamenByNombre(String termino);

    Iterable<Asignatura> findAllAsignaturas();

    Asignatura saveAsignatura(Asignatura asignatura);

    Iterable<Long> findExamenesIdsConRespuestasByPreguntIds(Iterable<Long> preguntaIds);
}
