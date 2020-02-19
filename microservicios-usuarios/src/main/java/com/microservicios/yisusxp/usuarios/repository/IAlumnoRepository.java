package com.microservicios.yisusxp.usuarios.repository;

import com.microservicios.yisusxp.commons.model.Alumno;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IAlumnoRepository extends PagingAndSortingRepository<Alumno, Long> {

    @Query(value="select * from alumnos where nombre like %?1% or apellido like %?1%", nativeQuery = true)
    List<Alumno> findByNombreOrApellido(String termino);

}
