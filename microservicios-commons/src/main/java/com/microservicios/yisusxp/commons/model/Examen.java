package com.microservicios.yisusxp.commons.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "examenes")
public class Examen implements Serializable {

    private static final long serialVersionUID = -5132436110454490932L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotEmpty
    private String nombre;
    @Column(name = "create_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createAt;
    // cascade ALL y orphanRemoval para q cuando se elimine el examen se eliminen sus preguntas
    @JsonIgnoreProperties(value = {"examen"}, allowSetters = true)
    @OneToMany(mappedBy = "examen",fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pregunta> preguntas;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    private Asignatura asignaturaPadre;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    private Asignatura asignaturaHija;
    @Transient
    private boolean respondido;

    public Examen() {
        this.preguntas = new ArrayList<>();
    }

    @PrePersist
    public void prePersist() {
        this.createAt = new Date();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public List<Pregunta> getPreguntas() {
        return preguntas;
    }

    public void setPreguntas(List<Pregunta> preguntas) {
        this.preguntas.clear();
        preguntas.forEach(this::addPregunta);
    }

    public void addPregunta(Pregunta pregunta) {
        this.preguntas.add(pregunta);
        pregunta.setExamen(this);
    }

    public void removePregunta(Pregunta pregunta) {
        this.preguntas.remove(pregunta);
        pregunta.setExamen(null);
    }

    public boolean isRespondido() {
        return respondido;
    }

    public void setRespondido(boolean respondido) {
        this.respondido = respondido;
    }

    public Asignatura getAsignaturaPadre() {
        return asignaturaPadre;
    }

    public void setAsignaturaPadre(Asignatura asignaturaPadre) {
        this.asignaturaPadre = asignaturaPadre;
    }

    public Asignatura getAsignaturaHija() {
        return asignaturaHija;
    }

    public void setAsignaturaHija(Asignatura asignaturaHija) {
        this.asignaturaHija = asignaturaHija;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Examen)) return false;
        Examen examen = (Examen) o;
        return getId() == examen.getId();
    }
}
