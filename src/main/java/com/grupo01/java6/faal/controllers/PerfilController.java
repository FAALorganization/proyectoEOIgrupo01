package com.grupo01.java6.faal.controllers;

import com.grupo01.java6.faal.config.UserDetailsImpl;
import com.grupo01.java6.faal.dtos.SubordinadoDTO;
import com.grupo01.java6.faal.entities.Detallesdeusuario;
import com.grupo01.java6.faal.services.DetallesdeusuarioService;
import com.grupo01.java6.faal.services.EquipoService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import com.grupo01.java6.faal.services.PerfilService;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PerfilController {

    private final DetallesdeusuarioService detallesdeusuarioService;
    private final PerfilService perfilService;
    private final EquipoService equipoService;

    public PerfilController(DetallesdeusuarioService detallesdeusuarioService, PerfilService perfilService, EquipoService equipoService) {
        this.detallesdeusuarioService = detallesdeusuarioService;
        this.perfilService = perfilService;
        this.equipoService = equipoService;
    }


    @PostMapping("/perfil/guardar")
    public String guardarPerfil(@ModelAttribute Detallesdeusuario detallesdeusuario) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String emailPrimario = userDetails.getUsername();

        detallesdeusuarioService.actualizarDetalles(emailPrimario, detallesdeusuario);

        return "redirect:/perfil";
    }


    @GetMapping("/perfil")
    public String perfilAdmin(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String email = perfilService.obtenerEmailPrimario(authentication);
        Detallesdeusuario detalles = perfilService.obtenerDetallesUsuario(email);
        List<Detallesdeusuario> usuarios = perfilService.obtenerUsuariosActivos();
        List<SubordinadoDTO> subordinados = new ArrayList<>();
        boolean isAdmin = perfilService.tieneRol(authentication, "ROLE_ADMIN");
        boolean isJefe = perfilService.tieneRol(authentication, "ROLE_JEFE");
        if (isJefe) {
            subordinados = perfilService.obtenerSubordinadosDelJefeActual(); // <--- método que tú implementás
        }
        model.addAttribute("detallesdeusuario", detalles);
        model.addAttribute("usuarios", usuarios);
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("isJefe", isJefe);
        model.addAttribute("subordinados", subordinados);


        return "perfil";
    }
}
