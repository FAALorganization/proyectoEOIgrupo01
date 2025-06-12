package com.grupo01.java6.faal.services;

import com.grupo01.java6.faal.entities.Login;
import com.grupo01.java6.faal.entities.Roles;
import com.grupo01.java6.faal.repositories.LoginRepository;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.core.userdetails.User;
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
    public UserDetails loadUserByUsername(String nombreUsuario) throws UsernameNotFoundException {
        Login usuario = loginRepository.getLoginByEmailPrimario(nombreUsuario)
                .orElseThrow(() -> new UsernameNotFoundException("Email " + nombreUsuario + " not found"));

        // Convertir Set<Roles> a String[]
        String[] roles = usuario.getRoles().stream()
                .map(Roles::getNombre)
                .map(String::toUpperCase)
                .toArray(String[]::new);

        return User.builder()
                .username(usuario.getEmailPrimario())
                .password(usuario.getPassword())
                .roles(roles)
                .build();
    }




    @PostAuthorize("#username == authentication.principal.username")
    public Login modifyUser(Login usuario) throws UsernameNotFoundException {

        Optional<Login> usuarioRecuperado = Optional.of(new Login());
        usuarioRecuperado = loginRepository.getLoginByEmailPrimario(usuario.getEmailPrimario());
        return usuarioRecuperado.orElse(null);

    }
}

