package io.bootify.parcial1.service;

import io.bootify.parcial1.domain.Exposicion;
import io.bootify.parcial1.domain.Obra;
import io.bootify.parcial1.model.ExposicionDTO;
import io.bootify.parcial1.repos.ExposicionRepository;
import io.bootify.parcial1.repos.ObraRepository;
import io.bootify.parcial1.util.NotFoundException;
import io.bootify.parcial1.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class ExposicionService {

    private final ExposicionRepository exposicionRepository;
    private final ObraRepository obraRepository;

    public ExposicionService(final ExposicionRepository exposicionRepository,
            final ObraRepository obraRepository) {
        this.exposicionRepository = exposicionRepository;
        this.obraRepository = obraRepository;
    }

    public List<ExposicionDTO> findAll() {
        final List<Exposicion> exposicions = exposicionRepository.findAll(Sort.by("id"));
        return exposicions.stream()
                .map(exposicion -> mapToDTO(exposicion, new ExposicionDTO()))
                .toList();
    }

    public ExposicionDTO get(final Long id) {
        return exposicionRepository.findById(id)
                .map(exposicion -> mapToDTO(exposicion, new ExposicionDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final ExposicionDTO exposicionDTO) {
        final Exposicion exposicion = new Exposicion();
        mapToEntity(exposicionDTO, exposicion);
        return exposicionRepository.save(exposicion).getId();
    }

    public void update(final Long id, final ExposicionDTO exposicionDTO) {
        final Exposicion exposicion = exposicionRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(exposicionDTO, exposicion);
        exposicionRepository.save(exposicion);
    }

    public void delete(final Long id) {
        exposicionRepository.deleteById(id);
    }

    private ExposicionDTO mapToDTO(final Exposicion exposicion, final ExposicionDTO exposicionDTO) {
        exposicionDTO.setId(exposicion.getId());
        exposicionDTO.setTitulo(exposicion.getTitulo());
        exposicionDTO.setDescripcion(exposicion.getDescripcion());
        exposicionDTO.setFechaInauguracion(exposicion.getFechaInauguracion());
        exposicionDTO.setFechaClausura(exposicion.getFechaClausura());
        return exposicionDTO;
    }

    private Exposicion mapToEntity(final ExposicionDTO exposicionDTO, final Exposicion exposicion) {
        exposicion.setTitulo(exposicionDTO.getTitulo());
        exposicion.setDescripcion(exposicionDTO.getDescripcion());
        exposicion.setFechaInauguracion(exposicionDTO.getFechaInauguracion());
        exposicion.setFechaClausura(exposicionDTO.getFechaClausura());
        return exposicion;
    }

    public ReferencedWarning getReferencedWarning(final Long id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Exposicion exposicion = exposicionRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Obra exposicionObra = obraRepository.findFirstByExposicion(exposicion);
        if (exposicionObra != null) {
            referencedWarning.setKey("exposicion.obra.exposicion.referenced");
            referencedWarning.addParam(exposicionObra.getId());
            return referencedWarning;
        }
        return null;
    }

}
