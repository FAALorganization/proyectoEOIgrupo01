package com.grupo01.java6.faal.controllers;

import com.grupo01.java6.faal.entities.Checkin;
import com.grupo01.java6.faal.repositories.CheckinRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.time.format.DateTimeFormatter;

import java.util.List;

@Slf4j
@Controller
public class CheckinController {

    private final CheckinRepository checkinRepository;

    @Autowired
    public CheckinController(CheckinRepository checkinRepository) {
        this.checkinRepository = checkinRepository;
    }

    @GetMapping("/checkin")
    public String listarCheckins(Model model) {
        List<Checkin> lista = checkinRepository.findAll();
        lista.forEach(checkin -> checkin.setFechaFormatted(checkin.getFecha().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
        model.addAttribute("checkins", lista);
        return "checkin";
    }

}
