package io.bootify.parcial1.model;

import jakarta.validation.constraints.Size;


public class OfertaDTO {

    private Long id;

    private Double monto;

    @Size(max = 255)
    private String fechaOferta;

    private Long obra;

    private Long usuario;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(final Double monto) {
        this.monto = monto;
    }

    public String getFechaOferta() {
        return fechaOferta;
    }

    public void setFechaOferta(final String fechaOferta) {
        this.fechaOferta = fechaOferta;
    }

    public Long getObra() {
        return obra;
    }

    public void setObra(final Long obra) {
        this.obra = obra;
    }

    public Long getUsuario() {
        return usuario;
    }

    public void setUsuario(final Long usuario) {
        this.usuario = usuario;
    }

}
