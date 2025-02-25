package io.bootify.parcial1.repos;

import io.bootify.parcial1.domain.Pago;
import io.bootify.parcial1.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PagoRepository extends JpaRepository<Pago, Long> {

    Pago findFirstByUsuario(Usuario usuario);

}
