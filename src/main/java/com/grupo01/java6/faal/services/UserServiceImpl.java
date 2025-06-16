//package com.grupo01.java6.faal.services;
//
//import com.grupo01.java6.faal.entities.Login;
//import com.grupo01.java6.faal.entities.Roles;
//import com.grupo01.java6.faal.entities.TicketRelUsuario;
//import com.grupo01.java6.faal.repositories.LoginRepository;
//import com.grupo01.java6.faal.repositories.TicketRelUsuarioRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.Optional;
//
//@Service
//@RequiredArgsConstructor
//@Transactional
//public class UserServiceImpl {
//    private final LoginRepository loginRepository;
//    private final TicketRelUsuarioRepository ticketRelRepository;
//    private final PasswordEncoder passwordEncoder;
//    public List<Login> findAllAdmins() {
//        return loginRepository.findByRolesContaining("ROLE_ADMIN");
//    }
//    public List<Login> findAllAgents() {
//        return loginRepository.findByRolesContaining("ROLE_AGENT");
//    }


//    public boolean esAdministrador(Login login) {
//        return login.getRoles().contains("ROLE_ADMIN");
//    }

//    public Login getCurrentUser(Authentication authentication) {
//        String email = authentication.getName();
//        return (Login) loginRepository.findByEmailPrimario(email)
//                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
//    }

//}
