package io.bootify.parcial1.service;

import io.bootify.parcial1.domain.Obra;
import io.bootify.parcial1.domain.Oferta;
import io.bootify.parcial1.domain.Usuario;
import io.bootify.parcial1.model.OfertaDTO;
import io.bootify.parcial1.repos.ObraRepository;
import io.bootify.parcial1.repos.OfertaRepository;
import io.bootify.parcial1.repos.UsuarioRepository;
import io.bootify.parcial1.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class OfertaService {

    private final OfertaRepository ofertaRepository;
    private final ObraRepository obraRepository;
    private final UsuarioRepository usuarioRepository;

    public OfertaService(final OfertaRepository ofertaRepository,
            final ObraRepository obraRepository, final UsuarioRepository usuarioRepository) {
        this.ofertaRepository = ofertaRepository;
        this.obraRepository = obraRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<OfertaDTO> findAll() {
        final List<Oferta> ofertas = ofertaRepository.findAll(Sort.by("id"));
        return ofertas.stream()
                .map(oferta -> mapToDTO(oferta, new OfertaDTO()))
                .toList();
    }

    public OfertaDTO get(final Long id) {
        return ofertaRepository.findById(id)
                .map(oferta -> mapToDTO(oferta, new OfertaDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final OfertaDTO ofertaDTO) {
        final Oferta oferta = new Oferta();
        mapToEntity(ofertaDTO, oferta);
        return ofertaRepository.save(oferta).getId();
    }

    public void update(final Long id, final OfertaDTO ofertaDTO) {
        final Oferta oferta = ofertaRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(ofertaDTO, oferta);
        ofertaRepository.save(oferta);
    }

    public void delete(final Long id) {
        ofertaRepository.deleteById(id);
    }

    private OfertaDTO mapToDTO(final Oferta oferta, final OfertaDTO ofertaDTO) {
        ofertaDTO.setId(oferta.getId());
        ofertaDTO.setMonto(oferta.getMonto());
        ofertaDTO.setFechaOferta(oferta.getFechaOferta());
        ofertaDTO.setObra(oferta.getObra() == null ? null : oferta.getObra().getId());
        ofertaDTO.setUsuario(oferta.getUsuario() == null ? null : oferta.getUsuario().getId());
        return ofertaDTO;
    }

    private Oferta mapToEntity(final OfertaDTO ofertaDTO, final Oferta oferta) {
        oferta.setMonto(ofertaDTO.getMonto());
        oferta.setFechaOferta(ofertaDTO.getFechaOferta());
        final Obra obra = ofertaDTO.getObra() == null ? null : obraRepository.findById(ofertaDTO.getObra())
                .orElseThrow(() -> new NotFoundException("obra not found"));
        oferta.setObra(obra);
        final Usuario usuario = ofertaDTO.getUsuario() == null ? null : usuarioRepository.findById(ofertaDTO.getUsuario())
                .orElseThrow(() -> new NotFoundException("usuario not found"));
        oferta.setUsuario(usuario);
        return oferta;
    }

}
