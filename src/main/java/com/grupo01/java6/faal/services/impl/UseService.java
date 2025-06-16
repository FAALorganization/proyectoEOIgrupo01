package com.grupo01.java6.faal.services.impl;

import com.grupo01.java6.faal.entities.Login;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UseService {
    List<Login> findAllAdmins();
    List<Login> findAllAgents();
    Optional<Login> findByEmail(String email);
    boolean isAdminUser(String email);
    Login getCurrentUser(Authentication authentication);
    List<Login> getAvailableAgents();
    Map<String, Long> getUserTicketStats();
    Login saveUser(Login user);
    void resetPassword(String email, String newPassword);
}
