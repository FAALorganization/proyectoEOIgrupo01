package com.grupo01.java6.faal.services;

import com.grupo01.java6.faal.dtos.EmpleadoConAusenciasDTO;
import com.grupo01.java6.faal.dtos.NombreConAusenciasDTO;
import com.grupo01.java6.faal.dtos.NombreDTO;
import com.grupo01.java6.faal.dtos.UsuarioDTO;
import com.grupo01.java6.faal.entities.Detallesdeusuario;
import com.grupo01.java6.faal.entities.Login;
import com.grupo01.java6.faal.repositories.LoginRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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
            empleado.agregarAusencia(dto.getFechaInicio(), dto.getFechaFin(), dto.isAprobado(), dto.getIdAusencia(), dto.getJustificacion());
        }

        return new ArrayList<>(map.values());
    }

    public Login getUserByEmail(String email) {
        return loginRepository.getLoginByEmailPrimario(email).orElse(null);
    }

    public Login getUserById(Integer id) {
        return loginRepository.findById(id).orElse(null);
    }

    public List<UsuarioDTO> getUsuariosDTOConRolUsuarioOVisitante() {
        List<Login> logins = loginRepository.findAllWithRoleUsuarioOrVisitante();
        return logins.stream()
                .map(login -> new UsuarioDTO(
                        login.getId(),
                        login.getIdDetallesDeUsuario().getNombre(),
                        login.getEmailPrimario()
                ))
                .toList();
    }

    public Login getUserBy_Id(Integer id) {
        return loginRepository.findById(id).orElse(null);
    }

    public void guardarLogin(Login login) {
        loginRepository.save(login); //metodo para guardar un Login
    }

    public Login obtenerPorDetallesUsuario(Detallesdeusuario detallesdeusuario) {
        return loginRepository.findByIdDetallesDeUsuario(detallesdeusuario).orElse(null);

    }

    public Login obtenerPorId(Integer id) {
        return loginRepository.findById(id).orElse(null);

    }
}



