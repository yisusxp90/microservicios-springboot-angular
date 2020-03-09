package com.microservicios.yisusxp.usuarios.repository;

import com.microservicios.yisusxp.commons.model.Alumno;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IAlumnoRepository extends PagingAndSortingRepository<Alumno, Long> {

    @Query(value="select * from alumnos where upper(nombre) like %?1% or upper(apellido) like %?1%", nativeQuery = true)
    List<Alumno> findByNombreOrApellido(String termino);

    Iterable<Alumno> findAllByOrderByIdAsc();

    Page<Alumno> findAllByOrderByIdAsc(Pageable pageable);

}
