package com.microservicios.yisusxp.respuestas.repository;

import com.microservicios.yisusxp.respuestas.model.Respuesta;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IRespuestaRepository extends MongoRepository<Respuesta, String> {

    // fecth en una sola consulta traiga todos los objetos relacionados, respuesta junto con alumno, pregunta y examen y no hacer consultas independientes
    // @Query("Select r from Respuesta r join fetch r.pregunta p join fetch p.examen e where r.alumnoId=?1 and e.id=?2")
    // Iterable<Respuesta> findRespuestaByAlumnoByExamen(Long alumnoId, Long examenID);
    @Query("{'alumnoId': ?0}, 'preguntaId': {$in: ?1}")
    Iterable<Respuesta> findRespuestaByAlumnoByPreguntaIds(Long alumnoId, Iterable<Long> preguntaIds);

    // el fecth no va por q solo necesitamos ids del examen no el objeto completo y los relacionados
    // @Query("Select e.id from Respuesta r join r.pregunta p join p.examen e where r.alumnoId=?1 group by e.id")
    // Iterable<Long> findExamenesIdsConRespuestasByAlumno(Long alumnoId);
    @Query("{'alumnoId': ?0}")
    Iterable<Respuesta> findByAlumnoId(Long alumnoid);

    @Query("{'alumnoId': ?0, 'pregunta.examen.id': ?1}")
    Iterable<Respuesta> findRespuestaByAlumnoByExamen(Long alumnoId, Long examenID);

    @Query(value = "{'alumnoId': ?0}", fields = "{'pregunta.examen.id': 1}")
    Iterable<Respuesta> findExamenesIdsConRespuestasByAlumno(Long alumnoid);
}
