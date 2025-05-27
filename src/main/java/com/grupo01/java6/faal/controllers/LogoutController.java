package com.grupo01.java6.faal.controllers;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class LogoutController {

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {

        //Invalida la sesion actual:
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        //Eliminar la cookie "JSESSIONID" del navegador previamente creada al hacer login:
        Cookie cookie = new Cookie("JSESSIONID", null); //Asignamos el valor null a la cookie
        cookie.setPath("/"); //Establece que la cookie es valida para todas las rutas del dominio
        cookie.setHttpOnly(true); //Permite que la cookie no pueda ser accedida a traves de JavaScript
        cookie.setMaxAge(0); //Pone la cookie con tiempo 0 obligando al navegador a eliminarla
        response.addCookie(cookie); //Añade la cookie a la respuesta permite sobreescribir y eliminar la cookie existente

        //Redirige a login u otra página pública
        return "redirect:/";
    }
}
