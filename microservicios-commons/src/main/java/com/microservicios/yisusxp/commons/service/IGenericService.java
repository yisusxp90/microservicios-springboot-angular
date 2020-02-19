package com.microservicios.yisusxp.commons.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IGenericService<E> {

    Iterable<E> findAll();

    Page<E> findAll(Pageable pageable);

    Optional<E> findById(Long id);

    E save(E entity);

    void deleteById(Long id);
}
