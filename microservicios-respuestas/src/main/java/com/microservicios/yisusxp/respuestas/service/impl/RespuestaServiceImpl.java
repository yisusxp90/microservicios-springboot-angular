package com.microservicios.yisusxp.respuestas.service.impl;

// import com.microservicios.yisusxp.commons.model.Examen;
// import com.microservicios.yisusxp.commons.model.Pregunta;
import com.microservicios.yisusxp.respuestas.client.IExamenFeignClient;
import com.microservicios.yisusxp.respuestas.model.Respuesta;
import com.microservicios.yisusxp.respuestas.repository.IRespuestaRepository;
import com.microservicios.yisusxp.respuestas.service.IRespuestaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RespuestaServiceImpl implements IRespuestaService {

    @Autowired
    private IRespuestaRepository iRespuestaRepository;
    @Autowired
    private IExamenFeignClient iExamenFeignClient;

    @Override
    public Iterable<Respuesta> saveAll(Iterable<Respuesta> respuestas) {
        return iRespuestaRepository.saveAll(respuestas);
    }

    @Override
    public Iterable<Respuesta> findRespuestaByAlumnoByExamen(Long alumnoId, Long examenID) {
        /*Examen examen = iExamenFeignClient.obtenerExamenPorId(examenID);
        List<Pregunta> preguntas = examen.getPreguntas();
        List<Long> preguntasIds = preguntas.stream().map(pregunta -> pregunta.getId()).collect(Collectors.toList());
        List<Respuesta> respuestas = (List<Respuesta>) iRespuestaRepository.findRespuestaByAlumnoByPreguntaIds(alumnoId, preguntasIds);
        respuestas.stream().map(respuesta -> {
            preguntas.forEach(pregunta -> {
                if(pregunta.getId() == respuesta.getPreguntaId()){
                    respuesta.setPregunta(pregunta);
                }
            });
            return respuesta;
        }).collect(Collectors.toList());*/
        List<Respuesta> respuestas = (List<Respuesta>) iRespuestaRepository.findRespuestaByAlumnoByExamen(alumnoId, examenID);
        return respuestas;
    }

    @Override
    public Iterable<Long> findExamenesIdsConRespuestasByAlumno(Long alumnoId) {
        /*List<Respuesta> respuestas = (List<Respuesta>) iRespuestaRepository.findByAlumnoId(alumnoId);
        List<Long> examenIds = Collections.emptyList();
        if(respuestas.size() > 0) {
            List<Long> preguntasIds = respuestas.stream().map(respuesta -> respuesta.getPreguntaId()).collect(Collectors.toList());
            examenIds = (List<Long>) iExamenFeignClient.obtenerExamenesIdsPorPreguntasIdsRespondidas(preguntasIds);
        }*/
        List<Respuesta> respuestas = (List<Respuesta>) iRespuestaRepository.findExamenesIdsConRespuestasByAlumno(alumnoId);
        List<Long> examenIds = respuestas
                .stream()
                .map(respuesta -> respuesta.getPregunta().getExamen().getId())
                .distinct()
                .collect(Collectors.toList());
        return examenIds;
    }

    @Override
    public Iterable<Respuesta> findByAlumnoId(Long alumnoid) {
        return iRespuestaRepository.findByAlumnoId(alumnoid);
    }
}
