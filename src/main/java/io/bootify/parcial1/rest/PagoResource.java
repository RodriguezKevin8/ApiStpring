package io.bootify.parcial1.rest;

import io.bootify.parcial1.model.PagoDTO;
import io.bootify.parcial1.service.PagoService;
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
@RequestMapping(value = "/api/pagos", produces = MediaType.APPLICATION_JSON_VALUE)
public class PagoResource {

    private final PagoService pagoService;

    public PagoResource(final PagoService pagoService) {
        this.pagoService = pagoService;
    }

    @GetMapping
    public ResponseEntity<List<PagoDTO>> getAllPagos() {
        return ResponseEntity.ok(pagoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PagoDTO> getPago(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(pagoService.get(id));
    }

    @PostMapping
    public ResponseEntity<Long> createPago(@RequestBody @Valid final PagoDTO pagoDTO) {
        final Long createdId = pagoService.create(pagoDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updatePago(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final PagoDTO pagoDTO) {
        pagoService.update(id, pagoDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePago(@PathVariable(name = "id") final Long id) {
        pagoService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
