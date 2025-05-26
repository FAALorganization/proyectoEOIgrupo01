package com.grupo01.java6.faal.repositories;


import com.grupo01.java6.faal.entities.Login;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LoginRepository extends CrudRepository<Login, Integer> {
    List<Login> getLoginByEmailPrimario(String emailPrimario);
}
