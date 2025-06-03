package com.grupo01.java6.faal.services;

import com.grupo01.java6.faal.dtos.JustificacionDTO;
import com.grupo01.java6.faal.entities.Ausencias;
import com.grupo01.java6.faal.repositories.AusenciasRepository;
import com.grupo01.java6.faal.services.mappers.AusenciasMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class AusenciasService extends AbstractBusinessService<Ausencias,Integer,JustificacionDTO,AusenciasRepository,AusenciasMapper>{

    public AusenciasService(AusenciasRepository repo, AusenciasMapper ausenciasMapper) {
        super(repo, ausenciasMapper);
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

    public void eliminateHoliday(Ausencias ausencia) {
        repo.delete(ausencia);
    }
    public void justificarAusencia(JustificacionDTO dto) throws Exception {
        Ausencias ausencia = serviceMapper.toEntity(dto);
        repo.save(ausencia);
    }
}
