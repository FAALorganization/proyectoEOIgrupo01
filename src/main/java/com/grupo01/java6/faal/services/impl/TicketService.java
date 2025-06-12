package com.grupo01.java6.faal.services.impl;

import com.grupo01.java6.faal.dtos.CreateTicketDTO;
import com.grupo01.java6.faal.dtos.TicketingDTO;
import com.grupo01.java6.faal.entities.Login;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TicketService {
//    TicketingDTO create(TicketingDTO dto);
//    TicketingDTO findById(Integer id);
//    List<TicketingDTO> findAll();
//    TicketingDTO update(TicketingDTO dto);
//    TicketingDTO createTicket(TicketingDTO ticketDTO, Login usuario);
//    TicketingDTO updateTicket(Integer id, TicketingDTO ticketDTO);
//    void approveTicket(Integer id,String email);
//    void delete(Integer id);
//    Page<TicketingDTO> findByAprobado(Boolean aprobado, Pageable pageable);
TicketingDTO create(TicketingDTO dto);
    TicketingDTO findById(Integer id);
    List<TicketingDTO> findAll();
    TicketingDTO update(TicketingDTO dto);
    TicketingDTO createTicket(TicketingDTO ticketDTO, String userEmail);
    TicketingDTO createTicketFromForm(CreateTicketDTO createDto, String userEmail);
    TicketingDTO updateTicket(Integer id, TicketingDTO ticketDTO);
    void approveTicket(Integer id, String approverEmail);
    void rejectTicket(Integer id, String approverEmail);
    void delete(Integer id);
    Page<TicketingDTO> findByAprobado(Boolean aprobado, Pageable pageable);
    List<TicketingDTO> findUserTickets(String userEmail);
}
