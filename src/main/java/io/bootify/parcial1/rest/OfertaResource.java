package io.bootify.parcial1.rest;

import io.bootify.parcial1.model.OfertaDTO;
import io.bootify.parcial1.service.OfertaService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/ofertas", produces = MediaType.APPLICATION_JSON_VALUE)
public class OfertaResource {

    private final OfertaService ofertaService;

    public OfertaResource(final OfertaService ofertaService) {
        this.ofertaService = ofertaService;
    }

    @GetMapping
    public ResponseEntity<List<OfertaDTO>> getAllOfertas() {
        return ResponseEntity.ok(ofertaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OfertaDTO> getOferta(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(ofertaService.get(id));
    }

    @PostMapping
    public ResponseEntity<Long> createOferta(@RequestBody @Valid final OfertaDTO ofertaDTO) {
        final Long createdId = ofertaService.create(ofertaDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateOferta(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final OfertaDTO ofertaDTO) {
        ofertaService.update(id, ofertaDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOferta(@PathVariable(name = "id") final Long id) {
        ofertaService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
