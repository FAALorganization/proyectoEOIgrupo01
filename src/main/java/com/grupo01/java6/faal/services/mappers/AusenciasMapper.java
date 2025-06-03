package com.grupo01.java6.faal.services.mappers;

import com.grupo01.java6.faal.dtos.JustificacionDTO;
import com.grupo01.java6.faal.entities.Ausencias;
import com.grupo01.java6.faal.repositories.AusenciasRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Slf4j
@Service
public class AusenciasMapper extends AbstractServiceMapper<Ausencias, JustificacionDTO> {
    private AusenciasRepository ausenciasRepository;

    @Override
    public JustificacionDTO toDto(Ausencias entidad) {
        return null;
    }

    @Override
    public Ausencias toEntity(JustificacionDTO dto) throws Exception {
        String fechaString = dto.getFecha();
        Optional<Ausencias> ausenciaRegistro = Optional.empty();
        Ausencias ausencia = ausenciaRegistro.get();
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

            String asunto = dto.getAsunto();
            String descripcion = dto.getDescripcion();
            ausencia.setJustificacion(asunto + "//" + descripcion);
            if (dto.getArchivos() != null) {
                ausencia.setDocumentos(dto.getArchivos());
            }
            //ausenciasRepository.save(ausencia);
            return ausencia;
        }
        return ausencia;
    }
}
