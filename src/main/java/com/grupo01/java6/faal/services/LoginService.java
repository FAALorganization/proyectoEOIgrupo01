package com.grupo01.java6.faal.services;

import com.grupo01.java6.faal.dtos.EmpleadoConAusenciasDTO;
import com.grupo01.java6.faal.dtos.NombreConAusenciasDTO;
import com.grupo01.java6.faal.dtos.NombreDTO;
import com.grupo01.java6.faal.entities.Login;
import com.grupo01.java6.faal.repositories.LoginRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class LoginService {

    private final LoginRepository loginRepository;

    public LoginService(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    public Login obtainUser(String str){
        Optional<Login> logins = loginRepository.getLoginByEmailPrimario(str);
        return logins.orElse(null);
        // Por ejemplo, devolver el email primario del primer Login
    }

    public Set<String> allUsers() {
        return loginRepository.findAllEmails();
    }


    public List<NombreDTO> obtenerCompaneros(String email) {
        return loginRepository.findCompanerosByEmail(email);
    }

    public List<EmpleadoConAusenciasDTO> obtenerCompanerosConAusenciasAgrupados(String email) {
        List<NombreConAusenciasDTO> rawList = loginRepository.obtenerCompanerosConAusencias(email);

        Map<String, EmpleadoConAusenciasDTO> map = new LinkedHashMap<>();

        for (NombreConAusenciasDTO dto : rawList) {
            String key = dto.getNombre() + " " + dto.getApellidos();

            EmpleadoConAusenciasDTO empleado = map.get(key);
            if (empleado == null) {
                empleado = new EmpleadoConAusenciasDTO(dto.getNombre(), dto.getApellidos());
                map.put(key, empleado);
            }
            empleado.agregarAusencia(dto.getFechaInicio(), dto.getFechaFin());
        }

        return new ArrayList<>(map.values());
    }

//    private Login obtenerUsuarioActual() {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if (auth != null && auth.isAuthenticated()) {
//            String username = auth.getName();
//            // Aquí debes recuperar tu Login con ese username
//            // Por ejemplo, usando un servicio LoginService:
//            return loginService.buscarPorNombreUsuario(username);
//        }
//        return null;
//    }
}