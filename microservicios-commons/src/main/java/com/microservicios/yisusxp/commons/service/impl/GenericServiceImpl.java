package com.microservicios.yisusxp.commons.service.impl;

import com.microservicios.yisusxp.commons.service.IGenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

// E es el objeto o entity y R es el repository que le pasaremos desde las clases que lo hereden
public class GenericServiceImpl<E, R extends PagingAndSortingRepository<E, Long>> implements IGenericService<E> {

    @Autowired
    // lo cambiamos a protected para que se pueda usar en las clases hijas que hereden
    protected R repository;

    @Override
    @Transactional(readOnly = true)
    public Iterable<E> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<E> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<E> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public E save(E entity) {
        return repository.save(entity);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
