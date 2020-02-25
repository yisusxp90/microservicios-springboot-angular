package com.microservicios.yisusxp.cursos.clients;

import com.microservicios.yisusxp.commons.model.Alumno;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Service
@FeignClient(name = "microservicios-usuarios")
public interface IAlumnosFeignClient {

    @GetMapping("/alumnos-por-curso")
    Iterable<Alumno> obtenerAlumnosPorCurso(@RequestParam("ids") Iterable<Long> ids);

}
