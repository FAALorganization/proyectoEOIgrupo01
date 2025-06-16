package com.grupo01.java6.faal.services.mappers;

import com.grupo01.java6.faal.dtos.JustificacionDTO;
import com.grupo01.java6.faal.entities.Ausencias;
import com.grupo01.java6.faal.entities.Login;
import com.grupo01.java6.faal.repositories.AusenciasRepository;
import com.grupo01.java6.faal.services.AusenciasService;
import com.grupo01.java6.faal.services.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.cglib.core.Local;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class AusenciasMapper extends AbstractServiceMapper<Ausencias, JustificacionDTO> {
    private final AusenciasRepository ausenciasRepository;
    private final LoginService loginService;

    public AusenciasMapper(AusenciasRepository ausenciasRepository, LoginService loginService) {
        this.ausenciasRepository = ausenciasRepository;
        this.loginService = loginService;
    }

    @Override
    public JustificacionDTO toDto(Ausencias entidad) {
        return null;
    }

    @Override
    public Ausencias toEntity(JustificacionDTO dto) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        Login login = loginService.obtainUser(email);
        if (login == null) {
            throw new Exception("Usuario no encontrado");
        }

        Integer usuarioId = login.getId();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        LocalDate fechaInicio;
        LocalDate fechaFin;

        String fechaString = dto.getFecha();

        if (fechaString.contains(" al ")) {
            String[] fechas = fechaString.split(" al ");
            fechaInicio = LocalDate.parse(fechas[0].trim(), formatter);
            fechaFin = LocalDate.parse(fechas[1].trim(), formatter);
        } else {
            fechaInicio = LocalDate.parse(fechaString.trim(), formatter);
            fechaFin = fechaInicio;
        }

        List<Ausencias> listaAusencias = ausenciasRepository.findByLoginAusencias_Id(usuarioId);

        Optional<Ausencias> ausenciaRegistro = Optional.empty();

        for (Ausencias ausencia : listaAusencias) {
            if (ausencia.getFechaInicio().isEqual(fechaInicio) && ausencia.getFechaFin().isEqual(fechaFin)) {
                ausenciaRegistro = Optional.of(ausencia);
                break;  // Salimos cuando la encontramos
            }
        }

        if (!ausenciaRegistro.isPresent()) {
            throw new Exception("No se encontró la ausencia para actualizar");
        }

        Ausencias ausencia = ausenciaRegistro.get();

        String asunto = dto.getAsunto();
        String descripcion = dto.getDescripcion();

        ausencia.setJustificacion(asunto + "//" + descripcion);

        if (dto.getArchivos() != null) {
            ausencia.setDocumentos(dto.getArchivos());
        }

        return ausencia;
    }


}
