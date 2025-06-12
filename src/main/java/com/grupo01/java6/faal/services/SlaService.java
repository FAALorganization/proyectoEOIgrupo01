package com.grupo01.java6.faal.services;
import com.grupo01.java6.faal.entities.Sla;
import com.grupo01.java6.faal.entities.Prioridades;
import com.grupo01.java6.faal.repositories.SlaRepository;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import com.grupo01.java6.faal.repositories.SlaRepository;

public class SlaService {
    private final SlaRepository slaRepository;
    private final Map<Long, SlaTimer> activeSlaTimers = new ConcurrentHashMap<>();

    public SlaService(SlaRepository slaRepository) {
        this.slaRepository = slaRepository;
    }
}
