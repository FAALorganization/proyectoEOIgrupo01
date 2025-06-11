package com.grupo01.java6.faal.config;

import com.grupo01.java6.faal.entities.Login;
import com.grupo01.java6.faal.entities.Roles;
import com.grupo01.java6.faal.repositories.LoginRepository;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;


public class UserDetailsImpl implements UserDetails, CredentialsContainer {

    private final Login login;
    private final LoginRepository loginRepository;

    public UserDetailsImpl(Login login, LoginRepository loginRepository) {
        this.login = login;
        this.loginRepository = loginRepository;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return login.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getNombre().toUpperCase()))
                .collect(Collectors.toList());
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

    @Override
    public void eraseCredentials() {

    }

//    @Override //porqué sale como override??
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//
//        System.out.println("loadUserByUsername email : " + email);
//        Login  usuario = loginRepository.findUsuarioByEmailAndActiveTrue(email); //hay que creare este metodo
//        System.out.println("loadUserByUsername usuario : " + usuario.getIdDetallesDeUsuario().getNombre());
//        org.springframework.security.core.userdetails.User springUser = null;
//        Set<GrantedAuthority> ga = new HashSet<>();
//        for (Roles item : usuario.getRoles()){
//            ga.add(new SimpleGrantedAuthority(item.getNombre()));
//        }
//        springUser = new org.springframework.security.core.userdetails.User(
//                email,
//                usuario.getPassword(),
//                ga );
//        return springUser;
//
//    }
}

