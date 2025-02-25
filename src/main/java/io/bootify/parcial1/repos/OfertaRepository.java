package io.bootify.parcial1.repos;

import io.bootify.parcial1.domain.Obra;
import io.bootify.parcial1.domain.Oferta;
import io.bootify.parcial1.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OfertaRepository extends JpaRepository<Oferta, Long> {

    Oferta findFirstByObra(Obra obra);

    Oferta findFirstByUsuario(Usuario usuario);

}
