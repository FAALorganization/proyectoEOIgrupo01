package com.grupo01.java6.faal.services.impl;

import com.grupo01.java6.faal.dtos.TicketingDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TicketSrvImpl {
    TicketingDTO create(TicketingDTO dto);
    TicketingDTO findById(Integer id);
    List<TicketingDTO> findAll();
    TicketingDTO update(TicketingDTO dto);
    TicketingDTO createTicket(TicketingDTO ticketDTO, String username);
    TicketingDTO updateTicket(Integer id, TicketingDTO ticketDTO);
    void approveTicket(Integer id);
    void delete(Integer id);
    Page<TicketingDTO> findByAprobado(Boolean aprobado, Pageable pageable);
}
