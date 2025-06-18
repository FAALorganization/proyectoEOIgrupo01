package com.grupo01.java6.faal.services;

import com.grupo01.java6.faal.dtos.CheckinDTO;
import com.grupo01.java6.faal.entities.Checkin;
import com.grupo01.java6.faal.entities.Login;
import com.grupo01.java6.faal.entities.TipoCheckin;
import com.grupo01.java6.faal.repositories.CheckinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CheckinService {

    @Autowired
    private CheckinRepository checkinRepository;

    @Autowired
    private LoginService loginService;

    public Login getUsuarioAutenticado() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if (username == null || username.equals("anonymousUser")) {
            return null;
        }
        return loginService.obtainUser(username);
    }

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

    public void cargarDatosCheckinParaVista(Model model, Login login) {
        List<CheckinDTO> checkinsDTO = obtenerCheckinsPorUsuario(login);
        model.addAttribute("checkins", checkinsDTO);

        boolean tieneRolJefe = loginService.tieneRolJefe(login);
        model.addAttribute("tieneRolJefe", tieneRolJefe);

        if (tieneRolJefe) {
            Login loginConSubordinados = loginService.obtenerPorIdConSubordinados(login.getId());
            if (loginConSubordinados != null) {
                List<Login> subordinadosSinJefe = loginConSubordinados.getSubordinados()
                        .stream()
                        .filter(sub -> !sub.getId().equals(login.getId()))
                        .collect(Collectors.toList());

                model.addAttribute("subordinados", subordinadosSinJefe);
            }
        }
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

    public List<CheckinDTO> obtenerCheckinsPorEmpleado(Integer idEmpleado) {
        Login empleado = loginService.obtenerPorId(idEmpleado);
        if (empleado == null) {
            return List.of();
        }
        return obtenerCheckinsPorUsuario(empleado);
    }
    public boolean tieneCheckoutPendiente(Login login) {
        List<Checkin> checkins = checkinRepository.findByIdLoginCheckin_IdOrderByIdDesc(login.getId());
        if (!checkins.isEmpty()) {
            Checkin ultimoCheckin = checkins.get(0);
            return ultimoCheckin.getHoraSalida() == null &&
                    ultimoCheckin.getFecha().equals(LocalDate.now());
        }
        return false;
    }
}
