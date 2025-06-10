package com.grupo01.java6.faal.Mapper;

import com.grupo01.java6.faal.dtos.TicketingDTO;
import com.grupo01.java6.faal.entities.Prioridades;
import com.grupo01.java6.faal.entities.Ticketing;
import org.springframework.stereotype.Component;

import com.grupo01.java6.faal.entities.Ticketing;
@Component

public class TicketMapper {

    public TicketingDTO toDto (Ticketing entity) {
        return new TicketingDTO(
                entity.getId(),
                entity.getNombre(),
                entity.getAsunto(),
                entity.getDescripcion(),
                entity.getTipoTicket(),
                entity.getFechaInicio(),
                entity.getFechaFin(),
                entity.getAprobado(),
                entity.getIdPrior().getPrioridadesEnum()

        );

    }

    public Ticketing toEntity(TicketingDTO dto, Prioridades prioridad) {
        Ticketing t = new Ticketing();
        t.setId(dto.getId());
        t.setNombre(dto.getNombre());
        t.setAsunto(dto.getAsunto());
        t.setDescripcion(dto.getDescripcion());
        t.setTipoTicket(dto.getTipoTicket());
        t.setFechaInicio(dto.getFechaInicio());
        t.setFechaFin(dto.getFechaFin());
        t.setAprobado(dto.getAprobado());
        t.setIdPrior(prioridad);
        return t;
    }
}
