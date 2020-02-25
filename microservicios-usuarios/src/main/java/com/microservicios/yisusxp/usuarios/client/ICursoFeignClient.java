package com.microservicios.yisusxp.usuarios.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Service
@FeignClient(name = "microservicios-cursos")
public interface ICursoFeignClient {

    @DeleteMapping("eliminar-alumno/{id}")
    void eliminarCursoAlumnoPorId(@PathVariable("id") Long id);
}
