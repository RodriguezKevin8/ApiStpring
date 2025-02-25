package io.bootify.parcial1.repos;

import io.bootify.parcial1.domain.Exposicion;
import io.bootify.parcial1.domain.Obra;
import io.bootify.parcial1.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ObraRepository extends JpaRepository<Obra, Long> {

    Obra findFirstByArtista(Usuario usuario);

    Obra findFirstByPropietario(Usuario usuario);

    Obra findFirstByExposicion(Exposicion exposicion);

}
