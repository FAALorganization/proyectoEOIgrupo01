package com.grupo01.java6.faal.config;

import com.grupo01.java6.faal.entities.Login;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


public class UserDetailsImpl implements UserDetails {

    private final Login login;

    public UserDetailsImpl(Login login) {
        this.login = login;
    }

//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return login.getRoles().stream()
//                .map(role -> new SimpleGrantedAuthority(role.getNombre()))
//                .collect(Collectors.toList());
//    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return login.getPassword();
    }

    @Override
    public String getUsername() {
        return login.getEmailPrimario(); // Cambia si usas otro campo como "username"
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true; // Puedes vincularlo a algún campo en tu entidad `Login`
    }

    public Login getLogin() {
        return login;
    }
}

