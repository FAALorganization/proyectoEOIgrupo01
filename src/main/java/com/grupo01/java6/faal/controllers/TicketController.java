package com.grupo01.java6.faal.controllers;

import com.grupo01.java6.faal.dtos.TicketingDTO;
import com.grupo01.java6.faal.services.TicketingService;
import com.grupo01.java6.faal.services.impl.TicketSrvImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/ticket")

public class TicketController {
    private final TicketSrvImpl ticketingService;

    public TicketController(TicketSrvImpl ticketingService) {
        this.ticketingService = ticketingService;
    }
    // crear el ticket
    @PostMapping
    public ResponseEntity<TicketingDTO> create(@RequestBody TicketingDTO dto, @RequestParam String username) {
        TicketingDTO created = TicketingService.createTicket(dto, username);
        return ResponseEntity.ok(created);
    }
    @GetMapping("/{id}")
    public ResponseEntity<TicketingDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(ticketingService.findById(id));
    }
    @GetMapping
    public ResponseEntity<List<TicketingDTO>> findAll() {
        return ResponseEntity.ok(ticketingService.findAll());
    }
    @PutMapping("/{id}/approve")
    public ResponseEntity<Void> approve(@PathVariable Integer id) {
        ticketingService.approveTicket(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        ticketingService.delete(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/aprobado")
    public ResponseEntity<Page<TicketingDTO>> findByAprobado(@RequestParam Boolean aprobado, Pageable pageable) {
        return ResponseEntity.ok(ticketingService.findByAprobado(aprobado, pageable));
    }
}
