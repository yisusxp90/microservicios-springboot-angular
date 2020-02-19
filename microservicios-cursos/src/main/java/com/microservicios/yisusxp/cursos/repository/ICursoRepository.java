package com.microservicios.yisusxp.cursos.repository;

import com.microservicios.yisusxp.cursos.models.Curso;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICursoRepository extends PagingAndSortingRepository<Curso, Long> {

    @Query("Select c from Curso c join fetch c.alumnos a where a.id=?1")
    Curso findCursoByAlumnoId(Long id);

}
