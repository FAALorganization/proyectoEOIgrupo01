package com.grupo01.java6.faal.controllers;

import com.grupo01.java6.faal.entities.Ausencias;
import com.grupo01.java6.faal.entities.Login;
import com.grupo01.java6.faal.services.AusenciasService;
import com.grupo01.java6.faal.services.LoginService;
import com.grupo01.java6.faal.services.TiposAusenciasService;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
public class VacacionesController {
    private final AusenciasService ausenciaService;
    private final LoginService loginService;
    private final TiposAusenciasService tiposAusenciasService;

    public VacacionesController(AusenciasService ausenciaService, LoginService loginService, TiposAusenciasService tiposAusenciasService) {
        this.ausenciaService = ausenciaService;
        this.loginService = loginService;
        this.tiposAusenciasService = tiposAusenciasService;
    }

    @PostMapping("/gestion/pedirVacaciones")
    public ResponseEntity<String> obtenerVacaciones(@RequestBody List<List<String>> listaFechas) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String userName = auth.getName();
            Login login = loginService.obtainUser(userName);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            if (login == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no encontrado");
            }

            for (List<String> fechas : listaFechas) {
                if (fechas.isEmpty()) continue;

                String fechaIni = fechas.getFirst();
                String fechaFin = fechas.getLast();

                LocalDate inicio;
                LocalDate fin;
                try {
                    inicio = LocalDate.parse(fechaIni, formatter);
                    fin = LocalDate.parse(fechaFin, formatter);
                } catch (DateTimeParseException e) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body("Fechas no válidas: " + fechaIni + " - " + fechaFin);
                }

                if (fin.isBefore(inicio)) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body("Fecha fin no puede ser anterior a fecha inicio");
                }

                Ausencias ausencia = new Ausencias();
                ausencia.setAprobado(false);
                ausencia.setJustificacion(null);
                ausencia.setDocumentos(null);
                ausencia.setLoginAusencias(login);
                ausencia.setFechaInicio(inicio);
                ausencia.setFechaFin(fin);
                ausencia.setCalcularDias(25);
                ausencia.setTiposAusencias(tiposAusenciasService.getTipoAusenciaById(1));
                ausenciaService.guardarVacacion(ausencia);
            }
            return ResponseEntity.ok("Vacaciones guardadas correctamente.");
        } catch (Exception e) {
            e.printStackTrace(); // mejor con logger
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al guardar las vacaciones.");
        }
    }

    @PostMapping("/gestion/anularVacaciones")
    public ResponseEntity<String> eliminarVacaciones(@RequestBody List<List<String>> listaFechas) {
       try {
           log.info(listaFechas.toString());
           Authentication auth = SecurityContextHolder.getContext().getAuthentication();
           String userName = auth.getName();
           Login login = loginService.obtainUser(userName);

           DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
           if (login == null) {
               return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no encontrado");
           }

           for (List<String> fechas : listaFechas) {
               if (fechas.isEmpty()) continue;
               String fechaIni = fechas.getFirst();

               LocalDate inicio;
               try {
                   inicio = LocalDate.parse(fechaIni, formatter);
               } catch (DateTimeParseException e) {
                   return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                           .body("Fechas no válidas: " + fechaIni);
               }

               Optional<Ausencias> holiday = ausenciaService.obtainHolidayByStartDate(inicio,login.getId());
               if (holiday.isPresent()) {
                   ausenciaService.eliminateHoliday(holiday.get());
               }
           }
           return ResponseEntity.ok("Vacaciones guardadas correctamente.");
    } catch (Exception e) {
        e.printStackTrace(); // mejor con logger
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error al guardar las vacaciones.");
    }
    }
}
