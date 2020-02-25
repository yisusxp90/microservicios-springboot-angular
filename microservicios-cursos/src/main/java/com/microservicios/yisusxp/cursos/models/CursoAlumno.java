package com.microservicios.yisusxp.cursos.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "cursos_alumnos")
public class CursoAlumno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "alumno_id", unique = true)
    private Long alumnoId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curso_id") // nombre de ka orimary key
    @JsonIgnoreProperties(value = {"cursoAlumnos"}, allowSetters = true)
    private Curso curso;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAlumnoId() {
        return alumnoId;
    }

    public void setAlumnoId(Long alumnoId) {
        this.alumnoId = alumnoId;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CursoAlumno)) return false;
        CursoAlumno that = (CursoAlumno) o;
        return Objects.equals(getAlumnoId(), that.getAlumnoId());
    }

}
