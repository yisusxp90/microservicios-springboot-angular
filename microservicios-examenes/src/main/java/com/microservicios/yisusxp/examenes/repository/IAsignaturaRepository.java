package com.microservicios.yisusxp.examenes.repository;

import com.microservicios.yisusxp.commons.model.Asignatura;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAsignaturaRepository extends PagingAndSortingRepository<Asignatura, Long> {
}
