package com.grupo01.java6.faal.repositories;

import com.grupo01.java6.faal.entities.ChatAbierto;
import com.grupo01.java6.faal.entities.Login;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatAbiertoRepository extends JpaRepository<ChatAbierto, Long> {

    @Query("SELECT c FROM ChatAbierto c WHERE (c.usuarioA = :login OR c.usuarioB = :login) AND c.activo = true")
    List<ChatAbierto> findChatsActivosByUsuario(@Param("login") Login login);
}

