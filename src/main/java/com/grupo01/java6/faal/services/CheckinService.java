package com.grupo01.java6.faal.services;

import com.grupo01.java6.faal.dtos.CheckinDTO;
import com.grupo01.java6.faal.entities.Checkin;
import com.grupo01.java6.faal.entities.Login;
import com.grupo01.java6.faal.entities.TipoCheckin;
import com.grupo01.java6.faal.repositories.CheckinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class CheckinService {

    @Autowired
    private CheckinRepository checkinRepository;

    public List<CheckinDTO> obtenerCheckinsPorUsuario(Login login) {
        List<Checkin> lista = checkinRepository.findByIdLoginCheckin_IdOrderByIdDesc(login.getId());

        return lista.stream().map(checkin -> new CheckinDTO(
                checkin.getId(),
                checkin.getHoraEntrada(),
                checkin.getHoraSalida(),
                checkin.getFecha(),
                checkin.getIp(),
                checkin.getTipo()
        )).toList();
    }



    public String registrarCheckin(Login login, String tipo) {
        Checkin checkin = new Checkin();
        checkin.setFecha(LocalDate.now());
        checkin.setHoraEntrada(LocalTime.now());
        checkin.setTipo(TipoCheckin.valueOf(tipo.toUpperCase()));
        checkin.setIdLoginCheckin(login);
        checkinRepository.save(checkin);
        return "Check-in registrado correctamente.";
    }

    public String registrarCheckout(Login login) {
        List<Checkin> checkins = checkinRepository.findByIdLoginCheckin_IdOrderByIdDesc(login.getId());
        if (!checkins.isEmpty()) {
            Checkin ultimoCheckin = checkins.get(0);
            ultimoCheckin.setHoraSalida(LocalTime.now());
            checkinRepository.save(ultimoCheckin);
            return "Check-out registrado correctamente.";
        } else {
            return "No se encontró un registro de Check-in para este usuario.";
        }
    }

}
