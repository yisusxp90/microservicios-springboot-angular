package com.microservicios.yisusxp.commons.controller;

import com.microservicios.yisusxp.commons.service.IGenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

// E es el objeto o entity y S es el service
public class GenericController<E, S extends IGenericService<E>> {

    @Autowired
    protected S service;

    @GetMapping
    public ResponseEntity<?> listar() {
        return ResponseEntity.ok().body(service.findAll());
    }

    @GetMapping("/pagina")
    public ResponseEntity<?> listar(Pageable pageable) {
        return ResponseEntity.ok().body(service.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> ver(@PathVariable Long id) {
        Optional<E> optionalEntity = service.findById(id);
        boolean exist = optionalEntity.isPresent();
        if (!exist) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(optionalEntity.get());
    }

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody E entity, BindingResult result) {
        if(result.hasErrors()) {
            return this.validar(result);
        }
        E entityDB = service.save(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(entityDB);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    protected ResponseEntity<?> validar(BindingResult result) {
        Map<String, Object> errores = new HashMap<>();
        result.getFieldErrors().forEach(error -> {
            errores.put(error.getField(), "El campo " + error.getField() + " " + error.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errores);
    }

}
