package com.microservicios.yisusxp.respuestas.client;

import com.microservicios.yisusxp.commons.model.Examen;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Service
@FeignClient(name = "microservicios-examenes")
public interface IExamenFeignClient {

    @GetMapping("/{id}")
    Examen obtenerExamenPorId(@PathVariable("id") Long id);

    @GetMapping("/respondidos-por-preguntas")
    Iterable<Long> obtenerExamenesIdsPorPreguntasIdsRespondidas(@RequestParam("preguntaIds") Iterable<Long> preguntaIds);
}
