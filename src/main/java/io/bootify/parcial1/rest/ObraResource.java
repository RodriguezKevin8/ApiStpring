package io.bootify.parcial1.rest;

import io.bootify.parcial1.model.ObraDTO;
import io.bootify.parcial1.service.ObraService;
import io.bootify.parcial1.util.ReferencedException;
import io.bootify.parcial1.util.ReferencedWarning;
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
@RequestMapping(value = "/api/obras", produces = MediaType.APPLICATION_JSON_VALUE)
public class ObraResource {

    private final ObraService obraService;

    public ObraResource(final ObraService obraService) {
        this.obraService = obraService;
    }

    @GetMapping
    public ResponseEntity<List<ObraDTO>> getAllObras() {
        return ResponseEntity.ok(obraService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ObraDTO> getObra(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(obraService.get(id));
    }

    @PostMapping
    public ResponseEntity<Long> createObra(@RequestBody @Valid final ObraDTO obraDTO) {
        final Long createdId = obraService.create(obraDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateObra(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final ObraDTO obraDTO) {
        obraService.update(id, obraDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteObra(@PathVariable(name = "id") final Long id) {
        final ReferencedWarning referencedWarning = obraService.getReferencedWarning(id);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        obraService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
