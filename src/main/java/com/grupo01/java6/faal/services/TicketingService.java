package com.grupo01.java6.faal.services;

import com.grupo01.java6.faal.dtos.TicketingDTO;
import com.grupo01.java6.faal.entities.Prioridades;
import com.grupo01.java6.faal.entities.Ticketing;
import com.grupo01.java6.faal.repositories.PrioridadesRepository;
import com.grupo01.java6.faal.repositories.TicketingRepository;
import com.grupo01.java6.faal.services.impl.TicketSrvImpl;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class TicketingService implements TicketSrvImpl {

    private final TicketingRepository ticketingRepository;
    private final PrioridadesRepository prioridadesRepository;
    private final ModelMapper modelMapper;

    public TicketingService(TicketingRepository ticketingRepository,
                            PrioridadesRepository prioridadesRepository,
                            ModelMapper modelMapper) {
        this.ticketingRepository = ticketingRepository;
        this.prioridadesRepository = prioridadesRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public TicketingDTO create(TicketingDTO dto) {
        if (dto.getPrioridad() == null) {
            throw new ResourceNotFoundException("Prioridad no especificada");
        }

        Prioridades prioridad = (Prioridades) prioridadesRepository.findByPrioridadesEnum(dto.getPrioridad())
                .orElseThrow(() -> new ResourceNotFoundException("Prioridad no encontrada"));

        Ticketing ticket = modelMapper.map(dto, Ticketing.class);
        ticket.setIdPrior(prioridad);
        ticket.setFechaInicio(LocalDate.now());
        ticket.setAprobado(false);
        ticket.setModificacion(null);
        ticket.setEliminacion(null);

        return convertToDto(ticketingRepository.save(ticket));
    }

    @Override
    @Transactional(readOnly = true)
    public TicketingDTO findById(Integer id) {
        return ticketingRepository.findActiveById(id)
                .map(this::convertToDto)
                .orElseThrow(() -> new ResourceNotFoundException( "Ticket no encontrado"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<TicketingDTO> findAll() {
        return ticketingRepository.findAll().stream()
                .map(this::convertToDto)
                .toList();
    }

    @Override
    @Transactional
    public TicketingDTO update(TicketingDTO dto) {
        Ticketing existing = ticketingRepository.findActiveById(dto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Ticket no encontrado"));

        if (dto.getPrioridad() != null) {
            Prioridades prioridad = (Prioridades) prioridadesRepository.findByPrioridadesEnum(dto.getPrioridad())
                    .orElseThrow(() -> new ResourceNotFoundException("Prioridad no encontrada"));
            existing.setIdPrior(prioridad);
        }

        modelMapper.map(dto, existing);
        existing.setModificacion(LocalDate.now());

        return convertToDto(ticketingRepository.save(existing));
    }



    @Override
    @Transactional
    public static TicketingDTO createTicket(TicketingDTO dto, String username) {
        Ticketing ticket = modelMapper.map(dto, Ticketing.class);
        ticket.setCreatedBy(username);
        return convertToDto(ticketingRepository.save(ticket));
    }

    @Override
    @Transactional
    public TicketingDTO updateTicket(Integer id, TicketingDTO ticketDTO){
        ticketDTO.setId(id.intValue());
        return update(ticketDTO);
    }

    @Override
    @Transactional
    public void approveTicket(Integer id) {
        Ticketing ticket = ticketingRepository.findActiveById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket no encontrado"));
        ticket.setAprobado(true);
        ticket.setModificacion(LocalDate.now());
        ticketingRepository.save(ticket);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        Ticketing ticket = ticketingRepository.findActiveById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket no encontrado"));
        ticket.setEliminacion(LocalDate.now());
        ticketingRepository.save(ticket);
    }

    @Transactional(readOnly = true)
    public List<TicketingDTO> findByAprobado(Boolean aprobado) {
        return ticketingRepository.findByAprobado(aprobado).stream()
                .map(this::convertToDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TicketingDTO> findByAprobado(Boolean aprobado, Pageable pageable) {
        return ticketingRepository.findFilteredTickets(aprobado, null, pageable)
                .map(this::convertToDto);
    }

    private TicketingDTO convertToDto(Ticketing ticket) {
        TicketingDTO dto = modelMapper.map(ticket, TicketingDTO.class);
        if (ticket.getIdPrior() != null) {
            dto.setPrioridad(ticket.getIdPrior().getPrioridadesEnum());
        }
        return dto;
    }
    static class ResourceNotFoundException extends RuntimeException {
        public ResourceNotFoundException(String message) {
            super(message);
        }
    }
}
