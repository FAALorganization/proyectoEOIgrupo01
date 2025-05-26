package com.grupo01.java6.faal.controllers;

import com.grupo01.java6.faal.entities.Login;
import com.grupo01.java6.faal.repositories.LoginRepository;
import com.grupo01.java6.faal.services.LoginService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Slf4j
@Controller
public class LoginController {

    private LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping({"/loginFaal", "/"})
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
    ) {
        Login login = loginService.obtainUser(correo);
        // Aquí podrías validar contra una base de datos real
        if (login != null && login.getPassword().equals(contrasena)) {
            //Crea un token de autenticacion:
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(correo, contrasena,
                            List.of(new SimpleGrantedAuthority("ROLE_USER")));

            //Establece autenticacion en contexto de seguridad:
            SecurityContextHolder.getContext().setAuthentication(authToken);

            //Crea sesion y asocia contexto de seguridad:
            HttpSession session = request.getSession(true);
            session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,SecurityContextHolder.getContext());
            model.addAttribute("mensaje", "¡Inicio de sesión exitoso!");
            log.info("Correcto: " + correo + " " + contrasena);
            return "redirect:/home"; // plantilla HTML de bienvenida

        } else {
            model.addAttribute("error", "Credenciales incorrectas");
            log.info("Error: " + correo + " " + contrasena);
            return "loginFaal"; // vuelve a la plantilla de login
        }
    }
}
