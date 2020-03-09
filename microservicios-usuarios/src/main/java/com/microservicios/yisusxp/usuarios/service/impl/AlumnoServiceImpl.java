package com.microservicios.yisusxp.usuarios.service.impl;

import com.microservicios.yisusxp.commons.model.Alumno;
import com.microservicios.yisusxp.commons.service.impl.GenericServiceImpl;
import com.microservicios.yisusxp.usuarios.client.ICursoFeignClient;
import com.microservicios.yisusxp.usuarios.repository.IAlumnoRepository;
import com.microservicios.yisusxp.usuarios.service.IAlumnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AlumnoServiceImpl extends GenericServiceImpl<Alumno, IAlumnoRepository> implements IAlumnoService {

    @Autowired
    private ICursoFeignClient iCursoFeignClient;

    @Override
    @Transactional(readOnly = true)
    public List<Alumno> findByNombreOrApellido(String termino) {
        return repository.findByNombreOrApellido(termino.toUpperCase());
    }

    @Override
    public Iterable<Alumno> findAllById(Iterable<Long> ids) {
        return repository.findAllById(ids);
    }

    @Override
    public void eliminarCursoAlumnoPorId(Long id) {
        iCursoFeignClient.eliminarCursoAlumnoPorId(id);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        super.deleteById(id);
        this.eliminarCursoAlumnoPorId(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<Alumno> findAll() {
        return repository.findAllByOrderByIdAsc();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Alumno> findAll(Pageable pageable) {
        return repository.findAllByOrderByIdAsc(pageable);
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
