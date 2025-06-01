package com.grupo01.java6.faal.controllers;

import com.grupo01.java6.faal.entities.Login;
import com.grupo01.java6.faal.entities.Roles;
import com.grupo01.java6.faal.services.LoginService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Set;

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
    public String showLogin()
    {
        return "loginFaal"; // View name
    }



    @PostMapping("/login")
    public String login(
            @RequestParam String correo,
            @RequestParam String contrasena,
            Model model,
            HttpServletRequest request
    ) { try {
        // 1. Crear token de autenticacion con las credenciales recibidas:
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(correo, contrasena);
        var authentication = authenticationManager.authenticate(authRequest);

        // 2. Delegar autenticacion a Spring Security:
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 3. Si la autenticación es correcta, guardar contexto de seguridad:
        HttpSession session = request.getSession(true);
        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());

        // 5. Mensaje y redirección a home:
        model.addAttribute("mensaje", "¡Inicio de sesión exitoso!");
        log.info("Correcto: " + correo);
        return "redirect:/home";

    } catch (BadCredentialsException e) {
        // 6. Si la autenticación falla, mostrar error:
        model.addAttribute("error", "Credenciales incorrectas");
        log.info("Error login: " + correo + " - " + e.getMessage());
        return "loginFaal";  // Volver a login
        }catch (Exception e) {
        model.addAttribute("error", "Error inesperado: " + e.getMessage());
        log.error("Error inesperado en login: " + correo, e);
        return "loginFaal";
        }
    }
}
