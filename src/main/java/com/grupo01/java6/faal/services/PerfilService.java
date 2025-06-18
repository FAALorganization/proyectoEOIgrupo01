package com.grupo01.java6.faal.services;

import com.grupo01.java6.faal.config.UserDetailsImpl;
import com.grupo01.java6.faal.dtos.CompaneroDTO;
import com.grupo01.java6.faal.dtos.SubordinadoDTO;
import com.grupo01.java6.faal.entities.Detallesdeusuario;
import com.grupo01.java6.faal.entities.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.grupo01.java6.faal.services.LoginService;

import java.util.List;

@Service
public class PerfilService {

    @Autowired
    private DetallesdeusuarioService detallesdeusuarioService;
    @Autowired
    private LoginService loginService;  // Esto es un objeto de la clase LoginService

    public Detallesdeusuario obtenerDetallesUsuario(String email) {
        Detallesdeusuario detalles = detallesdeusuarioService.findByEmail(email);
        if (detalles == null) {
            detalles = new Detallesdeusuario();
            detalles.setEmailPersonal(email);
        }
        return detalles;
    }

    public List<Detallesdeusuario> obtenerUsuariosActivos() {
        return detallesdeusuarioService.obtenerUsuariosActivos();
    }

    public boolean tieneRol(Authentication authentication, String rol) {
        return authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(rol));
    }

    public String obtenerEmailPrimario(Authentication authentication) {
        Object principal = authentication.getPrincipal();

        if (principal instanceof UserDetailsImpl) {
            return ((UserDetailsImpl) principal).getUsername();
        } else if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return principal.toString();
        }
    }
    @Transactional
    public List<SubordinadoDTO> obtenerSubordinadosDelJefeActual() {
        // Obtener el usuario actual desde el contexto de seguridad
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Login jefe = loginService.obtainUser(email); // Obtener el jefe actual

        if (jefe != null) {
            return jefe.getSubordinados().stream()
                    .filter(subordinado -> !subordinado.getId().equals(jefe.getId())) // Excluir al propio jefe
                    .map(subordinado -> new SubordinadoDTO(
                            subordinado.getId(),
                            subordinado.getIdDetallesDeUsuario().getNombre(),
                            subordinado.getEmailPrimario(),
                            subordinado.getIdDetallesDeUsuario().getTlf()

                    ))
                    .toList();
        }
        return List.of();
    }


}

