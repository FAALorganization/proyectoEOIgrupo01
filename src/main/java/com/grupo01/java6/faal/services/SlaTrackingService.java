package com.grupo01.java6.faal.services;

import com.grupo01.java6.faal.dtos.TicketingDTO;
import com.grupo01.java6.faal.entities.Sla;

import java.time.Duration;
import java.util.Optional;

public interface SlaTrackingService {
    /**
     * Pauses the SLA tracking for a given ticket
     * @param ticketId ID of the ticket to pause SLA for
     * @param modifiedBy Username of who initiated the pause
     * @throws RuntimeException if ticket is closed, not found, or SLA already paused
     */
    void pauseSla(Integer ticketId, String modifiedBy);

    /**
     * Resumes the SLA tracking for a given ticket
     * @param ticketId ID of the ticket to resume SLA for
     * @param modifiedBy Username of who initiated the resume
     * @throws RuntimeException if ticket is closed, not found, or SLA not paused
     */
    void resumeSla(Integer ticketId, String modifiedBy);

    /**
     * Gets the current SLA status for a ticket
     * @param ticketId ID of the ticket
     * @return Sla object with current status
     */
    Optional<Sla> getSlaStatus(Integer ticketId);

    /**
     * Calculates remaining time for SLA
     * @param ticketId ID of the ticket
     * @return Duration remaining until SLA breach
     */
    Optional<Duration> calculateRemainingTime(Integer ticketId);
}