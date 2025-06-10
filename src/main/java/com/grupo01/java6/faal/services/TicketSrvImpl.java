package com.grupo01.java6.faal.services;

import com.grupo01.java6.faal.dtos.TicketingDTO;

import java.util.List;

public interface TicketSrvImpl {
    TicketingDTO create(TicketingDTO dto);
    TicketingDTO findById(Integer id);
    List<TicketingDTO> findAll();
    TicketingDTO update(TicketingDTO dto);
    List<TicketingDTO> getAllTickets();

}
