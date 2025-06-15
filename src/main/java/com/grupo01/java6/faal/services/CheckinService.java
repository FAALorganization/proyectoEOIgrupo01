package com.grupo01.java6.faal.services;

import com.grupo01.java6.faal.entities.Checkin;
import com.grupo01.java6.faal.repositories.CheckinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CheckinService {

    @Autowired
    private CheckinRepository checkinRepository;

    public List<Checkin> findAll() {
        return checkinRepository.findAll();
    }
}