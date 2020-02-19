package com.microservicios.yisusxp.cursos.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "microservicios-respuestas")
public interface IRespuestaFeignClient {

    // debe ser la misma que esta en el controller del microservicio respuesta
    @GetMapping("/alumno/examenes-ids-respondidos/{alumnoId}")
    Iterable<Long> obtenerExamenesIdsConRespuestasAlumno(@PathVariable(name = "alumnoId") Long alumnoId);

}
