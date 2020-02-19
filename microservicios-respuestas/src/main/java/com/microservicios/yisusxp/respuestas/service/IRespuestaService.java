package com.microservicios.yisusxp.respuestas.service;

import com.microservicios.yisusxp.respuestas.model.Respuesta;

public interface IRespuestaService {

    Iterable<Respuesta> saveAll(Iterable<Respuesta> respuestas);

    Iterable<Respuesta> findRespuestaByAlumnoByExamen(Long alumnoId, Long examenID);

    Iterable<Long> findExamenesIdsConRespuestasByAlumno(Long alumnoId);
}
