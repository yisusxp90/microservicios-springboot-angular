package com.microservicios.yisusxp.respuestas.repository;

import com.microservicios.yisusxp.respuestas.model.Respuesta;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRespuestaRepository extends CrudRepository<Respuesta, Long> {

    // fecth en una sola consulta traiga todos los objetos relacionados, respuesta junto con alumno, pregunta y examen y no hacer consultas independientes
    @Query("Select r from Respuesta r join fetch r.alumno a join fetch r.pregunta p join fetch p.examen e where a.id=?1 and e.id=?2")
    Iterable<Respuesta> findRespuestaByAlumnoByExamen(Long alumnoId, Long examenID);

    // el fecth no va por q solo necesitamos ids del examen no el objeto completo y los relacionados
    @Query("Select e.id from Respuesta r join r.alumno a join r.pregunta p join p.examen e where a.id=?1 group by e.id")
    Iterable<Long> findExamenesIdsConRespuestasByAlumno(Long alumnoId);
}
