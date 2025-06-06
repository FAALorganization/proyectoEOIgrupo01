package com.grupo01.java6.faal.controllers;

import com.grupo01.java6.faal.entities.Checkin;
import com.grupo01.java6.faal.repositories.CheckinRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.time.format.DateTimeFormatter;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import com.grupo01.java6.faal.entities.TipoCheckin;
import com.grupo01.java6.faal.entities.Login;

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
    @PostMapping("/checkin")
    public ResponseEntity<String> realizarCheckin(@RequestParam String tipo, @RequestParam Integer idLogin) {
        Checkin checkin = new Checkin();
        checkin.setFecha(LocalDate.now());
        checkin.setHoraEntrada(LocalTime.now());
        checkin.setTipo(TipoCheckin.valueOf(tipo.toUpperCase()));
        Login login = new Login();
        login.setId(idLogin);
        checkin.setIdLoginCheckin(login);

        checkinRepository.save(checkin);
        return ResponseEntity.ok("Check-in registrado correctamente.");
    }
    @PostMapping("/checkout")
    public ResponseEntity<String> realizarCheckout(@RequestParam Integer idLogin) {
        List<Checkin> checkins = checkinRepository.findByIdLoginCheckin_IdOrderByIdDesc(idLogin);
        if (!checkins.isEmpty()) {
            Checkin ultimoCheckin = checkins.get(0);
            ultimoCheckin.setHoraSalida(LocalTime.now());
            checkinRepository.save(ultimoCheckin);
            return ResponseEntity.ok("Check-out registrado correctamente.");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se encontró un registro de Check-in para este usuario.");
    }
}
