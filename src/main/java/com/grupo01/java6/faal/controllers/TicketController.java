package com.grupo01.java6.faal.controllers;

import com.grupo01.java6.faal.dtos.TicketingDTO;
import com.grupo01.java6.faal.entities.Login;
import com.grupo01.java6.faal.services.impl.TicketSrvImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.web.exchanges.HttpExchange;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Controller
@RequestMapping("/ticket")
@Slf4j

public class TicketController {
    private final TicketSrvImpl ticketingService;

    @GetMapping("")
    public String ticket() {
        return "ticket";
    }
    public TicketController(TicketSrvImpl ticketingService) {
        this.ticketingService = ticketingService;
    }
    // crear el ticket
    @PostMapping
    public ResponseEntity<TicketingDTO> create(@RequestBody TicketingDTO dto, @RequestParam Login usuario) {
        TicketingDTO created = ticketingService.createTicket(dto, usuario);
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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        ticketingService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/approve/{id}")
    public String approveTicket(@PathVariable Integer id, HttpExchange.Principal principal) {
        String email = principal.getName(); // Retrieves the logged-in user's email
        ticketingService.approveTicket(id, email);
        return "redirect:/tickets"; // Redirect to ticket list or confirmation page
    }
    @GetMapping("/aprobado")
    public ResponseEntity<Page<TicketingDTO>> findByAprobado(@RequestParam Boolean aprobado, Pageable pageable) {
        return ResponseEntity.ok(ticketingService.findByAprobado(aprobado, pageable));
    }
}
