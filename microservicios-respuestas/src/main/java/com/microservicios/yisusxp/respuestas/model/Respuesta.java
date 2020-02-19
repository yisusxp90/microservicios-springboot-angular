package com.microservicios.yisusxp.respuestas.model;


import com.microservicios.yisusxp.commons.model.Alumno;
import com.microservicios.yisusxp.commons.model.Pregunta;

import javax.persistence.*;

@Entity
@Table(name = "respuestas")
public class Respuesta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String texto;
    @ManyToOne(fetch = FetchType.LAZY)
    private Alumno alumno;
    @OneToOne(fetch = FetchType.LAZY)
    private Pregunta pregunta;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    public Pregunta getPregunta() {
        return pregunta;
    }

    public void setPregunta(Pregunta pregunta) {
        this.pregunta = pregunta;
    }
}
