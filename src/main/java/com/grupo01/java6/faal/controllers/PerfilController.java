package com.grupo01.java6.faal.controllers;

import com.grupo01.java6.faal.config.UserDetailsImpl;
import com.grupo01.java6.faal.entities.Detallesdeusuario;
import com.grupo01.java6.faal.services.DetallesdeusuarioService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/perfil")
public class PerfilController {

    private final DetallesdeusuarioService detallesdeusuarioService;

    public PerfilController(DetallesdeusuarioService detallesdeusuarioService) {
        this.detallesdeusuarioService = detallesdeusuarioService;
    }

    @PostMapping("/guardar")
    public String guardarPerfil(@ModelAttribute Detallesdeusuario detallesdeusuario) {
        // Obtener el usuario autenticado
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        // Obtener el email o identificador del usuario desde UserDetailsImpl
        String emailPrimario = userDetails.getUsername();

        // Buscar los detalles existentes del usuario por email
        Detallesdeusuario detallesExistentes = detallesdeusuarioService.findByEmail(emailPrimario);

        if (detallesExistentes != null) {
            // Actualizar los detalles existentes
            detallesExistentes.setNombre(detallesdeusuario.getNombre());
            detallesExistentes.setApellidos(detallesdeusuario.getApellidos());
            detallesExistentes.setTlf(detallesdeusuario.getTlf());
            detallesExistentes.setTlf2(detallesdeusuario.getTlf2());
            detallesExistentes.setDireccion(detallesdeusuario.getDireccion());
            detallesExistentes.setCodigoPostal(detallesdeusuario.getCodigoPostal());
            detallesExistentes.setEmailPersonal(detallesdeusuario.getEmailPersonal());
            detallesExistentes.setContactoEmergencia(detallesdeusuario.getContactoEmergencia());
            detallesExistentes.setPais(detallesdeusuario.getPais());
            detallesExistentes.setPoblacion(detallesdeusuario.getPoblacion());

            // Guardar los cambios
            detallesdeusuarioService.guardar(detallesExistentes);
        }

        return "redirect:/perfiladmin"; // Redirige al perfil después de guardar
    }
}