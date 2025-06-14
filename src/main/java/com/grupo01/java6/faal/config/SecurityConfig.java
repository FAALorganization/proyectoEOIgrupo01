package com.grupo01.java6.faal.config;

import com.grupo01.java6.faal.services.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Clase de configuración de seguridad para la aplicación.
 * Utiliza las anotaciones de Spring Security para definir y personalizar
 * la seguridad de la aplicación, incluyendo la configuración de solicitudes
 * HTTP, autenticación y control de acceso.
 *
 * @Configuration Marca esta clase como una clase de configuración.
 * @EnableWebSecurity Habilita la seguridad web de Spring Security en la aplicación.
 *
 * @Author No se especificó autor.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity

public class SecurityConfig {


    private final CustomUserDetailsService customUserDetailsService;


    private final Environment environment;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService, Environment environment) {
        this.customUserDetailsService = customUserDetailsService;
        this.environment = environment;
    }

    //Por ahora lo comento porque no lo usamos (no tenemos las passwords codificadas: pass1, pass2, etc.)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
        //return new BCryptPasswordEncoder(); //Codificacion de passwords con BCrypt
    }



    /**
     * Método que configura un {@link UserDetailsService} para la autenticación en memoria.
     * Este método crea un usuario en memoria utilizando un nombre de usuario y contraseña
     * definidos en las propiedades de configuración `spring.security.user.name` y
     * `spring.security.user.password`, o valores predeterminados si no están configurados.
     *
     * <p>Nota: Se utiliza el identificador `{noop}` en la contraseña para evitar el uso
     * de un encoder, ideal solo para pruebas. No debe ser utilizado en un entorno de producción.</p>
     *
     * @return Un {@link InMemoryUserDetailsManager} que contiene los detalles del usuario configurado.
     *
     * @Author No se especificó autor.
     */

//    @Bean
//    public UserDetailsService userDetailsService() {
//        String name = environment.getProperty("spring.security.user.name", "user");
//        String password = environment.getProperty("spring.security.user.password", "password");
//
//        var user = User.withUsername(name)
//                .password("{noop}" + password) // {noop} indica que no se usa encoder para simplificar (solo pruebas)
//                .roles("USER")
//                .build();
//
//        return new InMemoryUserDetailsManager(user);
//    }

    /**
     * Configura una cadena de filtros de seguridad para gestionar la seguridad HTTP de la aplicación.
     * Permite personalizar los comportamientos de seguridad como protección CSRF, autenticación básica,
     * inicio de sesión con formulario y control de acceso a las solicitudes HTTP.
     *
     * <p>Este método define, entre otras configuraciones:
     * <ul>
     *   <li>Deshabilitar la protección CSRF, comúnmente usado en entornos de pruebas o para APIs REST.</li>
     *   <li>Autenticación HTTP básica y a través de formulario por defecto.</li>
     *   <li>Permitir el acceso público a ciertas rutas específicas, mientras que otras rutas
     *       requieren autenticación.</li>
     * </ul>
     * </p>
     *
     * @param http Objeto {@link HttpSecurity} provisto por Spring Security, utilizado para personalizar
     *             la configuración de seguridad web.
     * @return Un objeto {@link SecurityFilterChain} que representa la configuración de seguridad
     *         HTTP personalizada.
     * @throws Exception En caso de que ocurra algún error durante la configuración.
     *
     * @Author No se especificó autor.
     */
//    @Bean
    //ESTE TROZO DE CODIGO ES EL DEFAULT POR PARTE DE ALEJANDRO
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(Customizer.withDefaults()) // deshabilitado para pruebas o APIs
//                .httpBasic(Customizer.withDefaults())
//                .formLogin(Customizer.withDefaults())
//                .authorizeHttpRequests(authorize -> authorize
//                        .requestMatchers("/entities").permitAll()
//                        .requestMatchers("/entities/*").permitAll()
//                        .requestMatchers("/css/*").permitAll()
//                        .requestMatchers(HttpMethod.POST,"/entidades/deleteHija/*").authenticated()
//                        .anyRequest().authenticated()
//                );
//
//        return http.build();
//    }
    //ESTO TE PERMITE QUITAR TODOS LOS PERMISOS Y TENGAS QUE LOGEARTE
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(csrf -> csrf.disable()) // Desactiva CSRF
//                .authorizeHttpRequests(authz -> authz
//                        .anyRequest().permitAll() // Permite cualquiera sin autenticacion
//                )
//                .formLogin(form -> form.disable()) // Desactiva login con formulario
//                .httpBasic(httpBasic -> httpBasic.disable()); // Desactiva auth básica
//
//        return http.build();
//    }

    //ESTO TE IMPIDE IR A OTRA RUTA SIN HABERTE LOGEADO PRIMERO:

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/loginFaal", "/login?error", "/login?logout", "/css/**", "/js/**", "/images/**").permitAll()
                        .requestMatchers("/ticket").hasAnyRole("USUARIO", "ADMIN", "VISITANTE", "JEFE")
                        .anyRequest().authenticated()  // siempre al final
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .usernameParameter("correo")
                        .passwordParameter("contrasena")
                        .defaultSuccessUrl("/home", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                );

        return http.build();
    }



//        @Bean
//        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//            http.csrf(csrf -> csrf.disable())
//                    .authorizeHttpRequests(auth -> auth //Permite definir que rutas pueden accederse sin autorizacion
//                            .requestMatchers("/", "/login", "/loginFaal", "/css/**", "/js/**", "/images/**").permitAll() // público
//                            .anyRequest().authenticated() // el resto requiere login
//                    )
//                    .formLogin(form -> form //inicia la configuracion para login formularion personalizado
//                            .loginPage("/") // tu HTML de login
//                            .defaultSuccessUrl("/inicio", true)
//                            .permitAll()
//                    )
//                    .logout(logout -> logout
//                            //.logoutUrl("/logout")
//                            .logoutSuccessUrl("/") // vuelve al login tras logout
//                            .invalidateHttpSession(true) //invalida la sesion
//                            .deleteCookies("JSESSIONID") //borra la cookie
//                    );
//
//            return http.build();
//        }


    /**
     * Configura y proporciona un bean de tipo {@link AuthenticationManager}.
     * Este método utiliza {@link AuthenticationConfiguration} para recuperar
     * y devolver una instancia del manejador de autenticación.
     *
     * <p>El objeto {@link AuthenticationManager} es clave para gestionar los flujos
     * de autenticación en la aplicación.</p>
     *
     * @param authConfig Objeto {@link AuthenticationConfiguration} utilizado para crear
     *                   el manejador de autenticación.
     *
     * @return Una instancia de {@link AuthenticationManager} configurada a partir del
     *         {@link AuthenticationConfiguration} proporcionado.
     *
     * @throws Exception Si ocurre algún error durante la creación del manejador de autenticación.
     *
     * @Author No se especificó autor.
     */
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
//        return authConfig.getAuthenticationManager();
//    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);

        authBuilder
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder());

        return authBuilder.build();
    }


}
