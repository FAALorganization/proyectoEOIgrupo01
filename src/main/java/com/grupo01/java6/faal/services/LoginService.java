package com.grupo01.java6.faal.services;

import com.grupo01.java6.faal.dtos.EmpleadoConAusenciasDTO;
import com.grupo01.java6.faal.dtos.NombreConAusenciasDTO;
import com.grupo01.java6.faal.dtos.NombreDTO;
import com.grupo01.java6.faal.dtos.UsuarioDTO;
import com.grupo01.java6.faal.entities.Detallesdeusuario;
import com.grupo01.java6.faal.entities.Login;
import com.grupo01.java6.faal.entities.Mensaje;
import com.grupo01.java6.faal.repositories.LoginRepository;
import org.springframework.stereotype.Service;
import org.hibernate.Hibernate;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    public List<Login> obtenerUsuariosSinConversacion(Login actual, List<Mensaje> recientes) {
        Set<Integer> idsConConversacion = recientes.stream()
                .flatMap(m -> Stream.of(m.getEmisor().getId(), m.getReceptor() != null ? m.getReceptor().getId() : null))
                .filter(Objects::nonNull)
                .filter(id -> !id.equals(actual.getId()))
                .collect(Collectors.toSet());

        List<Login> todos = obtenerTodosMenosActual(actual.getId());

        return todos.stream()
                .filter(u -> !idsConConversacion.contains(u.getId()))
                .collect(Collectors.toList());
    }


    public boolean tieneRolJefe(Login login) {
        return login.getRoles().stream()
                .anyMatch(rol -> rol.getDescripcion().equalsIgnoreCase("JEFE"));
    }

    // LoginService.java
    public List<Login> obtenerTodosMenosActual(Integer idActual) {
        return loginRepository.findAll().stream()
                .filter(user -> !user.getId().equals(idActual))
                .toList();
    }
    public Login obtenerPorIdConSubordinados(Integer id) {
        return loginRepository.findByIdWithSubordinados(id).orElse(null);
    }
}