package io.bootify.parcial1.model;

import jakarta.validation.constraints.Size;


public class ObraDTO {

    private Long id;

    @Size(max = 255)
    private String titulo;

    @Size(max = 255)
    private String estilo;

    private Double precioSalida;

    @Size(max = 255)
    private String imagenUrl;

    private Long artista;

    private Long propietario;

    private Long exposicion;

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

    public String getEstilo() {
        return estilo;
    }

    public void setEstilo(final String estilo) {
        this.estilo = estilo;
    }

    public Double getPrecioSalida() {
        return precioSalida;
    }

    public void setPrecioSalida(final Double precioSalida) {
        this.precioSalida = precioSalida;
    }

    public String getImagenUrl() {
        return imagenUrl;
    }

    public void setImagenUrl(final String imagenUrl) {
        this.imagenUrl = imagenUrl;
    }

    public Long getArtista() {
        return artista;
    }

    public void setArtista(final Long artista) {
        this.artista = artista;
    }

    public Long getPropietario() {
        return propietario;
    }

    public void setPropietario(final Long propietario) {
        this.propietario = propietario;
    }

    public Long getExposicion() {
        return exposicion;
    }

    public void setExposicion(final Long exposicion) {
        this.exposicion = exposicion;
    }

}
