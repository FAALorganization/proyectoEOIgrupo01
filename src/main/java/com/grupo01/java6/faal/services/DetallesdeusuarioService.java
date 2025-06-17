// Archivo: DetallesdeusuarioService.java
package com.grupo01.java6.faal.services;

import com.grupo01.java6.faal.entities.Detallesdeusuario;
import com.grupo01.java6.faal.repositories.DetallesDeUsuarioRepository;
import com.grupo01.java6.faal.services.mappers.DetallesdeusuarioMapper;
import org.springframework.stereotype.Service;
import com.grupo01.java6.faal.dtos.NombreDTO;

import java.util.List;

@Service
public class DetallesdeusuarioService extends AbstractBusinessService<Detallesdeusuario, Integer, NombreDTO, DetallesDeUsuarioRepository, DetallesdeusuarioMapper> {

    private final DetallesDeUsuarioRepository detallesdeusuarioRepository;

    public DetallesdeusuarioService(DetallesDeUsuarioRepository repo, DetallesdeusuarioMapper mapper) {
        super(repo, mapper);
        this.detallesdeusuarioRepository = repo;
    }

    public Detallesdeusuario findByNombreAndApellidos(String nombre, String apellidos) {
        Detallesdeusuario detalles = detallesdeusuarioRepository.findByNombreAndApellidos(nombre, apellidos);
        if (detalles == null) {
            throw new IllegalArgumentException("Usuario no encontrado con nombre y apellidos: " + nombre + " " + apellidos);
        }
        return detalles;
    }

    public boolean verificarRolJefe(Integer idUsuario) {
        String rol = detallesdeusuarioRepository.findRolByUsuarioId(idUsuario);
        return "JEFE".equalsIgnoreCase(rol);
    }

    public List<Detallesdeusuario> obtenerTodosLosUsuarios() {
        return detallesdeusuarioRepository.findAll();
    }

    public List<Detallesdeusuario> obtenerUsuariosActivos() {
        return detallesdeusuarioRepository.findUsuariosActivos().stream()
                .filter(u -> !u.getNombre().equalsIgnoreCase("general"))
                .toList();
    }


    public void guardar(Detallesdeusuario detallesdeusuario) {
        detallesdeusuarioRepository.save(detallesdeusuario);
    }

    public Detallesdeusuario findByEmail(String emailPrimario) {
        return detallesdeusuarioRepository.findByEmailPrimario(emailPrimario);
    }

    public void actualizarDetalles(String email, Detallesdeusuario nuevosDetalles) {
        Detallesdeusuario detallesExistentes = detallesdeusuarioRepository.findByEmailPrimario(email);

        if (detallesExistentes != null) {
            if (nuevosDetalles.getNombre() != null && !nuevosDetalles.getNombre().isBlank()) {
                detallesExistentes.setNombre(nuevosDetalles.getNombre());
            }
            if (nuevosDetalles.getApellidos() != null && !nuevosDetalles.getApellidos().isBlank()) {
                detallesExistentes.setApellidos(nuevosDetalles.getApellidos());
            }
            if (nuevosDetalles.getTlf() != null && !nuevosDetalles.getTlf().isBlank()) {
                detallesExistentes.setTlf(nuevosDetalles.getTlf());
            }
            if (nuevosDetalles.getTlf2() != null && !nuevosDetalles.getTlf2().isBlank()) {
                detallesExistentes.setTlf2(nuevosDetalles.getTlf2());
            }
            if (nuevosDetalles.getDireccion() != null && !nuevosDetalles.getDireccion().isBlank()) {
                detallesExistentes.setDireccion(nuevosDetalles.getDireccion());
            }
            if (nuevosDetalles.getCodigoPostal() != null && nuevosDetalles.getCodigoPostal() != 0) {
                detallesExistentes.setCodigoPostal(nuevosDetalles.getCodigoPostal());
            }
            if (nuevosDetalles.getEmailPersonal() != null && !nuevosDetalles.getEmailPersonal().isBlank()) {
                detallesExistentes.setEmailPersonal(nuevosDetalles.getEmailPersonal());
            }
            if (nuevosDetalles.getContactoEmergencia() != null && !nuevosDetalles.getContactoEmergencia().isBlank()) {
                detallesExistentes.setContactoEmergencia(nuevosDetalles.getContactoEmergencia());
            }
            if (nuevosDetalles.getPais() != null && !nuevosDetalles.getPais().isBlank()) {
                detallesExistentes.setPais(nuevosDetalles.getPais());
            }
            if (nuevosDetalles.getPoblacion() != null && !nuevosDetalles.getPoblacion().isBlank()) {
                detallesExistentes.setPoblacion(nuevosDetalles.getPoblacion());
            }

            detallesdeusuarioRepository.save(detallesExistentes);
        }

    }
}