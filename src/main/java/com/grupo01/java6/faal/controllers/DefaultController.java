package com.grupo01.java6.faal.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controlador encargado de manejar las solicitudes relacionadas con la entidad principal.
 *
 * Este controlador utiliza la anotación {@code @Controller} para ser detectado como un componente
 * Spring MVC y maneja solicitudes HTTP. Su objetivo principal es gestionar las operaciones
 * necesarias para mostrar una lista de entidades en la vista correspondiente.
 *
 * Anotaciones importantes:
 * - {@code @Controller}: Indica que esta clase se comporta como un controlador Spring MVC.
 * - {@code @PreAuthorize}: Define que el acceso a ciertos métodos esté restringido
 *   según las reglas de autorización establecidas.
 *
 * Dependencias:
 * - {@code EntidadPadreRepository}: Interfaz del repositorio que permite interactuar con
 *   la base de datos para operaciones de persistencia y consulta relacionadas con
 *   la entidad padre.
 *
 * Métodos principales:
 * - {@code listEntities}: Maneja solicitudes GET a la URL "/entities", recupera los
 *   datos de las entidades desde la base de datos y los pasa al modelo para mostrarlos
 *   en una vista.
 *
 */
@Controller
public class DefaultController {

    @GetMapping("/test")
    public String testAusencias(){
        return "ausencias";
    }
    @GetMapping("/calendario")
    public String showCalendar()
    {

        return "calendario"; // View name
    }

    @GetMapping("/forum")
    public String showForum()
    {

        return "forum"; // View name
    }

    @GetMapping("/ticket")
    public String showticket()
    {

        return "ticket"; // View name
    }


    @GetMapping("/gestionAusencias")
    public String showgestionAusencias()
    {

        return "gestionAusencias"; // View name
    }

    @GetMapping("/gestionARes")
    public String showgestionARes()
    {

        return "gestionARes"; // View name
    }

    @GetMapping("/gestionVRes")
    public String showgestionVRes()
    {

        return "gestionVRes"; // View name
    }

    @GetMapping("/creacionTicket")
    public String showcreacionTicket()
    {

        return "creacionTicket"; // View name
    }

    @GetMapping("/checkin")
    public String showCheckin()
    {
         return "checkin"; // View name
    }
    @GetMapping("/checkinjefe")
    public String showCheckinjefe()
    {
        return "checkinjefe";}



    @GetMapping("/perfiljefe")
    public String showPerfiljefe()
    {

        return "perfiljefe"; // View name
    }

    @GetMapping("/perfil")
    public String showPerfil()
    {

        return "perfil"; // View name
    }


    @GetMapping("/home")
    public String showHome()
    {

        return "home"; // View name

    }
    @GetMapping("/TicketAdmin")
    public String showTicketAdmin()
    {

        return "TicketAdmin"; // View name

    }

    @GetMapping("/TermsAndConditions")
    public String showTermsAndConditions()
    {

        return "TermsAndConditions"; // View name

    }


}
