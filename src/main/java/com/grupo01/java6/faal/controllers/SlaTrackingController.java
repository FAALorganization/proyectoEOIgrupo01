//package com.grupo01.java6.faal.controllers;
//
//import com.grupo01.java6.faal.dtos.SlaDTO;
//import com.grupo01.java6.faal.dtos.TicketingDTO;
//import com.grupo01.java6.faal.services.SlaTrackingService;
//import com.grupo01.java6.faal.services.TicketingService;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.Authentication;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/sla")
//public class SlaTrackingController {
//
//    private final SlaTrackingService slaTrackingService;
//    private final TicketingService ticketingService;
//
//    public SlaTrackingController(SlaTrackingService slaTrackingService, TicketingService ticketingService) {
//        this.slaTrackingService = slaTrackingService;
//        this.ticketingService = ticketingService;
//    }
//
//    @GetMapping("/{id}/sla")
//    public ResponseEntity<SlaDTO> getTicketSla(@PathVariable Integer id) {
//        TicketingDTO ticket = ticketingService.findById(id);
//        ticket.getSlaDTO().calculateRemaining();
//        return ResponseEntity.ok(ticket.getSlaDTO());
//    }
//
//    @PostMapping("/{id}/pause-sla")
//    public ResponseEntity<Void> pauseSla(@PathVariable Integer id) {
//        ticketingService.pauseSla(id);
//        return ResponseEntity.ok().build();
//    }
//
//    @PostMapping("/{id}/resume-sla")
//    public ResponseEntity<Void> resumeSla(@PathVariable Integer id) {
//        ticketingService.resumeSla(id);
//        return ResponseEntity.ok().build();
//    }
//}
