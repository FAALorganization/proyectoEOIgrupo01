package com.grupo01.java6.faal.controllers;

import com.grupo01.java6.faal.services.LoginService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;
@Slf4j
@Controller
public class LoginController {

    private final LoginService loginService;
    private final AuthenticationManager authenticationManager;

    public LoginController(LoginService loginService, AuthenticationManager authenticationManager) {
        this.loginService = loginService;
        this.authenticationManager = authenticationManager;
    }

    @GetMapping({"/loginFaal", "/", "/login"})
    public String showLogin() {
        return "loginFaal";
    }

//Endpoints de prueba para ver roles:
    //@PreAuthorize("hasRole('JEFE')")  //En caso de no tener el .requestMatchers().hasRole()
    @GetMapping("/jefe-only")
    @ResponseBody
    public Map<String, String> jefeOnlyEndpoint(Authentication auth) {
        Map<String, String> response = new LinkedHashMap<>();
        response.put("mensaje", "¡Bienvenido JEFE!");
        response.put("name", auth.getName());
        response.put("authority", auth.getAuthorities().toString());
        return response;
    }

    @GetMapping("/admin-only")
    @ResponseBody
   // @PreAuthorize("hasRole('ADMIN')") //Si usamos PreAuthorize debemos activar en la Aplication.class -> @EnableGlobalMethodSecurity(prePostEnabled = true)
    public Map<String, String> adminOnlyEndpoint(Authentication auth) {
        Map<String, String> response = new LinkedHashMap<>();
        response.put("mensaje", "¡Bienvenido ADMIN!");
        response.put("name", auth.getName());
        response.put("authority", auth.getAuthorities().toString());
        return response;
    }

    @GetMapping("/usuario-only")
    @ResponseBody
    //@PreAuthorize("hasRole('USUARIO')")
    public Map<String, String> usuarioOnlyEndpoint(Authentication auth) {
        Map<String, String> response = new LinkedHashMap<>();
        response.put("mensaje", "¡Bienvenido USUARIO!");
        response.put("name", auth.getName());
        response.put("authority", auth.getAuthorities().toString());
        return response;
    }

    @GetMapping("/visitante-only")
    @ResponseBody
    //@PreAuthorize("hasRole('VISITANTE')")
    public Map<String, String> visitanteOnlyEndpoint(Authentication auth) {
        Map<String, String> response = new LinkedHashMap<>();
        response.put("mensaje", "¡Bienvenido VISITANTE!");
        response.put("name", auth.getName());
        response.put("authority", auth.getAuthorities().toString());
        return response;
    }
}
