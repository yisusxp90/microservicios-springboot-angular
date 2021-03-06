package com.microservicios.yisusxp.respuestas.controller;

import com.microservicios.yisusxp.respuestas.model.Respuesta;
import com.microservicios.yisusxp.respuestas.service.IRespuestaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class RespuestaController {

    @Autowired
    private IRespuestaService iRespuestaService;

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Iterable<Respuesta> respuestas) {

        respuestas = ((List<Respuesta>)respuestas).stream().map(respuesta -> {
            respuesta.setAlumnoId(respuesta.getAlumno().getId());
            respuesta.setPreguntaId(respuesta.getPregunta().getId());
            return respuesta;
        }).collect(Collectors.toList());

        Iterable<Respuesta> respuestasDB = iRespuestaService.saveAll(respuestas);
        return ResponseEntity.status(HttpStatus.CREATED).body(respuestasDB);
    }

    @GetMapping("/alumno/{alumnoId}/{examenId}")
    public ResponseEntity<?> obtenerRespuestasPorAlumnoPorExamen(@PathVariable Long alumnoId, @PathVariable Long examenId){
        Iterable<Respuesta> respuestas = iRespuestaService.findRespuestaByAlumnoByExamen(alumnoId, examenId);
        return ResponseEntity.ok(respuestas);
    }

    @GetMapping("/alumno/examenes-ids-respondidos/{alumnoId}")
    public ResponseEntity<?> obtenerExamenesIdsConRespuestasAlumno(@PathVariable Long alumnoId){
        Iterable<Long> exanenesIds = iRespuestaService.findExamenesIdsConRespuestasByAlumno(alumnoId);
        return ResponseEntity.ok(exanenesIds);
    }
}
