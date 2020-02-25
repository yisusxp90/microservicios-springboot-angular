package com.microservicios.yisusxp.cursos.controller;

import com.microservicios.yisusxp.commons.controller.GenericController;
import com.microservicios.yisusxp.commons.model.Alumno;
import com.microservicios.yisusxp.commons.model.Examen;
import com.microservicios.yisusxp.cursos.models.Curso;
import com.microservicios.yisusxp.cursos.models.CursoAlumno;
import com.microservicios.yisusxp.cursos.service.ICursoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @GetMapping
    @Override
    public ResponseEntity<?> listar() {

        List<Curso> cursos = ((List<Curso>) service.findAll())
                .stream().map(curso -> {
                    curso.getCursoAlumnos().forEach(cursoAlumno -> {
                        Alumno alumno = new Alumno();
                        alumno.setId(cursoAlumno.getAlumnoId());
                        curso.addAlumno(alumno);
                    });
                    return curso;
                }).collect(Collectors.toList());

        return ResponseEntity.ok().body(cursos);
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<?> ver(@PathVariable Long id) {
        Optional<Curso> o = service.findById(id);
        boolean exist = o.isPresent();
        if (!exist) {
            return ResponseEntity.notFound().build();
        }
        Curso curso = o.get();
        if(!curso.getCursoAlumnos().isEmpty()){
            List<Long> ids = curso.getCursoAlumnos().stream().map(CursoAlumno::getAlumnoId).collect(Collectors.toList());
            List<Alumno> alumnos = (List<Alumno>) service.obtenerAlumnosPorCurso(ids);
            curso.setAlumnos(alumnos);
        }

        return ResponseEntity.ok().body(curso);
    }

    @Override
    @GetMapping("/pagina")
    public ResponseEntity<?> listar(Pageable pageable) {
        Page<Curso> cursos =  service.findAll(pageable).map(curso -> {
            curso.getCursoAlumnos().forEach(cursoAlumno -> {
                Alumno alumno = new Alumno();
                alumno.setId(cursoAlumno.getAlumnoId());
                curso.addAlumno(alumno);
            });
            return curso;
        });
        return ResponseEntity.ok().body(cursos);
    }

    @DeleteMapping("eliminar-alumno/{id}")
    public ResponseEntity<?> eliminarCursoAlumnoPorId(@PathVariable Long id) {
        service.eliminarCursoAlumnoPorId(id);
        return ResponseEntity.noContent().build();
    }

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
        alumnos.forEach(alumno -> {
            CursoAlumno cursoAlumno = new CursoAlumno();
            cursoAlumno.setAlumnoId(alumno.getId());
            cursoAlumno.setCurso(cursoDB);
            cursoDB.addCursoAlumno(cursoAlumno);
        });
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
        CursoAlumno cursoAlumno = new CursoAlumno();
        cursoAlumno.setAlumnoId(alumno.getId());
        cursoDB.removeCursoAlumnos(cursoAlumno);
        return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(cursoDB));
    }

    @GetMapping("/alumno/{id}")
    public ResponseEntity<?> buscarCursoPorAlumnoId(@PathVariable Long id) {
        Curso curso = service.findCursoByAlumnoId(id);
        if(curso == null){
            return ResponseEntity.notFound().build();
        }else{
            List<Long> examenesIds = (List<Long>) service.obtenerExamenesIdsConRespuestasAlumno(id);
            if(examenesIds != null && examenesIds.size() > 0) {
                List<Examen> examenes = curso.getExamenes().stream().map(examen -> {
                    if (examenesIds.contains(examen.getId())) {
                        examen.setRespondido(true);
                    }
                    return examen;
                }).collect(Collectors.toList());
                curso.setExamenes(examenes);
            }
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
