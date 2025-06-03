package com.grupo01.java6.faal.services;

import com.grupo01.java6.faal.dtos.JustificacionDTO;
import com.grupo01.java6.faal.entities.Ausencias;
import com.grupo01.java6.faal.repositories.AusenciasRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class AusenciasService {
    private final AusenciasRepository ausenciasRepository;

    public AusenciasService(AusenciasRepository ausenciasRepository) {
        this.ausenciasRepository = ausenciasRepository;
    }

    public List<Ausencias> findVacaciones(Integer id){
        return ausenciasRepository.findByLoginAusencias_Id(id);
    }

    public void guardarVacacion(Ausencias ausencia) {
        ausenciasRepository.save(ausencia);
    }

    public Optional<Ausencias> obtainHolidayByStartDate(LocalDate date, Integer idUsuario) {
        List<Ausencias> listaAusencias = findVacaciones(idUsuario);
        for (Ausencias ausencia : listaAusencias) {
            if (ausencia.getFechaInicio().isEqual(date)) {
                return Optional.of(ausencia);
            }
        }
        return Optional.empty();
    }

    public void eliminateHoliday(Ausencias ausencia) {
        ausenciasRepository.delete(ausencia);
    }
    public boolean justificarAusencia(JustificacionDTO dto) {
        String fechaString = dto.getFecha();
        Optional<Ausencias> ausenciaRegistro;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        if (fechaString.contains(" al ")) {
            String[] fechas = fechaString.split(" al ");
            LocalDate fechaFin = LocalDate.parse(fechas[1], formatter);
            LocalDate fechaInicio = LocalDate.parse(fechas[0], formatter);
            ausenciaRegistro = ausenciasRepository.findByFechaFinAndFechaInicio(fechaFin, fechaInicio);

            log.info("\nFECHAS:\n" + fechaFin + "\n" + fechaInicio);
        } else {
            LocalDate fechaFin = LocalDate.parse(fechaString, formatter);
            LocalDate fechaInicio = LocalDate.parse(fechaString, formatter);
            ausenciaRegistro = ausenciasRepository.findByFechaFinAndFechaInicio(fechaFin, fechaInicio);
            log.info("\nFECHAS:\n" + fechaFin + "\n" + fechaInicio);

        }

        if(ausenciaRegistro.isPresent()){
            Ausencias ausencia = ausenciaRegistro.get();
            String asunto = dto.getAsunto();
            String descripcion = dto.getDescripcion();
            ausencia.setJustificacion(asunto + "//" + descripcion);
            log.info("\nA escribir: " + dto.getArchivos());
            if (dto.getArchivos() != null && !dto.getArchivos().isEmpty()) {
                ausencia.setDocumentos(dto.getArchivos());
                log.info("\nEscribiendo: " + dto.getArchivos());
            }
            ausenciasRepository.save(ausencia);
            return true;
        }
        return false;

    }
}
