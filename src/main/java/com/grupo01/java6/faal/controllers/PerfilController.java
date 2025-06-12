package com.grupo01.java6.faal.controllers;

import com.grupo01.java6.faal.config.UserDetailsImpl;
import com.grupo01.java6.faal.entities.Detallesdeusuario;
import com.grupo01.java6.faal.services.DetallesdeusuarioService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class PerfilController {

    private final DetallesdeusuarioService detallesdeusuarioService;

    public PerfilController(DetallesdeusuarioService detallesdeusuarioService) {
        this.detallesdeusuarioService = detallesdeusuarioService;
    }

    @PostMapping("/perfil/guardar")
    public String guardarPerfil(@ModelAttribute Detallesdeusuario detallesdeusuario) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String emailPrimario = userDetails.getUsername();

        Detallesdeusuario detallesExistentes = detallesdeusuarioService.findByEmail(emailPrimario);

        if (detallesExistentes != null) {
            if (detallesdeusuario.getNombre() != null && !detallesdeusuario.getNombre().isBlank()) {
                detallesExistentes.setNombre(detallesdeusuario.getNombre());
            }
            if (detallesdeusuario.getApellidos() != null && !detallesdeusuario.getApellidos().isBlank()) {
                detallesExistentes.setApellidos(detallesdeusuario.getApellidos());
            }
            if (detallesdeusuario.getTlf() != null && !detallesdeusuario.getTlf().isBlank()) {
                detallesExistentes.setTlf(detallesdeusuario.getTlf());
            }
            if (detallesdeusuario.getTlf2() != null && !detallesdeusuario.getTlf2().isBlank()) {
                detallesExistentes.setTlf2(detallesdeusuario.getTlf2());
            }
            if (detallesdeusuario.getDireccion() != null && !detallesdeusuario.getDireccion().isBlank()) {
                detallesExistentes.setDireccion(detallesdeusuario.getDireccion());
            }
            if (detallesdeusuario.getCodigoPostal() != null && detallesdeusuario.getCodigoPostal() != 0) {
                detallesExistentes.setCodigoPostal(detallesdeusuario.getCodigoPostal());
            }
            if (detallesdeusuario.getEmailPersonal() != null && !detallesdeusuario.getEmailPersonal().isBlank()) {
                detallesExistentes.setEmailPersonal(detallesdeusuario.getEmailPersonal());
            }
            if (detallesdeusuario.getContactoEmergencia() != null && !detallesdeusuario.getContactoEmergencia().isBlank()) {
                detallesExistentes.setContactoEmergencia(detallesdeusuario.getContactoEmergencia());
            }
            if (detallesdeusuario.getPais() != null && !detallesdeusuario.getPais().isBlank()) {
                detallesExistentes.setPais(detallesdeusuario.getPais());
            }
            if (detallesdeusuario.getPoblacion() != null && !detallesdeusuario.getPoblacion().isBlank()) {
                detallesExistentes.setPoblacion(detallesdeusuario.getPoblacion());
            }

            detallesdeusuarioService.guardar(detallesExistentes);
        }

        return "redirect:/perfil";
    }

    @GetMapping("/perfil")
    public String perfilAdmin(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();

        String emailPrimario;

        if (principal instanceof UserDetailsImpl) {
            emailPrimario = ((UserDetailsImpl) principal).getUsername();
        } else if (principal instanceof UserDetails) {
            emailPrimario = ((UserDetails) principal).getUsername();
        } else {
            emailPrimario = principal.toString();
        }

        Detallesdeusuario detalles = detallesdeusuarioService.findByEmail(emailPrimario);
        if (detalles == null) {
            detalles = new Detallesdeusuario();
            detalles.setEmailPersonal(emailPrimario);
        }

        List<Detallesdeusuario> usuarios = detallesdeusuarioService.obtenerUsuariosActivos();

        // Verificar si el usuario tiene el rol de administrador
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));

        model.addAttribute("detallesdeusuario", detalles);
        model.addAttribute("usuarios", usuarios);
        model.addAttribute("isAdmin", isAdmin); // Añadir atributo al modelo

        return "perfil";
    }

}