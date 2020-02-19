package com.microservicios.yisusxp.respuestas.service.impl;

import com.microservicios.yisusxp.respuestas.model.Respuesta;
import com.microservicios.yisusxp.respuestas.repository.IRespuestaRepository;
import com.microservicios.yisusxp.respuestas.service.IRespuestaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RespuestaServiceImpl implements IRespuestaService {

    @Autowired
    private IRespuestaRepository iRespuestaRepository;

    @Override
    @Transactional
    public Iterable<Respuesta> saveAll(Iterable<Respuesta> respuestas) {
        return iRespuestaRepository.saveAll(respuestas);
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<Respuesta> findRespuestaByAlumnoByExamen(Long alumnoId, Long examenID) {
        return iRespuestaRepository.findRespuestaByAlumnoByExamen(alumnoId, examenID);
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<Long> findExamenesIdsConRespuestasByAlumno(Long alumnoId) {
        return iRespuestaRepository.findExamenesIdsConRespuestasByAlumno(alumnoId);
    }
}
