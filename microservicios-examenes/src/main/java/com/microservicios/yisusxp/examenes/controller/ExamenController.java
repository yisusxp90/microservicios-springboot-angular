package com.microservicios.yisusxp.examenes.controller;

import com.microservicios.yisusxp.commons.controller.GenericController;
import com.microservicios.yisusxp.commons.model.Asignatura;
import com.microservicios.yisusxp.commons.model.Examen;
import com.microservicios.yisusxp.commons.model.Pregunta;
import com.microservicios.yisusxp.examenes.services.IExamenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class ExamenController extends GenericController<Examen, IExamenService> {

    @GetMapping("/respondidos-por-preguntas")
    public ResponseEntity<?> obtenerExamenesIdsPorPreguntasIdsRespondidas(@RequestParam List<Long> preguntaIds){
        preguntaIds.forEach(ids -> {
            System.out.println(ids);
        });
        return ResponseEntity.ok().body(this.service.findExamenesIdsConRespuestasByPreguntIds(preguntaIds));
    }

    @PutMapping("/{id}")
    // el BindingResult result debe ir despues del entity siempre
    public ResponseEntity<?> editar(@Valid @RequestBody Examen examen, BindingResult result, @PathVariable Long id) {

        if(result.hasErrors()) {
            return this.validar(result);
        }

        Optional<Examen> o = this.service.findById(id);
        boolean exist = o.isPresent();
        if(!exist) {
            return ResponseEntity.notFound().build();
        }

        Examen examenDB = o.get();
        examenDB.setNombre(examen.getNombre());
        examenDB.setAsignaturaHija(examen.getAsignaturaHija());
        examenDB.setAsignaturaPadre(examen.getAsignaturaPadre());
        List<Pregunta> eliminadas = examenDB.getPreguntas()
                .stream()
                .filter(pregunta -> !examen.getPreguntas().contains(pregunta))
                .collect(Collectors.toList());

        eliminadas.forEach(examenDB::removePregunta);

        /*List<Pregunta> eliminadas = new ArrayList<>();
        examenDB.getPreguntas().forEach(pregunta -> {
            if(!examen.getPreguntas().contains(pregunta)){
                eliminadas.add(pregunta);
            }
        });
        eliminadas.forEach(examenDB::removePregunta);

        examenDB.setPreguntas(examen.getPreguntas());*/

        examenDB.setPreguntas(examen.getPreguntas());

        return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(examenDB));
    }

    @GetMapping("/filtrar/{termino}")
    public ResponseEntity<?> buscarExamenPorNombre(@PathVariable String termino) {
        return ResponseEntity.ok(this.service.findExamenByNombre(termino));
    }

    @GetMapping("/asignaturas")
    public ResponseEntity<?> listarAsignaturas() {
        return ResponseEntity.ok(this.service.findAllAsignaturas());
    }

    @PostMapping("/asignaturas")
    public ResponseEntity<?> crear(@RequestBody Asignatura asignatura) {
        Asignatura asignaturaDB = this.service.saveAsignatura(asignatura);
        return ResponseEntity.status(HttpStatus.CREATED).body(asignaturaDB);
    }

}
