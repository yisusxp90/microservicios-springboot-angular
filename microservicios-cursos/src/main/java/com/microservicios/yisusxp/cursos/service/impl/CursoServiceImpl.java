package com.microservicios.yisusxp.cursos.service.impl;

import com.microservicios.yisusxp.commons.model.Alumno;
import com.microservicios.yisusxp.commons.service.impl.GenericServiceImpl;
import com.microservicios.yisusxp.cursos.clients.IAlumnosFeignClient;
import com.microservicios.yisusxp.cursos.clients.IRespuestaFeignClient;
import com.microservicios.yisusxp.cursos.models.Curso;
import com.microservicios.yisusxp.cursos.repository.ICursoRepository;
import com.microservicios.yisusxp.cursos.service.ICursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CursoServiceImpl extends GenericServiceImpl<Curso, ICursoRepository> implements ICursoService {

    @Autowired
    private IRespuestaFeignClient iRespuestaFeignClient;

    @Autowired
    private IAlumnosFeignClient iAlumnosFeignClient;

    @Override
    @Transactional(readOnly = true)
    public Curso findCursoByAlumnoId(Long id) {
        return repository.findCursoByAlumnoId(id);
    }

    @Override
    public Iterable<Long> obtenerExamenesIdsConRespuestasAlumno(Long alumnoId) {
        return iRespuestaFeignClient.obtenerExamenesIdsConRespuestasAlumno(alumnoId);
    }

    @Override
    public Iterable<Alumno> obtenerAlumnosPorCurso(Iterable<Long> ids) {
        return iAlumnosFeignClient.obtenerAlumnosPorCurso(ids);
    }

    @Override
    @Transactional
    public void eliminarCursoAlumnoPorId(Long id) {
        repository.eliminarCursoAlumnoPorId(id);
    }
}
