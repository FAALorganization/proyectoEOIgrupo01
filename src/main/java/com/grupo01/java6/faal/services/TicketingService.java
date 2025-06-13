//package com.grupo01.java6.faal.services;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//
//import com.grupo01.java6.faal.dtos.CreateTicketDTO;
//import com.grupo01.java6.faal.dtos.TicketingDTO;
//import com.grupo01.java6.faal.entities.Login;
//import com.grupo01.java6.faal.entities.Prioridades;
//import com.grupo01.java6.faal.entities.Ticketing;
//import com.grupo01.java6.faal.repositories.LoginRepository;
//import com.grupo01.java6.faal.repositories.PrioridadesRepository;
//import com.grupo01.java6.faal.repositories.TicketingRepository;
//import com.grupo01.java6.faal.services.impl.TicketService;
//import org.modelmapper.ModelMapper;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.rest.webmvc.ResourceNotFoundException;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.LocalDate;
//import java.util.List;
//
//@RequiredArgsConstructor
//@Slf4j
//@Transactional
//@Service
//public class TicketingService implements TicketService {
//    private final LoginRepository loginRepository;
//    ;
//    private final TicketingRepository ticketingRepository;
//    private final PrioridadesRepository prioridadesRepository;
//    private final ModelMapper modelMapper;
//
//    @Override
//    public TicketingDTO create(TicketingDTO dto) {
//        log.info("Creating ticket: {}", dto.getNombre());
//
//        Prioridades prioridad = (Prioridades) prioridadesRepository.findByPrioridadesEnum(dto.getPrioridad())
//                .orElseThrow(() -> new ResourceNotFoundException("Prioridad no encontrada: " + dto.getPrioridad()));
//
//        Ticketing ticket = modelMapper.map(dto, Ticketing.class);
//        ticket.setIdPrior(prioridad);
//        ticket.setFechaInicio(LocalDate.now());
//        ticket.setAprobado(false);
//
//        Ticketing saved = ticketingRepository.save(ticket);
//        log.info("Ticket created successfully with ID: {}", saved.getId());
//
//        return convertToDto(saved);
//    }
//
//    @Override
//    public TicketingDTO createTicket(TicketingDTO dto, String userEmail) {
//        log.info("Creating ticket for user: {}", userEmail);
//
//        Login usuario = loginRepository.getLoginByEmailPrimario(userEmail)
//                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado: " + userEmail));
//
//        Prioridades prioridad = (Prioridades) prioridadesRepository.findByPrioridadesEnum(dto.getPrioridad())
//                .orElseThrow(() -> new ResourceNotFoundException("Prioridad no encontrada: " + dto.getPrioridad()));
//
//        Ticketing ticket = modelMapper.map(dto, Ticketing.class);
//        ticket.setIdPrior(prioridad);
//        ticket.setCreatedBy(usuario);
//        ticket.setFechaInicio(LocalDate.now());
//        ticket.setAprobado(false);
//
//        Ticketing saved = ticketingRepository.save(ticket);
//        log.info("Ticket created successfully for user {} with ID: {}", userEmail, saved.getId());
//
//        return convertToDto(saved);
//    }
//
//    // Create from form data
//    public TicketingDTO createTicketFromForm(CreateTicketDTO createDto, String userEmail) {
//        TicketingDTO dto = modelMapper.map(createDto, TicketingDTO.class);
//        return createTicket(dto, userEmail);
//    }
//
//    @Override
//    @Transactional(readOnly = true)
//    public TicketingDTO findById(Integer id) {
//        return ticketingRepository.findActiveById(id)
//                .map(this::convertToDto)
//                .orElseThrow(() -> new ResourceNotFoundException("Ticket no encontrado con ID: " + id));
//    }
//
//    @Override
//    @Transactional(readOnly = true)
//    public List<TicketingDTO> findAll() {
//        return ticketingRepository.findAllActive().stream()
//                .map(this::convertToDto)
//                .toList();
//    }
//
//    @Transactional(readOnly = true)
//    public List<TicketingDTO> findUserTickets(String userEmail) {
//        return ticketingRepository.findByCreatorEmail(userEmail).stream()
//                .map(this::convertToDto)
//                .toList();
//    }
//
//    @Override
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
//        // Update fields selectively
//        if (dto.getNombre() != null) existing.setNombre(dto.getNombre());
//        if (dto.getAsunto() != null) existing.setAsunto(dto.getAsunto());
//        if (dto.getDescripcion() != null) existing.setDescripcion(dto.getDescripcion());
//        if (dto.getTipoTicket() != null) existing.setTipoTicket(dto.getTipoTicket());
//        if (dto.getFechaFin() != null) existing.setFechaFin(dto.getFechaFin());
//
//        return convertToDto(ticketingRepository.save(existing));
//    }
//
//    @Override
//    public TicketingDTO updateTicket(Integer id, TicketingDTO ticketDTO) {
//        ticketDTO.setId(id);
//        return update(ticketDTO);
//    }
//
//    @Override
//    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
//    public void approveTicket(Integer id, String approveEmail) {
//        log.info("Approving ticket {} by user {}", id, approveEmail);
//
//        Ticketing ticket = ticketingRepository.findActiveById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Ticket no encontrado"));
//
//        Login aprobador = loginRepository.getLoginByEmailPrimario(approveEmail)
//                .orElseThrow(() -> new ResourceNotFoundException("Usuario aprobador no encontrado"));
//
//        ticket.setAprobado(true);
//        ticket.setUsuarioAprobador(aprobador);
//        ticket.setModificacion(LocalDate.now());
//
//        ticketingRepository.save(ticket);
//        log.info("Ticket {} approved successfully by {}", id, approveEmail);
//    }
//
//    public void rejectTicket(Integer id, String approverEmail) {
//        log.info("Rejecting ticket {} by user {}", id, approverEmail);
//
//        Ticketing ticket = ticketingRepository.findActiveById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Ticket no encontrado"));
//
//        Login aprobador = loginRepository.getLoginByEmailPrimario(approverEmail)
//                .orElseThrow(() -> new ResourceNotFoundException("Usuario aprobador no encontrado"));
//
//        ticket.setAprobado(false);
//        ticket.setUsuarioAprobador(aprobador);
//        ticket.setModificacion(LocalDate.now());
//
//        ticketingRepository.save(ticket);
//        log.info("Ticket {} rejected by {}", id, approverEmail);
//    }
//
//    @Override
//    public void delete(Integer id) {
//        Ticketing ticket = ticketingRepository.findActiveById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Ticket no encontrado"));
//
//        ticket.setEliminacion(LocalDate.now());
//        ticketingRepository.save(ticket);
//        log.info("Ticket {} marked as deleted", id);
//    }
//
//    @Override
//    @Transactional(readOnly = true)
//    public Page<TicketingDTO> findByAprobado(Boolean aprobado, Pageable pageable) {
//        return ticketingRepository.findByAprobadoAndEliminacionIsNull(aprobado, pageable)
//                .map(this::convertToDto);
//    }
//
//    private TicketingDTO convertToDto(Ticketing ticket) {
//        TicketingDTO dto = modelMapper.map(ticket, TicketingDTO.class);
//
//        if (ticket.getIdPrior() != null) {
//            dto.setPrioridad(ticket.getIdPrior().getPrioridadesEnum());
//        }
//
//        if (ticket.getUsuarioCreador() != null) {
//            dto.setUsuarioCreador(ticket.getUsuarioCreador().getEmailPrimario());
//        }
//
//        if (ticket.getUsuarioAprobador() != null) {
//            dto.setUsuarioAprobador(ticket.getUsuarioAprobador().getEmailPrimario());
//        }
//
//        return dto;
//    }
//
//    // Custom exception class
//    public static class ResourceNotFoundException extends RuntimeException {
//        public ResourceNotFoundException(String message) {
//            super(message);
//        }
//    }
//}
//
////// constructor 1
////    public TicketingService(TicketingRepository ticketingRepository,
////                            PrioridadesRepository prioridadesRepository,
////                            LoginRepository loginRepository,
////                            ModelMapper modelMapper) {
////        this.ticketingRepository = ticketingRepository;
////        this.prioridadesRepository = prioridadesRepository;
////        this.loginRepository = loginRepository;
////        this.modelMapper = modelMapper;
////    }
//
////    @Override
////    @Transactional
////    public TicketingDTO create(TicketingDTO dto) {
////        if (dto.getPrioridad() == null) {
////            throw new ResourceNotFoundException("Prioridad no especificada");
////        }
////// no es un enum por tema de preferncies si es un enum you can add .name () metohd
////        Prioridades prioridad = (Prioridades) prioridadesRepository.findByPrioridadesEnum(dto.getPrioridad())
////                .orElseThrow(() -> new ResourceNotFoundException("Prioridad no encontrada"));
////
////        Ticketing ticket = modelMapper.map(dto, Ticketing.class);
////        ticket.setIdPrior(prioridad);
////        ticket.setFechaInicio(LocalDate.now());
////        ticket.setAprobado(false);
////        ticket.setModificacion(null);
////        ticket.setEliminacion(null);
////        ticket.setCreatedBy(null);
////        return convertToDto(ticketingRepository.save(ticket));
////    }
////
////    @Override
////    @Transactional(readOnly = true)
////    public TicketingDTO findById(Integer id) {
////        return ticketingRepository.findActiveById(id)
////                .map(this::convertToDto)
////                .orElseThrow(() -> new ResourceNotFoundException("Ticket no encontrado"));
////    }
////
////    @Override
////    @Transactional(readOnly = true)
////    public List<TicketingDTO> findAll() {
////        return ticketingRepository.findAll().stream()
////                .map(this::convertToDto)
////                .toList();
////    }
////
////    @Override
////    @Transactional
////    public TicketingDTO update(TicketingDTO dto) {
////        Ticketing existing = ticketingRepository.findActiveById(dto.getId())
////                .orElseThrow(() -> new ResourceNotFoundException("Ticket no encontrado"));
////
////        if (dto.getPrioridad() != null) {
////            Prioridades prioridad = (Prioridades) prioridadesRepository.findByPrioridadesEnum(dto.getPrioridad())
////                    .orElseThrow(() -> new ResourceNotFoundException("Prioridad no encontrada"));
////            existing.setIdPrior(prioridad);
////        }
////
////        modelMapper.map(dto, existing);
////        existing.setModificacion(LocalDate.now());
////
////        return convertToDto(ticketingRepository.save(existing));
////    }
////
////
////
////    @Override
////    @Transactional
////    public TicketingDTO createTicket(TicketingDTO dto, Login usuario) {
////        Ticketing ticket = modelMapper.map(dto, Ticketing.class);
////        ticket.setCreatedBy(usuario);
////        return convertToDto(ticketingRepository.save(ticket));
////    }
////
////    @Override
////    @Transactional
////    public TicketingDTO updateTicket(Integer id, TicketingDTO ticketDTO){
////        ticketDTO.setId(id);
////        return update(ticketDTO);
////    }
////
////
////
////    @Transactional
////    public void approveTicket(Integer id, String email) {
////        Ticketing ticket = ticketingRepository.findActiveById(id)
////                .orElseThrow(() -> new ResourceNotFoundException("Ticket no encontrado"));
////
////        Login aprobador = loginRepository.getLoginByEmailPrimario(email)
////                .orElseThrow(() -> new ResourceNotFoundException("Usuario aprobador no encontrado"));
////
////        ticket.setAprobado(true);
////        ticket.setUsuarioAprobador(aprobador);
////        ticket.setModificacion(LocalDate.now());
////        ticketingRepository.save(ticket);
////    }
////
////    @Override
////    @Transactional
////    public void delete(Integer id) {
////        Ticketing ticket = ticketingRepository.findActiveById(id)
////                .orElseThrow(() -> new ResourceNotFoundException("Ticket no encontrado"));
////        ticket.setEliminacion(LocalDate.now());
////        ticketingRepository.save(ticket);
////    }
////
////    @Transactional(readOnly = true)
////    public List<TicketingDTO> findByAprobado(Boolean aprobado) {
////        return ticketingRepository.findByAprobado(aprobado).stream()
////                .map(this::convertToDto)
////                .toList();
////    }
////
////    @Override
////    @Transactional(readOnly = true)
////    public Page<TicketingDTO> findByAprobado(Boolean aprobado, Pageable pageable) {
////        return ticketingRepository.findFilteredTickets(aprobado, null, pageable)
////                .map(this::convertToDto);
////    }
////
////    private TicketingDTO convertToDto(Ticketing ticket) {
////        TicketingDTO dto = modelMapper.map(ticket, TicketingDTO.class);
////        if (ticket.getIdPrior() != null) {
////            dto.setPrioridad(ticket.getIdPrior().getPrioridadesEnum());
////        }
////        return dto;
////    }
////
////    static class ResourceNotFoundException extends RuntimeException {
////        public ResourceNotFoundException(String message) {
////            super(message);
////        }
////    }
////}
