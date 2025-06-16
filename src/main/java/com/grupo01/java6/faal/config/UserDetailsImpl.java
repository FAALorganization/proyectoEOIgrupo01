package com.grupo01.java6.faal.config;

import com.grupo01.java6.faal.entities.Login;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

public class UserDetailsImpl implements UserDetails, CredentialsContainer {

    private final Login login;

    public UserDetailsImpl(Login login) {
        this.login = login;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return login.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getNombre().toUpperCase()))
                .collect(Collectors.toSet());
    }

    @Override
    public String getPassword() {
        return login.getPassword();
    }

    @Override
    public String getUsername() {
        return login.getEmailPrimario();
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
        return true; // Puedes atarlo a algún campo como login.isActivo()
    }

    public Login getLogin() {
        return login;
    }

    @Override
    public void eraseCredentials() {
        // Podrías limpiar la contraseña si se desea
    }
}
