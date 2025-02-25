package io.bootify.parcial1.service;

import io.bootify.parcial1.domain.Pago;
import io.bootify.parcial1.domain.Usuario;
import io.bootify.parcial1.model.PagoDTO;
import io.bootify.parcial1.repos.PagoRepository;
import io.bootify.parcial1.repos.UsuarioRepository;
import io.bootify.parcial1.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class PagoService {

    private final PagoRepository pagoRepository;
    private final UsuarioRepository usuarioRepository;

    public PagoService(final PagoRepository pagoRepository,
            final UsuarioRepository usuarioRepository) {
        this.pagoRepository = pagoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<PagoDTO> findAll() {
        final List<Pago> pagoes = pagoRepository.findAll(Sort.by("id"));
        return pagoes.stream()
                .map(pago -> mapToDTO(pago, new PagoDTO()))
                .toList();
    }

    public PagoDTO get(final Long id) {
        return pagoRepository.findById(id)
                .map(pago -> mapToDTO(pago, new PagoDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final PagoDTO pagoDTO) {
        final Pago pago = new Pago();
        mapToEntity(pagoDTO, pago);
        return pagoRepository.save(pago).getId();
    }

    public void update(final Long id, final PagoDTO pagoDTO) {
        final Pago pago = pagoRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(pagoDTO, pago);
        pagoRepository.save(pago);
    }

    public void delete(final Long id) {
        pagoRepository.deleteById(id);
    }

    private PagoDTO mapToDTO(final Pago pago, final PagoDTO pagoDTO) {
        pagoDTO.setId(pago.getId());
        pagoDTO.setMonto(pago.getMonto());
        pagoDTO.setFechaPago(pago.getFechaPago());
        pagoDTO.setEstado(pago.getEstado());
        pagoDTO.setUsuario(pago.getUsuario() == null ? null : pago.getUsuario().getId());
        return pagoDTO;
    }

    private Pago mapToEntity(final PagoDTO pagoDTO, final Pago pago) {
        pago.setMonto(pagoDTO.getMonto());
        pago.setFechaPago(pagoDTO.getFechaPago());
        pago.setEstado(pagoDTO.getEstado());
        final Usuario usuario = pagoDTO.getUsuario() == null ? null : usuarioRepository.findById(pagoDTO.getUsuario())
                .orElseThrow(() -> new NotFoundException("usuario not found"));
        pago.setUsuario(usuario);
        return pago;
    }

}
