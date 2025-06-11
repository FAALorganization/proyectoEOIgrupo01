package com.grupo01.java6.faal.services.impl;

import com.grupo01.java6.faal.dtos.TicketingDTO;
import com.grupo01.java6.faal.entities.Login;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TicketSrvImpl {
    TicketingDTO create(TicketingDTO dto);
    TicketingDTO findById(Integer id);
    List<TicketingDTO> findAll();
    TicketingDTO update(TicketingDTO dto);
    TicketingDTO createTicket(TicketingDTO ticketDTO, Login usuario);
    TicketingDTO updateTicket(Integer id, TicketingDTO ticketDTO);
    void approveTicket(Integer id,String email);
    void delete(Integer id);
    Page<TicketingDTO> findByAprobado(Boolean aprobado, Pageable pageable);
}
