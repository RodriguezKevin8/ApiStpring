package io.bootify.parcial1.service;

import io.bootify.parcial1.domain.Obra;
import io.bootify.parcial1.domain.Oferta;
import io.bootify.parcial1.domain.Pago;
import io.bootify.parcial1.domain.Usuario;
import io.bootify.parcial1.model.UsuarioDTO;
import io.bootify.parcial1.repos.ObraRepository;
import io.bootify.parcial1.model.LoginRequestDTO;
import io.bootify.parcial1.model.LoginResponseDTO;
import io.bootify.parcial1.repos.OfertaRepository;
import io.bootify.parcial1.repos.PagoRepository;
import io.bootify.parcial1.repos.UsuarioRepository;
import io.bootify.parcial1.util.NotFoundException;
import io.bootify.parcial1.util.ReferencedWarning;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final ObraRepository obraRepository;
    private final OfertaRepository ofertaRepository;
    private final PagoRepository pagoRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UsuarioService(final UsuarioRepository usuarioRepository,
            final ObraRepository obraRepository, final OfertaRepository ofertaRepository,
            final PagoRepository pagoRepository) {
        this.usuarioRepository = usuarioRepository;
        this.obraRepository = obraRepository;
        this.ofertaRepository = ofertaRepository;
        this.pagoRepository = pagoRepository;
    }

    public List<UsuarioDTO> findAll() {
        final List<Usuario> usuarios = usuarioRepository.findAll(Sort.by("id"));
        return usuarios.stream()
                .map(usuario -> mapToDTO(usuario, new UsuarioDTO()))
                .toList();
    }

    public UsuarioDTO get(final Long id) {
        return usuarioRepository.findById(id)
                .map(usuario -> mapToDTO(usuario, new UsuarioDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final UsuarioDTO usuarioDTO) {
        final Usuario usuario = new Usuario();
        mapToEntity(usuarioDTO, usuario);
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        return usuarioRepository.save(usuario).getId();
    }
    
    public LoginResponseDTO login(LoginRequestDTO loginRequest) {
        Usuario usuario = usuarioRepository.findByEmailIgnoreCase(loginRequest.getEmail())
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));
    
        if (!passwordEncoder.matches(loginRequest.getPassword(), usuario.getPassword())) {
            throw new IllegalArgumentException("Credenciales incorrectas");
        }
    
        return new LoginResponseDTO(usuario.getId(), usuario.getNombre(), usuario.getEmail());
    }

    public void update(final Long id, final UsuarioDTO usuarioDTO) {
        final Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(usuarioDTO, usuario);
        usuarioRepository.save(usuario);
    }

    public void delete(final Long id) {
        usuarioRepository.deleteById(id);
    }

    private UsuarioDTO mapToDTO(final Usuario usuario, final UsuarioDTO usuarioDTO) {
        usuarioDTO.setId(usuario.getId());
        usuarioDTO.setNombre(usuario.getNombre());
        usuarioDTO.setEmail(usuario.getEmail());
        usuarioDTO.setPassword(usuario.getPassword());
        return usuarioDTO;
    }

    private Usuario mapToEntity(final UsuarioDTO usuarioDTO, final Usuario usuario) {
        usuario.setNombre(usuarioDTO.getNombre());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setPassword(usuarioDTO.getPassword());
        return usuario;
    }

    public boolean emailExists(final String email) {
        return usuarioRepository.existsByEmailIgnoreCase(email);
    }

    //hola a todos los que estan viendo esto

    public ReferencedWarning getReferencedWarning(final Long id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Obra artistaObra = obraRepository.findFirstByArtista(usuario);
        if (artistaObra != null) {
            referencedWarning.setKey("usuario.obra.artista.referenced");
            referencedWarning.addParam(artistaObra.getId());
            return referencedWarning;
        }
        final Obra propietarioObra = obraRepository.findFirstByPropietario(usuario);
        if (propietarioObra != null) {
            referencedWarning.setKey("usuario.obra.propietario.referenced");
            referencedWarning.addParam(propietarioObra.getId());
            return referencedWarning;
        }
        final Oferta usuarioOferta = ofertaRepository.findFirstByUsuario(usuario);
        if (usuarioOferta != null) {
            referencedWarning.setKey("usuario.oferta.usuario.referenced");
            referencedWarning.addParam(usuarioOferta.getId());
            return referencedWarning;
        }
        final Pago usuarioPago = pagoRepository.findFirstByUsuario(usuario);
        if (usuarioPago != null) {
            referencedWarning.setKey("usuario.pago.usuario.referenced");
            referencedWarning.addParam(usuarioPago.getId());
            return referencedWarning;
        }
        return null;
    }

}