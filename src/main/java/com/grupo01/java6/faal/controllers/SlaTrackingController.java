package com.grupo01.java6.faal.controllers;

import com.grupo01.java6.faal.services.SlaTrackingService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sla")
public class SlaTrackingController {

    private final SlaTrackingService slaTrackingService;

    public SlaTrackingController(SlaTrackingService slaTrackingService) {
        this.slaTrackingService = slaTrackingService;
    }

    @PostMapping("/{ticketId}/pause")
    public ResponseEntity<?> pauseSla(
            @PathVariable Integer ticketId,
            Authentication authentication) {

        try {
            String modifiedBy = authentication.getName();
            slaTrackingService.pauseSla(ticketId, modifiedBy);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{ticketId}/resume")
    public ResponseEntity<?> resumeSla(
            @PathVariable Integer ticketId,
            Authentication authentication) {

        try {
            String modifiedBy = authentication.getName();
            slaTrackingService.resumeSla(ticketId, modifiedBy);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
