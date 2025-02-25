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
public class Usuario {

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

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "artista")
    private Set<Obra> obrasCreadas;

    @OneToMany(mappedBy = "propietario")
    private Set<Obra> obrasPropiedad;

    @OneToMany(mappedBy = "usuario")
    private Set<Oferta> ofertas;

    @OneToMany(mappedBy = "usuario")
    private Set<Pago> pagos;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(final String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public Set<Obra> getObrasCreadas() {
        return obrasCreadas;
    }

    public void setObrasCreadas(final Set<Obra> obrasCreadas) {
        this.obrasCreadas = obrasCreadas;
    }

    public Set<Obra> getObrasPropiedad() {
        return obrasPropiedad;
    }

    public void setObrasPropiedad(final Set<Obra> obrasPropiedad) {
        this.obrasPropiedad = obrasPropiedad;
    }

    public Set<Oferta> getOfertas() {
        return ofertas;
    }

    public void setOfertas(final Set<Oferta> ofertas) {
        this.ofertas = ofertas;
    }

    public Set<Pago> getPagos() {
        return pagos;
    }

    public void setPagos(final Set<Pago> pagos) {
        this.pagos = pagos;
    }

}
