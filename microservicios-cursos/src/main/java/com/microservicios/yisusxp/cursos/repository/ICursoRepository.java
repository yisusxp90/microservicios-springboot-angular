package com.microservicios.yisusxp.cursos.repository;

import com.microservicios.yisusxp.cursos.models.Curso;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICursoRepository extends PagingAndSortingRepository<Curso, Long> {

    @Query("Select c from Curso c join fetch c.cursoAlumnos ca where ca.alumnoId=?1")
    Curso findCursoByAlumnoId(Long id);

    @Modifying
    @Query("delete from CursoAlumno ca where ca.alumnoId=?1")
    void eliminarCursoAlumnoPorId(Long id);

}
