package com.grupo01.java6.faal.services;
//import com.grupo01.java6.faal.repositories.TicketRelUsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.grupo01.java6.faal.dtos.TicketingDTO;
import com.grupo01.java6.faal.entities.Login;
import com.grupo01.java6.faal.entities.Prioridades;
import com.grupo01.java6.faal.entities.Ticketing;
import com.grupo01.java6.faal.repositories.LoginRepository;
import com.grupo01.java6.faal.repositories.PrioridadesRepository;
import com.grupo01.java6.faal.repositories.TicketingRepository;
import com.grupo01.java6.faal.services.impl.TicketService;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Transactional
@Service
public class TicketingService implements TicketService {
    private final LoginRepository loginRepository;
    private final TicketingRepository ticketingRepository;
    private final PrioridadesRepository prioridadesRepository;
    //private final TicketRelUsuarioRepository ticketRelUsuarioRepository;
    private final ModelMapper modelMapper;
// crear el ticket and save it to dto  checking la prioridad

    @Override
    public TicketingDTO createTicket(TicketingDTO dto, String userEmail) {
        log.info("Creating ticket for user: {}", userEmail);

        Login usuario = loginRepository.getLoginByEmailPrimario(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado: " + userEmail));

        // 2. Find priority - corrected lookup

        Prioridades prioridad = prioridadesRepository.findByValue(dto.getPrioridad().toLowerCase())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Prioridad no encontrada: " + dto.getPrioridad() +
                                ". Valores válidos: " + prioridadesRepository.findAllPriorityValues()));

        // 3. Create and save ticket

        Ticketing ticket = modelMapper.map(dto, Ticketing.class);
        ticket.setPrioridad(prioridad);
        ticket.setCreatedBy(usuario);
        ticket.setFechaInicio(LocalDate.now());
        ticket.setAprobado(false);
        Ticketing saved = ticketingRepository.save(ticket);
        log.info("Ticket created successfully for user {} with ID: {}", userEmail, saved.getId());
        return convertToDto(saved);

    }




    @Override
    @Transactional(readOnly = true)
    public TicketingDTO findById(Integer id) {
        return ticketingRepository.findActiveById(id)
                .map(this::convertToDto)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket no encontrado con ID: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<TicketingDTO> findAll() {
        return ticketingRepository.findAllActive().stream()
                .map(this::convertToDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<TicketingDTO> findUserTickets(String userEmail) {
        return ticketingRepository.findByCreatorEmail(userEmail).stream()
                .map(this::convertToDto)
                .toList();
    }
    @Override
    public TicketingDTO  save (TicketingDTO ticketingDTO, String userEmail) {
        log.info("Saving ticket (ID: {}) by user {}", ticketingDTO.getId(), userEmail);

        Login usuario = loginRepository.getLoginByEmailPrimario(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado: " + userEmail));

        // validar la prioridad
        String priorityValue = ticketingDTO.getPrioridad().toLowerCase();
        Prioridades prioridad = prioridadesRepository.findByValue(priorityValue)
                .orElseThrow(() -> new ResourceNotFoundException("Prioridad no encontrada: " + ticketingDTO.getPrioridad()));

        Ticketing ticket;

        if (ticketingDTO.getId()!=null) {

            ticket =ticketingRepository.findActiveById(ticketingDTO.getId())
                    .orElseThrow(()->new org.springframework.data.rest.webmvc.ResourceNotFoundException("Ticket Activo no encontrado con el ID:"+ ticketingDTO.getId()));
            ticket.setAsunto(ticketingDTO.getAsunto());
            ticket.setModificacion(LocalDate.now());
            ticket.setCreatedBy(usuario) ;

        } else {
            // 4. New ticket (create)
            ticket = modelMapper.map(ticketingDTO,
                    Ticketing.class);
            ticket.setIdPrior(prioridad);
            ticket.setFechaInicio(LocalDate.now());
            ticket.setModificacion(LocalDate.now());
            ticket.setCreatedBy(usuario) ;
            ticket.setFechaInicio(LocalDate.now());
            ticket.setAprobado(false);
        }
        Ticketing saved = ticketingRepository.save(ticket);
        log.info("Ticket (ID: {}) saved successfully by {}", saved.getId(), userEmail);

        return convertToDto(saved);
    }

    @Override
    public TicketingDTO reopenTicket(Integer id, String reopenedBy, String reason) {
        Ticketing ticket = ticketingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket no encontrado con ID: " + id));

        // Validate ticket status
        if (!ticket.getStatus().equals(Ticketing.TicketStatus.CLOSED)) {
            throw new IllegalStateException("Solo tickets cerrados pueden ser reabiertos");
        }

        // Update ticket properties
        ticket.setStatus(Ticketing.TicketStatus.REOPENED);
       // ticket.setFechaModificacion(LocalDateTime.now());
        ticket.setUsuarioModificacion(reopenedBy);

//        // Create a new history entry for the reopening
//        TicketHistory history = new TicketHistory();
//        history.setTicket(ticket);
//        history.setAction("REOPENED");
//        history.setDescription(reason != null ? reason : "Ticket reabierto sin especificar motivo");
//        history.setChangedBy(reopenedBy);
//        history.setChangedAt(LocalDateTime.now());
//
//        // Reset SLA tracking if needed
//        if (ticket.getSlaTracking() != null) {
//            ticket.getSlaTracking().setPaused(false);
//            ticket.getSlaTracking().setPauseStartTime(null);
//            ticket.getSlaTracking().setTotalPausedDuration(0L);
//        }
//
//        // Save changes
//        ticketingRepository.save(ticket);
//        ticketHistoryRepository.save(history);

        return convertToDto(ticketingRepository.save(ticket));
    }


    // update priority media alta
    @Override
    public TicketingDTO update(TicketingDTO dto) {
        Ticketing existing = ticketingRepository.findActiveById(dto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Ticket no encontrado"));

        // Update fields selectively
        if (dto.getNombre() != null) existing.setNombre(dto.getNombre());
        if (dto.getAsunto() != null) existing.setAsunto(dto.getAsunto());
        if (dto.getDescripcion() != null) existing.setDescripcion(dto.getDescripcion());
        if (dto.getTipoTicket() != null) existing.setTipoTicket(dto.getTipoTicket());
        // to do modify entity later on with the ux
        //if (dto.getFechaFin() != null) existing.setFechaFin(dto.getFechaQueja());

        return convertToDto(ticketingRepository.save(existing));
    }
//PUT /tickets/123 for future use upadter directement

    public TicketingDTO updateTicket(Integer id, TicketingDTO ticketDTO, String updatedBy) {
        ticketDTO.setId(id);
        Optional<TicketingDTO> existingTicket = Optional.ofNullable(findById(id));
        if (existingTicket.isEmpty()) {
            throw new EntityNotFoundException("Ticket with ID " + id + " not found");
        }
        return update(ticketDTO);
    }
 // aprove solo si es un Admin  tal que el aprovemeail en la entity es emaiprimario
    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void approveTicket(Integer id, String approveEmail) {
        log.info("Approving ticket {} by user {}", id, approveEmail);

        Ticketing ticket = ticketingRepository.findActiveById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket no encontrado"));

        Login aprobador = loginRepository.getLoginByEmailPrimario(approveEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario aprobador no encontrado"));
        // Ahora hacer un update

        ticket.setAprobado(true);
        ticket.setUsuarioAprobador(aprobador);
        ticket.setModificacion(LocalDate.now());

        ticketingRepository.save(ticket);
        log.info("Ticket {} approved successfully by {}", id, approveEmail);
    }
// user admin desaprove the ticket (future use ui )
    public void rejectTicket(Integer id, String approverEmail) {
        log.info("Rejecting ticket {} by user {}", id, approverEmail);

        Ticketing ticket = ticketingRepository.findActiveById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket no encontrado"));
// si es deleted or inactive exeptio rule (future use ui)
        Login aprobador = loginRepository.getLoginByEmailPrimario(approverEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario aprobador no encontrado"));

        ticket.setAprobado(false);
        ticket.setUsuarioAprobador(aprobador);
        ticket.setModificacion(LocalDate.now());

        ticketingRepository.save(ticket);
        log.info("Ticket {} rejected by {}", id, approverEmail);
    }


/// close ticket to do
    @Override
    @Transactional
    public TicketingDTO closeTicket(Integer ticketId, Integer loginId) {
        // 1. Find ticket
        Ticketing ticket = ticketingRepository.findById(ticketId)
                .orElseThrow(() -> new EntityNotFoundException("Ticket no encontrado"));

        // 2. Find user
        Login login = loginRepository.findById(loginId)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

//        // 3. Find relation between ticket and user
//        TicketRelUsuario rel = ticketRelUsuarioRepository
//                .findByIdTicketingAndLoginIdlogin(ticket, login)
//                .orElseThrow(() -> new EntityNotFoundException("Relación Ticket-Usuario no encontrada"));

//        // 4. Mark the relation as inactive
//        rel.setEstado(false);
//        ticketRelUsuarioRepository.save(rel);

        // 5. Close the ticket
        ticket.setFechaFin(LocalDate.now()); // Or create a separate 'fechaCierre'
        // Optional: ticket.setMotivoCierre("Usuario no responde"); // If you add this field
        ticketingRepository.save(ticket);

        // 6. Return updated DTO
        return convertToDto(ticket);
    }




    // delete a ticket
    @Override
    public void delete(Integer id) {
        Ticketing ticket = ticketingRepository.findActiveById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket no encontrado"));

        ticket.setEliminacion(LocalDate.now());
        ticketingRepository.save(ticket);
        log.info("Ticket {} marked as deleted", id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TicketingDTO> findByAprobado(Boolean aprobado, Pageable pageable) {
        return ticketingRepository.findByAprobadoAndEliminacionIsNull(aprobado, pageable)
                .map(this::convertToDto);
    }

    private TicketingDTO convertToDto(Ticketing ticket) {
        TicketingDTO dto = modelMapper.map(ticket, TicketingDTO.class);



        if (ticket.getUsuarioCreador() != null) {
            dto.setUsuarioCreador(ticket.getUsuarioCreador().getEmailPrimario());
        }

        if (ticket.getUsuarioAprobador() != null) {
            dto.setUsuarioAprobador(ticket.getUsuarioAprobador().getEmailPrimario());
        }

        return dto;
    }

//    public TicketRelUsuarioRepository getTicketRelUsuarioRepository() {
//        return ticketRelUsuarioRepository;
//    }

    // Custom exception class
    public static class ResourceNotFoundException extends RuntimeException {
        public ResourceNotFoundException(String message) {
            super(message);
        }
    }
}

//// constructor 1
//    public TicketingService(TicketingRepository ticketingRepository,
//                            PrioridadesRepository prioridadesRepository,
//                            LoginRepository loginRepository,
//                            ModelMapper modelMapper) {
//        this.ticketingRepository = ticketingRepository;
//        this.prioridadesRepository = prioridadesRepository;
//        this.loginRepository = loginRepository;
//        this.modelMapper = modelMapper;
//    }

//    @Override
//    @Transactional
//    public TicketingDTO create(TicketingDTO dto) {
//        if (dto.getPrioridad() == null) {
//            throw new ResourceNotFoundException("Prioridad no especificada");
//        }
//// no es un enum por tema de preferncies si es un enum you can add .name () metohd
//        Prioridades prioridad = (Prioridades) prioridadesRepository.findByPrioridadesEnum(dto.getPrioridad())
//                .orElseThrow(() -> new ResourceNotFoundException("Prioridad no encontrada"));
//
//        Ticketing ticket = modelMapper.map(dto, Ticketing.class);
//        ticket.setIdPrior(prioridad);
//        ticket.setFechaInicio(LocalDate.now());
//        ticket.setAprobado(false);
//        ticket.setModificacion(null);
//        ticket.setEliminacion(null);
//        ticket.setCreatedBy(null);
//        return convertToDto(ticketingRepository.save(ticket));
//    }
//
//    @Override
//    @Transactional(readOnly = true)
//    public TicketingDTO findById(Integer id) {
//        return ticketingRepository.findActiveById(id)
//                .map(this::convertToDto)
//                .orElseThrow(() -> new ResourceNotFoundException("Ticket no encontrado"));
//    }
//
//    @Override
//    @Transactional(readOnly = true)
//    public List<TicketingDTO> findAll() {
//        return ticketingRepository.findAll().stream()
//                .map(this::convertToDto)
//                .toList();
//    }
//
//    @Override
//    @Transactional
//    public TicketingDTO update(TicketingDTO dto) {
//        Ticketing existing = ticketingRepository.findActiveById(dto.getId())
//                .orElseThrow(() -> new ResourceNotFoundException("Ticket no encontrado"));
//
//        if (dto.getPrioridad() != null) {
//            Prioridades prioridad = (Prioridades) prioridadesRepository.findByPrioridadesEnum(dto.getPrioridad())
//                    .orElseThrow(() -> new ResourceNotFoundException("Prioridad no encontrada"));
//            existing.setIdPrior(prioridad);
//        }
//
//        modelMapper.map(dto, existing);
//        existing.setModificacion(LocalDate.now());
//
//        return convertToDto(ticketingRepository.save(existing));
//    }
//
//
//
//    @Override
//    @Transactional
//    public TicketingDTO createTicket(TicketingDTO dto, Login usuario) {
//        Ticketing ticket = modelMapper.map(dto, Ticketing.class);
//        ticket.setCreatedBy(usuario);
//        return convertToDto(ticketingRepository.save(ticket));
//    }
//
//    @Override
//    @Transactional
//    public TicketingDTO updateTicket(Integer id, TicketingDTO ticketDTO){
//        ticketDTO.setId(id);
//        return update(ticketDTO);
//    }
//
//
//
//    @Transactional
//    public void approveTicket(Integer id, String email) {
//        Ticketing ticket = ticketingRepository.findActiveById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Ticket no encontrado"));
//
//        Login aprobador = loginRepository.getLoginByEmailPrimario(email)
//                .orElseThrow(() -> new ResourceNotFoundException("Usuario aprobador no encontrado"));
//
//        ticket.setAprobado(true);
//        ticket.setUsuarioAprobador(aprobador);
//        ticket.setModificacion(LocalDate.now());
//        ticketingRepository.save(ticket);
//    }
//
//    @Override
//    @Transactional
//    public void delete(Integer id) {
//        Ticketing ticket = ticketingRepository.findActiveById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Ticket no encontrado"));
//        ticket.setEliminacion(LocalDate.now());
//        ticketingRepository.save(ticket);
//    }
//
//    @Transactional(readOnly = true)
//    public List<TicketingDTO> findByAprobado(Boolean aprobado) {
//        return ticketingRepository.findByAprobado(aprobado).stream()
//                .map(this::convertToDto)
//                .toList();
//    }
//
//    @Override
//    @Transactional(readOnly = true)
//    public Page<TicketingDTO> findByAprobado(Boolean aprobado, Pageable pageable) {
//        return ticketingRepository.findFilteredTickets(aprobado, null, pageable)
//                .map(this::convertToDto);
//    }
//
//    private TicketingDTO convertToDto(Ticketing ticket) {
//        TicketingDTO dto = modelMapper.map(ticket, TicketingDTO.class);
//        if (ticket.getIdPrior() != null) {
//            dto.setPrioridad(ticket.getIdPrior().getPrioridadesEnum());
//        }
//        return dto;
//    }
//
//    static class ResourceNotFoundException extends RuntimeException {
//        public ResourceNotFoundException(String message) {
//            super(message);
//        }
//    }
//}
