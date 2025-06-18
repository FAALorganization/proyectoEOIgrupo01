package com.grupo01.java6.faal.services;

import com.grupo01.java6.faal.entities.Sla;
import com.grupo01.java6.faal.entities.Ticketing;
import com.grupo01.java6.faal.repositories.SLATrackingRepository;
import com.grupo01.java6.faal.repositories.TicketingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class SlaTrackingServiceImpl implements SlaTrackingService {

    private final SLATrackingRepository slaRepository;
    private final TicketingRepository ticketRepository;

    @Override
    public void pauseSla(Integer ticketId, String modifiedBy) {
        Sla sla = slaRepository.findByTicketId(ticketId)
                .orElseThrow(() -> new RuntimeException("SLA not found"));

        if (sla.isPaused()) {
            throw new RuntimeException("SLA is already paused");
        }

        LocalDateTime now = LocalDateTime.now();
        sla.setPauseTime(now);
        sla.setFrozenRemainingTime(Duration.between(now, sla.getDeadline()));
        sla.setPaused(true);
        sla.setModifiedBy(modifiedBy);

        slaRepository.save(sla);
    }

    @Override
    public void resumeSla(Integer ticketId, String modifiedBy) {
        Sla sla = slaRepository.findByTicketId(ticketId)
                .orElseThrow(() -> new RuntimeException("SLA not found"));

        if (!sla.isPaused()) {
            throw new RuntimeException("SLA is not paused");
        }

        LocalDateTime now = LocalDateTime.now();
        Duration pausedDuration = Duration.between(sla.getPauseTime(), now);

        sla.setPausedDuration(sla.getPausedDuration().plus(pausedDuration));
        sla.setDeadline(sla.getDeadline().plus(pausedDuration));
        sla.setPaused(false);
        sla.setPauseTime(null);
        sla.setFrozenRemainingTime(null);
        sla.setModifiedBy(modifiedBy);

        slaRepository.save(sla);
    }

    @Override
    public Optional<Sla> getSlaStatus(Integer ticketId) {
        return Optional.empty();
    }

    @Override
    public Optional<Duration> calculateRemainingTime(Integer ticketId) {
        return Optional.empty();
    }
}