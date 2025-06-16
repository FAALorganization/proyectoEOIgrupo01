package com.grupo01.java6.faal.services;

import com.grupo01.java6.faal.dtos.JustificacionDTO;
import com.grupo01.java6.faal.entities.Ausencias;
import com.grupo01.java6.faal.entities.Detallesdeusuario;
import com.grupo01.java6.faal.entities.Login;
import com.grupo01.java6.faal.repositories.AusenciasRepository;
import com.grupo01.java6.faal.repositories.DetallesDeUsuarioRepository;
import com.grupo01.java6.faal.services.mappers.AusenciasMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class AusenciasService extends AbstractBusinessService<Ausencias,Integer,JustificacionDTO,AusenciasRepository,AusenciasMapper>{

    private final LoginService loginService;
    private final DetallesDeUsuarioRepository detallesDeUsuarioRepository;
    public AusenciasService(AusenciasRepository repo, AusenciasMapper ausenciasMapper, LoginService loginService, DetallesDeUsuarioRepository detallesDeUsuarioRepository) {
        super(repo, ausenciasMapper);
        this.loginService = loginService;
        this.detallesDeUsuarioRepository = detallesDeUsuarioRepository;
    }

    public List<Ausencias> findVacaciones(Integer id){
        return repo.findByLoginAusencias_Id(id);
    }

    public void guardarVacacion(Ausencias ausencia) throws Exception {
        guardarEntityEntity(ausencia);
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

    public Optional<Ausencias> obtainAusenciaByFechaInicioFechaFinId(LocalDate inicio, LocalDate fin, String nombre) {
        List<String> listaNombre = List.of(nombre.split("-"));
        String nombrePila = listaNombre.getFirst();

        String apellidos;
        if (nombre.split("-").length > 1) {
            apellidos = listaNombre.get(1) + " " + listaNombre.getLast();
        } else {
            apellidos = listaNombre.getLast();
        }

        Detallesdeusuario detallesusu = detallesDeUsuarioRepository.findByNombreAndApellidos(nombrePila, apellidos);

        Login login = detallesusu.getUsuarioLogin();
        List<Ausencias> listaAusencias = repo.findByLoginAusencias_Id(login.getId());
        for (Ausencias ausencia : listaAusencias) {
            if (ausencia.getFechaInicio().isEqual(inicio) && ausencia.getFechaFin().isEqual(fin)) {
                return Optional.of(ausencia);
            }
        }
        return Optional.empty();
    }

    public void eliminateHoliday(Ausencias ausencia) {
        repo.delete(ausencia);
    }

    public void justificarAusencia(JustificacionDTO dto, Authentication auth) throws Exception {
        String email = auth.getName();

        Ausencias ausencia = serviceMapper.toEntity(dto);
        repo.save(ausencia);
    }

    public void cambiarAprobado(Ausencias ausencia, Boolean respuesta) {
        ausencia.setAprobado(respuesta);
        repo.save(ausencia);
    }
}
