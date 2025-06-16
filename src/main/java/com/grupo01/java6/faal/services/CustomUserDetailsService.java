package com.grupo01.java6.faal.services;

import com.grupo01.java6.faal.config.UserDetailsImpl;
import com.grupo01.java6.faal.entities.Login;
import com.grupo01.java6.faal.repositories.LoginRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final LoginRepository loginRepository;

    public CustomUserDetailsService(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        Login usuario = loginRepository.getLoginByEmailPrimario(correo)
                .orElseThrow(() -> new UsernameNotFoundException("Correo " + correo + " no encontrado"));

        return new UserDetailsImpl(usuario);
    }

    public Login modifyUser(Login usuario) {
        return loginRepository.getLoginByEmailPrimario(usuario.getEmailPrimario()).orElse(null);
    }
}