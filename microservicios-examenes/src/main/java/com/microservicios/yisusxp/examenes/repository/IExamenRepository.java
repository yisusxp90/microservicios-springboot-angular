package com.microservicios.yisusxp.examenes.repository;

import com.microservicios.yisusxp.commons.model.Examen;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IExamenRepository extends PagingAndSortingRepository<Examen, Long> {

    @Query(value="select * from examenes where nombre like %?1%", nativeQuery = true)
    List<Examen> findExamenByNombre(String termino);

}
