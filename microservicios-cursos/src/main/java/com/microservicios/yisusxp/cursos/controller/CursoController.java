package com.microservicios.yisusxp.cursos.controller;

import com.microservicios.yisusxp.commons.controller.GenericController;
import com.microservicios.yisusxp.commons.model.Alumno;
import com.microservicios.yisusxp.commons.model.Examen;
import com.microservicios.yisusxp.cursos.models.Curso;
import com.microservicios.yisusxp.cursos.service.ICursoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class CursoController extends GenericController<Curso, ICursoService> {

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@Valid @RequestBody Curso curso, BindingResult result, @PathVariable Long id) {

        if(result.hasErrors()) {
            return this.validar(result);
        }

        Optional<Curso> o = this.service.findById(id);
        boolean exist = o.isPresent();
        if(!exist) {
            return ResponseEntity.notFound().build();
        }

        Curso cursoDB = o.get();
        cursoDB.setNombre(curso.getNombre());
        return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(cursoDB));
    }

    @PutMapping("/asignar-alumnos/{id}")
    public ResponseEntity<?> asignarAlumnos(@PathVariable Long id, @RequestBody List<Alumno> alumnos){
        Optional<Curso> o = this.service.findById(id);
        boolean exist = o.isPresent();
        if(!exist) {
            return ResponseEntity.notFound().build();
        }
        Curso cursoDB = o.get();
        alumnos.forEach(cursoDB::addAlumno);
        return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(cursoDB));
    }

    @PutMapping("/eliminar-alumnos/{id}")
    public ResponseEntity<?> eliminarAlumno(@PathVariable Long id, @RequestBody Alumno alumno){
        Optional<Curso> o = this.service.findById(id);
        boolean exist = o.isPresent();
        if(!exist) {
            return ResponseEntity.notFound().build();
        }
        Curso cursoDB = o.get();
        cursoDB.removeAlumno(alumno);
        return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(cursoDB));
    }

    @GetMapping("/alumno/{id}")
    public ResponseEntity<?> buscarCursoPorAlumnoId(@PathVariable Long id) {
        Curso curso = service.findCursoByAlumnoId(id);
        if(curso == null){
            return ResponseEntity.notFound().build();
        }else{
            List<Long> examenesIds = (List<Long>) service.obtenerExamenesIdsConRespuestasAlumno(id);
            List<Examen> examenes = curso.getExamenes().stream().map(examen -> {
                if(examenesIds.contains(examen.getId())){
                    examen.setRespondido(true);
                }
                return examen;
            }).collect(Collectors.toList());
            curso.setExamenes(examenes);
        }
        return ResponseEntity.ok(curso);
    }

    @PutMapping("/asignar-examen/{id}")
    public ResponseEntity<?> asignarExamen(@PathVariable Long id, @RequestBody List<Examen> examenes){
        Optional<Curso> o = this.service.findById(id);
        boolean exist = o.isPresent();
        if (exist) {
            Curso cursoDB = o.get();
            examenes.forEach(cursoDB::addExamen);
            return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(cursoDB));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/eliminar-examen/{id}")
    public ResponseEntity<?> eliminarExamen(@PathVariable Long id, @RequestBody Examen examen){
        Optional<Curso> o = this.service.findById(id);
        boolean exist = o.isPresent();
        if(!exist) {
            return ResponseEntity.notFound().build();
        }
        Curso cursoDB = o.get();
        cursoDB.removeExamen(examen);
        return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(cursoDB));
    }


}
