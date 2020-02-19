package com.microservicios.yisusxp.usuarios.service.impl;

import com.microservicios.yisusxp.commons.model.Alumno;
import com.microservicios.yisusxp.commons.service.impl.GenericServiceImpl;
import com.microservicios.yisusxp.usuarios.repository.IAlumnoRepository;
import com.microservicios.yisusxp.usuarios.service.IAlumnoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AlumnoServiceImpl extends GenericServiceImpl<Alumno, IAlumnoRepository> implements IAlumnoService {


    @Override
    @Transactional(readOnly = true)
    public List<Alumno> findByNombreOrApellido(String termino) {
        return repository.findByNombreOrApellido(termino);
    }

    /*


    Esto pasa a estar en la clase generica.

    @Autowired
    private IAlumnoRepository iAlumnoRepository;

    @Override
    @Transactional(readOnly = true)
    public Iterable<Alumno> findAll() {
        return iAlumnoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Alumno> findById(Long id) {
        return iAlumnoRepository.findById(id);
    }

    @Override
    @Transactional
    public Alumno save(Alumno alumno) {
        return iAlumnoRepository.save(alumno);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        iAlumnoRepository.deleteById(id);
    }*/
}
