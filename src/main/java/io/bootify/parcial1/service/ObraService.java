package io.bootify.parcial1.service;

import io.bootify.parcial1.domain.Exposicion;
import io.bootify.parcial1.domain.Obra;
import io.bootify.parcial1.domain.Oferta;
import io.bootify.parcial1.domain.Usuario;
import io.bootify.parcial1.model.ObraDTO;
import io.bootify.parcial1.repos.ExposicionRepository;
import io.bootify.parcial1.repos.ObraRepository;
import io.bootify.parcial1.repos.OfertaRepository;
import io.bootify.parcial1.repos.UsuarioRepository;
import io.bootify.parcial1.util.NotFoundException;
import io.bootify.parcial1.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class ObraService {

    private final ObraRepository obraRepository;
    private final UsuarioRepository usuarioRepository;
    private final ExposicionRepository exposicionRepository;
    private final OfertaRepository ofertaRepository;

    public ObraService(final ObraRepository obraRepository,
            final UsuarioRepository usuarioRepository,
            final ExposicionRepository exposicionRepository,
            final OfertaRepository ofertaRepository) {
        this.obraRepository = obraRepository;
        this.usuarioRepository = usuarioRepository;
        this.exposicionRepository = exposicionRepository;
        this.ofertaRepository = ofertaRepository;
    }

    public List<ObraDTO> findAll() {
        final List<Obra> obras = obraRepository.findAll(Sort.by("id"));
        return obras.stream()
                .map(obra -> mapToDTO(obra, new ObraDTO()))
                .toList();
    }

    public ObraDTO get(final Long id) {
        return obraRepository.findById(id)
                .map(obra -> mapToDTO(obra, new ObraDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final ObraDTO obraDTO) {
        final Obra obra = new Obra();
        mapToEntity(obraDTO, obra);
        return obraRepository.save(obra).getId();
    }

    public void update(final Long id, final ObraDTO obraDTO) {
        final Obra obra = obraRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(obraDTO, obra);
        obraRepository.save(obra);
    }

    public void delete(final Long id) {
        obraRepository.deleteById(id);
    }

    private ObraDTO mapToDTO(final Obra obra, final ObraDTO obraDTO) {
        obraDTO.setId(obra.getId());
        obraDTO.setTitulo(obra.getTitulo());
        obraDTO.setEstilo(obra.getEstilo());
        obraDTO.setPrecioSalida(obra.getPrecioSalida());
        obraDTO.setImagenUrl(obra.getImagenUrl());
        obraDTO.setArtista(obra.getArtista() == null ? null : obra.getArtista().getId());
        obraDTO.setPropietario(obra.getPropietario() == null ? null : obra.getPropietario().getId());
        obraDTO.setExposicion(obra.getExposicion() == null ? null : obra.getExposicion().getId());
        return obraDTO;
    }

    private Obra mapToEntity(final ObraDTO obraDTO, final Obra obra) {
        obra.setTitulo(obraDTO.getTitulo());
        obra.setEstilo(obraDTO.getEstilo());
        obra.setPrecioSalida(obraDTO.getPrecioSalida());
        obra.setImagenUrl(obraDTO.getImagenUrl());
        final Usuario artista = obraDTO.getArtista() == null ? null : usuarioRepository.findById(obraDTO.getArtista())
                .orElseThrow(() -> new NotFoundException("artista not found"));
        obra.setArtista(artista);
        final Usuario propietario = obraDTO.getPropietario() == null ? null : usuarioRepository.findById(obraDTO.getPropietario())
                .orElseThrow(() -> new NotFoundException("propietario not found"));
        obra.setPropietario(propietario);
        final Exposicion exposicion = obraDTO.getExposicion() == null ? null : exposicionRepository.findById(obraDTO.getExposicion())
                .orElseThrow(() -> new NotFoundException("exposicion not found"));
        obra.setExposicion(exposicion);
        return obra;
    }

    public ReferencedWarning getReferencedWarning(final Long id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Obra obra = obraRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Oferta obraOferta = ofertaRepository.findFirstByObra(obra);
        if (obraOferta != null) {
            referencedWarning.setKey("obra.oferta.obra.referenced");
            referencedWarning.addParam(obraOferta.getId());
            return referencedWarning;
        }
        return null;
    }

}
