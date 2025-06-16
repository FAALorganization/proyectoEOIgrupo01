package com.grupo01.java6.faal.services;

import com.grupo01.java6.faal.entities.Prioridades;
import com.grupo01.java6.faal.repositories.PrioridadesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PriorityService {

    private final PrioridadesRepository prioridadesRepository;

    public List<String> getAllPriorityValues() {
        return prioridadesRepository.findAllPriorityValues();
    }

    public Object findAllPriorityValues() {
        return prioridadesRepository.findAll();

    }

    public String getDisplayNameForValue(String value) {
        return prioridadesRepository.findDisplayNameByValue(value.toLowerCase())
                .orElseThrow(() -> new IllegalArgumentException("Invalid priority value"));
    }

    public void validatePriority(String value) {
        if (!prioridadesRepository.existsByValue(value.toLowerCase())) {
            throw new IllegalArgumentException("Priority not found: " + value);
        }
    }



}