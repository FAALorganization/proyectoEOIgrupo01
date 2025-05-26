package com.grupo01.java6.faal.services;

import com.grupo01.java6.faal.entities.Login;
import com.grupo01.java6.faal.repositories.LoginRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
public class LoginService {

    private final LoginRepository loginRepository;

    public LoginService(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    public Set<String> allUsers() {
        Iterable<Login> logins = loginRepository.findAll();
        Set<String> loginSet = new HashSet<>();
        logins.forEach(login -> loginSet.add(login.getEmailPrimario()));
        return loginSet;
    }

    public Set<String> allPasswords() {
        Iterable<Login> logins = loginRepository.findAll();
        Set<String> loginSet = new HashSet<>();
        logins.forEach(login -> loginSet.add(login.getPassword()));
        return loginSet;
    }

    public Login obtainUser(String str){
        List<Login> logins = loginRepository.getLoginByEmailPrimario(str);
        if (logins == null || logins.isEmpty()) {
            return null;
        }
        // Por ejemplo, devolver el email primario del primer Login
        return logins.getFirst();
    }





}
