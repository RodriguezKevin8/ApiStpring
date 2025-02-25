package io.bootify.parcial1.rest;

import io.bootify.parcial1.model.ExposicionDTO;
import io.bootify.parcial1.service.ExposicionService;
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
@RequestMapping(value = "/api/exposicions", produces = MediaType.APPLICATION_JSON_VALUE)
public class ExposicionResource {

    private final ExposicionService exposicionService;

    public ExposicionResource(final ExposicionService exposicionService) {
        this.exposicionService = exposicionService;
    }

    @GetMapping
    public ResponseEntity<List<ExposicionDTO>> getAllExposicions() {
        return ResponseEntity.ok(exposicionService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExposicionDTO> getExposicion(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(exposicionService.get(id));
    }

    @PostMapping
    public ResponseEntity<Long> createExposicion(
            @RequestBody @Valid final ExposicionDTO exposicionDTO) {
        final Long createdId = exposicionService.create(exposicionDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateExposicion(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final ExposicionDTO exposicionDTO) {
        exposicionService.update(id, exposicionDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExposicion(@PathVariable(name = "id") final Long id) {
        final ReferencedWarning referencedWarning = exposicionService.getReferencedWarning(id);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        exposicionService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
