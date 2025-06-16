package com.grupo01.java6.faal.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller

public class ErrorController {
    @GetMapping("error")
    public String accessDenied() {
    return "error";
    }
}
