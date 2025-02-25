package io.bootify.parcial1.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import java.util.Set;


@Entity
public class Obra {

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
    private String estilo;

    @Column
    private Double precioSalida;

    @Column
    private String imagenUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artista_id")
    private Usuario artista;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "propietario_id")
    private Usuario propietario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exposicion_id")
    private Exposicion exposicion;

    @OneToMany(mappedBy = "obra")
    private Set<Oferta> ofertas;

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

    public Usuario getArtista() {
        return artista;
    }

    public void setArtista(final Usuario artista) {
        this.artista = artista;
    }

    public Usuario getPropietario() {
        return propietario;
    }

    public void setPropietario(final Usuario propietario) {
        this.propietario = propietario;
    }

    public Exposicion getExposicion() {
        return exposicion;
    }

    public void setExposicion(final Exposicion exposicion) {
        this.exposicion = exposicion;
    }

    public Set<Oferta> getOfertas() {
        return ofertas;
    }

    public void setOfertas(final Set<Oferta> ofertas) {
        this.ofertas = ofertas;
    }

}
