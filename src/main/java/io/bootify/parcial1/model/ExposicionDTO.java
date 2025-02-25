package io.bootify.parcial1.model;

import jakarta.validation.constraints.Size;


public class ExposicionDTO {

    private Long id;

    @Size(max = 255)
    private String titulo;

    @Size(max = 255)
    private String descripcion;

    @Size(max = 255)
    private String fechaInauguracion;

    @Size(max = 255)
    private String fechaClausura;

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

}
