package com.grupo01.java6.faal.services;

import com.grupo01.java6.faal.entities.Login;
import com.grupo01.java6.faal.entities.Roles;
import com.grupo01.java6.faal.repositories.LoginRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final LoginRepository loginRepository;

    public CustomUserDetailsService(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Login usuario = loginRepository.getLoginByEmailPrimario(email)
                .orElseThrow(() -> new UsernameNotFoundException("Email " + email + " not found"));

        List<String> roles = usuario.getRoles().stream()
                .map(role -> {
                    String roleName = role.getDescripcion();
                    // Ensure proper ROLE_ prefix and uppercase format
                    return roleName.startsWith("ROLE_")
                            ? roleName.toUpperCase()
                            : "ROLE_" + roleName.toUpperCase();
                })
                .collect(Collectors.toList());

        return User.builder()
                .username(usuario.getEmailPrimario())
                .password(usuario.getPassword())
                .authorities(roles.toArray(new String[0])) // Use authorities() instead of roles()
                .build();
    }
}