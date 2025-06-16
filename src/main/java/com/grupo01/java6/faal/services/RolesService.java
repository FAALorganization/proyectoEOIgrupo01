package com.grupo01.java6.faal.services;

import com.grupo01.java6.faal.repositories.RolesRepository;
import org.springframework.stereotype.Service;

@Service
public class RolesService {
    private final RolesRepository rolesRepository;

    public RolesService(RolesRepository rolesRepository) {
        this.rolesRepository = rolesRepository;
    }

    public String findRolById(Integer id) {
        return rolesRepository.findById(id).get().getDescripcion();
    }
}
