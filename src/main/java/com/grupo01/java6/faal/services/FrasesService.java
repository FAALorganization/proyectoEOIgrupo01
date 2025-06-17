package com.grupo01.java6.faal.services;

import com.grupo01.java6.faal.entities.Frases;
import com.grupo01.java6.faal.repositories.FrasesRepository;
import org.springframework.stereotype.Service;

@Service
public class FrasesService {
    private final FrasesRepository frasesRepository;

    public FrasesService(FrasesRepository frasesRepository) {
        this.frasesRepository = frasesRepository;
    }

    public Frases getFraseById(Integer id) {
        return frasesRepository.findById(id).orElse(null);
    }
}
