package com.grupo01.java6.faal.loaders;

import com.grupo01.java6.faal.entities.*;
import com.grupo01.java6.faal.repositories.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Configuration
@Log4j2
@Profile("produccion")
public class ProductionlDataLoader {

    private final DetallesDeUsuarioRepository detallesDeUsuarioRepository;
    private final LoginRepository loginRepository;
    private final RolesRepository rolesRepository;
    private final TiposAusenciasRepository tiposAusenciasRepository;
    private final AusenciasRepository ausenciaRepository;
    private final ChatAbiertoRepository chatAbiertoRepository;
    private final MensajeRepository mensajeRepository;
    private final PrioridadesRepository prioridadesRepository;

    public ProductionlDataLoader(DetallesDeUsuarioRepository detallesDeUsuarioRepository, LoginRepository loginRepository, RolesRepository rolesRepository, TiposAusenciasRepository tiposAusenciasRepository, AusenciasRepository ausenciaRepository, ChatAbiertoRepository chatAbiertoRepository, MensajeRepository mensajeRepository, PrioridadesRepository prioridadesRepository) {
        this.detallesDeUsuarioRepository = detallesDeUsuarioRepository;
        this.loginRepository = loginRepository;
        this.rolesRepository = rolesRepository;
        this.tiposAusenciasRepository = tiposAusenciasRepository;
        this.ausenciaRepository = ausenciaRepository;
        this.chatAbiertoRepository = chatAbiertoRepository;
        this.mensajeRepository = mensajeRepository;

        this.prioridadesRepository = prioridadesRepository;
    }


    @Profile("produccionRESETDATA")
    public void loadDataProduccion() {
        //int[] ids = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        String[] nombres = {"Ana", "General", "Luis", "Marta", "Carlos", "Elena", "Javier", "Lucía", "Pedro", "Sara"};
        String[] apellidos = {"Pérez Gómez", "general general", "Martínez López", "Sánchez Ruiz", "Díaz Torres", "García Fernández", "López Martín", "Ramírez Castro", "Moreno Gil", "Jiménez Ortiz"};
        String[] ciudades = {"Madrid", null, "Barcelona", "Valencia", "Sevilla", "Sevilla", "Valencia", "Valencia", "Barcelona", "Barcelona"};
        String[] emails = {"ana.admin@correo.com", null, "luis.jefe1@correo.com", "marta.jefe2@correo.com", "carlos.user1@correo.com", "elena.user2@correo.com", "javier.user3@correo.com", "lucia.user4@correo.com", "pedro.visit1@correo.com", "sara.visit2@correo.com"};
        String[] tlf1 = {"600111111", null, "600222222", "600333333", "600444444", "600555555", "600666666", "600777777", "600888888", "600999999"};
        //String[] tlf2 = {null, null, null, null, null, null, null, null, null, null};
        Integer[] codigosPostales = {28001, null, 18001, 46001, 41001, 41002, 46002, 46003, 18002, 18003};
        String[] direcciones = {"Calle Admin 1", null, "Calle Jefe 1", "Calle Jefe 2", "Calle User 1", "Calle User 2", "Calle User 3", "Calle User 4", "Calle Visit 1", "Calle Visit 2"};
        String[] contactoEmergencia = {"600111112", null, "600222223", "600333334", "600444445","600555556", "600666667", "600777778", "600888889", "600999990"};
        String[] paises = {"España", null, "España", "España", "España", "España", "España", "España", "España", "España"};

        for (int i = 0; i < nombres.length; i++) {
            Detallesdeusuario detalles = new Detallesdeusuario();
            detalles.setNombre(nombres[i]);
            detalles.setApellidos(apellidos[i]);
            detalles.setPais(paises[i]);
            detalles.setDireccion(direcciones[i]);
            detalles.setTlf(tlf1[i]);
            detalles.setTlf2(null);
            detalles.setCodigoPostal(codigosPostales[i]);
            detalles.setContactoEmergencia(contactoEmergencia[i]);
            detalles.setEmailPersonal(emails[i]);
            detalles.setPoblacion(ciudades[i]);
            detallesDeUsuarioRepository.save(detalles);
        }

        int[] ids = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        String[] emailsTrabajo = {"ana.admin@correo.com", null, "luis.jefe1@correo.com", "marta.jefe2@correo.com", "carlos.user1@correo.com", "elena.user2@correo.com", "javier.user3@correo.com", "lucia.user4@correo.com", "pedro.visit1@correo.com", "sara.visit2@correo.com"};
        String[] passwords = {"adminpass", null, "jefe1pass", "jefe2pass", "user1pass", "user2pass", "user3pass", "user4pass", "visit1pass", "visit2pass"};
        String[] tokens = {"tokenadmin", null, "tokenjefe1", "tokenjefe2", "tokenuser1", "tokenuser2", "tokenuser3", "tokenuser4", "tokenvisit1", "tokenvisit2"};
        String[] fechasRegistro = {"2025-05-20", null, "2025-05-20", "2025-05-20", "2025-05-20", "2025-05-20", "2025-05-20", "2025-05-20", "2025-05-20", "2025-05-20"};
        Integer[] idJefe = {1, null, 3, 4, 3, 3, 4, 4, 3, 3};
        int[] personaIds = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        Map<Integer, Login> loginMap = new HashMap<>();

// Primera fase: crear y guardar todos los logins sin jefes
        for (int i = 0; i < emailsTrabajo.length; i++) {
            Login login = new Login();
            login.setEmailPrimario(emailsTrabajo[i]);
            login.setPassword(passwords[i]);
            login.setToken(tokens[i]);

            if (fechasRegistro[i] == null || fechasRegistro[i].isBlank()) {
                login.setLastLoginDay(null);
            } else {
                login.setLastLoginDay(LocalDate.parse(fechasRegistro[i], formatter));
            }

            Optional<Detallesdeusuario> detalles = detallesDeUsuarioRepository.findById(personaIds[i]);
            login.setIdDetallesDeUsuario(detalles.orElse(null));

            login = loginRepository.save(login); // persistimos primero
            loginMap.put(ids[i], login); // guardamos el login por ID
        }

// Segunda fase: asignar jefes
        for (int i = 0; i < emailsTrabajo.length; i++) {
            Login login = loginMap.get(ids[i]);

            if (idJefe[i] != null) {
                Login jefeLogin = loginMap.get(idJefe[i]);
                login.setJefeLogin(jefeLogin);
                loginRepository.save(login); // actualizamos login con jefe
            }
        }


        int[] roleIds = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        String[] roleNames = {"Admin", "Jefe", "Jefe", "Usuario", "Usuario", "Usuario", "Usuario", "Visitante", "Visitante"};
        int[] usuarioIds = {1, 3, 4, 5, 6, 7, 8, 9, 10};

        for (int i = 0; i < roleNames.length; i++) {
            Roles rol = new Roles();
            rol.setDescripcion(roleNames[i]);
            Login login = loginRepository.findById(usuarioIds[i]).get();
            rol.setLoginRol(login);
            rolesRepository.save(rol);
        }

        String[] tiposAusencias = {"Vacaciones", "No asiste", "Personal", "Salud"};
        for (int i = 0; i < tiposAusencias.length; i++) {
            TiposAusencias tausencia = new TiposAusencias();
            tausencia.setDescripcion(tiposAusencias[i]);
            tiposAusenciasRepository.save(tausencia);
        }

        boolean[] aprobado = {true, true, true, false, false, false, true, false, false, false, false, false, false, false, false, false, true};
        int[] totalDias = {25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25};
        String[] fechasFin = {"2025-07-24", "2025-06-02", "2025-05-02", "2025-07-06", "2025-05-16", "2025-05-13", "2025-05-19", "2025-04-04", "2025-04-16", "2025-07-27", "2025-07-18", "2025-05-23", "2025-03-13", "2025-07-17", "2025-08-22", "2025-05-09", "2025-05-14"};
        String[] fechasInicio = {"2025-07-20", "2025-05-28", "2025-04-24", "2025-07-04", "2025-05-15", "2025-05-13", "2025-05-19", "2025-04-04", "2025-04-14", "2025-07-23", "2025-07-16", "2025-05-21", "2025-03-13", "2025-07-14", "2025-08-18", "2025-05-07", "2025-05-12"};
        int[] idLogin = {1, 1, 1, 1, 1, 1, 1, 1, 5, 5, 5, 5, 5, 6, 6, 6, 6};
        int[] idTiposAusencias = {1, 1, 1, 1, 2, 2, 2, 2, 2, 1, 1, 2, 2, 1, 1, 2, 1};
        String[] documentos = {null, null, null, null, null, null, null, null, null, null, null, "tokenuser1.21_05_2025.23_05_2025.0.just.pdf|tokenuser1.21_05_2025.23_05_2025.1.just.pdf", "tokenuser1.13_03_2025.0.just.pdf|tokenuser1.13_03_2025.1.just.pdf", null, null, null, null};
        String[] comentarios = {null, null, null, null, null, null, null, null, null, null, null, "2//Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur rid", "4//Soy un maquina", null, null, null, null};

        for (int i = 0; i < totalDias.length; i++) {
            Ausencias ausencia = new Ausencias();
            ausencia.setAprobado(aprobado[i]);
            ausencia.setDocumentos(documentos[i]);
            ausencia.setCalcularDias(totalDias[i]);
            ausencia.setJustificacion(comentarios[i]);

            Login login = loginRepository.findById(idLogin[i]).get();
            ausencia.setLoginAusencias(login);

            TiposAusencias tiposausencia = tiposAusenciasRepository.getTiposAusenciasById(idTiposAusencias[i]);
            ausencia.setTiposAusencias(tiposausencia);

            if (fechasFin[i] == null || fechasFin[i].isBlank()) {
                ausencia.setFechaFin(null);
            } else {
                ausencia.setFechaFin(LocalDate.parse(fechasFin[i], formatter));
            }

            if (fechasInicio[i] == null || fechasInicio[i].isBlank()) {
                ausencia.setFechaInicio(null);
            } else {
                ausencia.setFechaInicio(LocalDate.parse(fechasInicio[i], formatter));
            }
            ausenciaRepository.save(ausencia);
        }

        int[] userIds = {1, 3, 4, 5, 6, 7, 8, 9, 10}; // omitimos el ID 2 (usuario "vacío")

        for (int i = 0; i < userIds.length; i++) {
            for (int j = 0; j < userIds.length; j++) {
                if (i != j) {
                    Integer idA = userIds[i];
                    Integer idB = userIds[j];

                    Login usuarioA = loginRepository.findById(idA).orElse(null);
                    Login usuarioB = loginRepository.findById(idB).orElse(null);

                    if (usuarioA != null && usuarioB != null) {
                        ChatAbierto chat = new ChatAbierto();
                        chat.setUsuarioA(usuarioA);
                        chat.setUsuarioB(usuarioB);
                        chat.setActivo(false); // todos los chats cerrados por defecto
                        chatAbiertoRepository.save(chat);
                    }
                }
            }
        }

        Login usuario5 = loginRepository.findById(5).orElseThrow(() -> new RuntimeException("Usuario 5 no encontrado"));
        Login usuario4 = loginRepository.findById(4).orElseThrow(() -> new RuntimeException("Usuario 4 no encontrado"));

        Mensaje[] mensajes = new Mensaje[5];

        mensajes[0] = new Mensaje();
        mensajes[0].setEmisor(usuario5);
        mensajes[0].setReceptor(usuario4);
        mensajes[0].setContenido("Hola, ¿cómo estás?");
        mensajes[0].setFechaEnvio(LocalDateTime.of(2025, 6, 10, 10, 0));
        mensajes[0].setEsGrupal(false);
        mensajes[0].setEsLeido(true);

        mensajes[1] = new Mensaje();
        mensajes[1].setEmisor(usuario4);
        mensajes[1].setReceptor(usuario5);
        mensajes[1].setContenido("¡Hola! Bien, gracias. ¿Y tú?");
        mensajes[1].setFechaEnvio(LocalDateTime.of(2025, 6, 10, 10, 2));
        mensajes[1].setEsGrupal(false);
        mensajes[1].setEsLeido(true);

        mensajes[2] = new Mensaje();
        mensajes[2].setEmisor(usuario5);
        mensajes[2].setReceptor(usuario4);
        mensajes[2].setContenido("Todo bien, trabajando en el proyecto.");
        mensajes[2].setFechaEnvio(LocalDateTime.of(2025, 6, 10, 10, 5));
        mensajes[2].setEsGrupal(false);
        mensajes[2].setEsLeido(true);

        mensajes[3] = new Mensaje();
        mensajes[3].setEmisor(usuario4);
        mensajes[3].setReceptor(usuario5);
        mensajes[3].setContenido("¡Genial! ¿Necesitas ayuda con algo?");
        mensajes[3].setFechaEnvio(LocalDateTime.of(2025, 6, 10, 10, 7));
        mensajes[3].setEsGrupal(false);
        mensajes[3].setEsLeido(true);

        mensajes[4] = new Mensaje();
        mensajes[4].setEmisor(usuario5);
        mensajes[4].setReceptor(usuario4);
        mensajes[4].setContenido("Por ahora no, gracias! Te aviso si surge algo.");
        mensajes[4].setFechaEnvio(LocalDateTime.of(2025, 6, 10, 10, 10));
        mensajes[4].setEsGrupal(false);
        mensajes[4].setEsLeido(true);

        mensajeRepository.saveAll(Arrays.asList(mensajes));
        // === PRIORIDADES ===
        String[][] prioridades = {{"high", "Alta"}, {"medium", "Media"}, {"low", "Baja"}};
        for (String[] p : prioridades) {
            Prioridades pr = new Prioridades();
            pr.setValue(p[0]);
            pr.setDisplayName(p[1]);
            prioridadesRepository.save(pr);
        }
    }



}
