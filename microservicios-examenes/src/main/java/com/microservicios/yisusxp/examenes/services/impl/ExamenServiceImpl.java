package com.microservicios.yisusxp.examenes.services.impl;

import com.microservicios.yisusxp.commons.model.Asignatura;
import com.microservicios.yisusxp.commons.model.Examen;
import com.microservicios.yisusxp.commons.service.impl.GenericServiceImpl;
import com.microservicios.yisusxp.examenes.repository.IAsignaturaRepository;
import com.microservicios.yisusxp.examenes.repository.IExamenRepository;
import com.microservicios.yisusxp.examenes.services.IExamenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ExamenServiceImpl extends GenericServiceImpl<Examen, IExamenRepository> implements IExamenService  {

    @Autowired
    private IAsignaturaRepository iAsignaturaRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Examen> findExamenByNombre(String termino) {
        return repository.findExamenByNombre(termino);
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<Asignatura> findAllAsignaturas() {
        return iAsignaturaRepository.findAll();
    }

    @Override
    @Transactional
    public Asignatura saveAsignatura(Asignatura asignatura) {
        return iAsignaturaRepository.save(asignatura);
    }
}
