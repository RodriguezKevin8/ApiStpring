package io.bootify.parcial1.rest;

import io.bootify.parcial1.model.LoginRequestDTO;
import io.bootify.parcial1.model.LoginResponseDTO;
import io.bootify.parcial1.model.UsuarioDTO;
import io.bootify.parcial1.service.UsuarioService;
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
@RequestMapping(value = "/api/usuarios", produces = MediaType.APPLICATION_JSON_VALUE)
public class UsuarioResource {

    private final UsuarioService usuarioService;

    public UsuarioResource(final UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> getAllUsuarios() {
        return ResponseEntity.ok(usuarioService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> getUsuario(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(usuarioService.get(id));
    }

    @PostMapping
    public ResponseEntity<Long> createUsuario(@RequestBody @Valid final UsuarioDTO usuarioDTO) {
        final Long createdId = usuarioService.create(usuarioDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO loginRequest) {
        return ResponseEntity.ok(usuarioService.login(loginRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateUsuario(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final UsuarioDTO usuarioDTO) {
        usuarioService.update(id, usuarioDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable(name = "id") final Long id) {
        final ReferencedWarning referencedWarning = usuarioService.getReferencedWarning(id);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        usuarioService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
