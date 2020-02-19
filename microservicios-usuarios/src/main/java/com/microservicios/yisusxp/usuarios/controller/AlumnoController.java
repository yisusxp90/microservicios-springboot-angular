package com.microservicios.yisusxp.usuarios.controller;

import com.microservicios.yisusxp.commons.controller.GenericController;
import com.microservicios.yisusxp.commons.model.Alumno;
import com.microservicios.yisusxp.usuarios.service.IAlumnoService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Optional;

@RestController
public class AlumnoController extends GenericController<Alumno, IAlumnoService> {

    // service esta como protected en el GenericController por tanto podemos usarlo aca
    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@Valid @RequestBody Alumno alumno, BindingResult result, @PathVariable Long id) {

        if(result.hasErrors()) {
            return this.validar(result);
        }

        Optional<Alumno> optionalAlumno = service.findById(id);
        if (!optionalAlumno.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Alumno alumnoDB = optionalAlumno.get();
        alumnoDB.setNombre(alumno.getNombre());
        alumnoDB.setApellido(alumno.getApellido());
        alumnoDB.setEmail(alumno.getEmail());
        Alumno alumnoEdit = service.save(alumnoDB);
        return ResponseEntity.status(HttpStatus.CREATED).body(alumnoEdit);
    }

    @GetMapping("/filtrar/{termino}")
    public ResponseEntity<?> filtrar(@PathVariable("termino") String termino){
        return ResponseEntity.ok(service.findByNombreOrApellido(termino));
    }

    @PostMapping("/crear-con-foto")
    public ResponseEntity<?> crearConFoto(@Valid Alumno alumno, BindingResult result, @RequestParam MultipartFile archivo) throws IOException {

        if(!archivo.isEmpty()){
            alumno.setFoto(archivo.getBytes());
        }
        return super.crear(alumno, result);
    }

    @PutMapping("/editar-con-foto/{id}")
    // se saca el @RequestBody ya que esto no es un raw body json es un multipartFormData estamos recibiendo bytes de contenido
    public ResponseEntity<?> editarConFoto(@Valid Alumno alumno, BindingResult result, @PathVariable Long id, @RequestParam MultipartFile archivo) throws IOException {

        if(result.hasErrors()) {
            return this.validar(result);
        }

        Optional<Alumno> optionalAlumno = service.findById(id);
        if (!optionalAlumno.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Alumno alumnoDB = optionalAlumno.get();
        alumnoDB.setNombre(alumno.getNombre());
        alumnoDB.setApellido(alumno.getApellido());
        alumnoDB.setEmail(alumno.getEmail());
        if(!archivo.isEmpty()){
            alumnoDB.setFoto(archivo.getBytes());
        }
        Alumno alumnoEdit = service.save(alumnoDB);
        return ResponseEntity.status(HttpStatus.CREATED).body(alumnoEdit);
    }

    @GetMapping("/uploads/img/{id}")
    public ResponseEntity<?> verFoto(@PathVariable() Long id){
        Optional<Alumno> o = service.findById(id);
        if (!o.isPresent() || o.get().getFoto() == null) {
            return ResponseEntity.notFound().build();
        }
        Resource imagen = new ByteArrayResource(o.get().getFoto());
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imagen);
    }
}
