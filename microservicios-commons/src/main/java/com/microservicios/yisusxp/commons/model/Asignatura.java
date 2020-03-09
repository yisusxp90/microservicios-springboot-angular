package com.microservicios.yisusxp.commons.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "asignaturas")
public class Asignatura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nombre;

    @JsonIgnoreProperties(value = {"hijos", "handler", "hibernateLazyInitializer"}, allowSetters = true)
    @ManyToOne(fetch = FetchType.LAZY)
    private Asignatura padre;

    @JsonIgnoreProperties(value = {"padre", "handler", "hibernateLazyInitializer"}, allowSetters = true)
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "padre", cascade = CascadeType.ALL)
    private List<Asignatura> hijos;

    public Asignatura() {
        this.hijos = new ArrayList<>();
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

    public Asignatura getPadre() {
        return padre;
    }

    public void setPadre(Asignatura padre) {
        this.padre = padre;
    }

    public List<Asignatura> getHijos() {
        return hijos;
    }

    public void setHijos(List<Asignatura> hijos) {
        this.hijos = hijos;
    }
}
