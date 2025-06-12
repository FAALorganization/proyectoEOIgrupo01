package com.grupo01.java6.faal.services;

import com.grupo01.java6.faal.entities.Login;
import com.grupo01.java6.faal.entities.Roles;
import com.grupo01.java6.faal.repositories.LoginRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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
// Spring expect roles to be prefixes with Role_
        String[] roles = usuario.getRoles().stream()
                .map(Roles::getNombre)
                //.map(nombre -> "ROLE_" + nombre.toUpperCase())
                .toArray(String[]::new);

        return User.builder()
                .username(usuario.getEmailPrimario())
                .password(usuario.getPassword())
                .roles(roles)
                .build();
    }

    @PreAuthorize("#email == authentication.principal.username")
    public Login modifyUser(String email) {
        return loginRepository.getLoginByEmailPrimario(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
    }
}
