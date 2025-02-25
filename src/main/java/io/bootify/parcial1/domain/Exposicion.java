package io.bootify.parcial1.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import java.util.Set;


@Entity
public class Exposicion {

    @Id
    @Column(nullable = false, updatable = false)
    @SequenceGenerator(
            name = "primary_sequence",
            sequenceName = "primary_sequence",
            allocationSize = 1,
            initialValue = 10000
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "primary_sequence"
    )
    private Long id;

    @Column
    private String titulo;

    @Column
    private String descripcion;

    @Column
    private String fechaInauguracion;

    @Column
    private String fechaClausura;

    @OneToMany(mappedBy = "exposicion")
    private Set<Obra> obras;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(final String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(final String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFechaInauguracion() {
        return fechaInauguracion;
    }

    public void setFechaInauguracion(final String fechaInauguracion) {
        this.fechaInauguracion = fechaInauguracion;
    }

    public String getFechaClausura() {
        return fechaClausura;
    }

    public void setFechaClausura(final String fechaClausura) {
        this.fechaClausura = fechaClausura;
    }

    public Set<Obra> getObras() {
        return obras;
    }

    public void setObras(final Set<Obra> obras) {
        this.obras = obras;
    }

}
