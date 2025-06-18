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

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //ESTO TE IMPIDE IR A OTRA RUTA SIN HABERTE LOGEADO PRIMERO:
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/loginFaal", "/login?error", "/login?logout", "/css/**", "/js/**", "/images/**", "/gestionVRes/aprobar-justificacion","/login/validar-token", "login/cambiar-password").permitAll()
                        .requestMatchers("/admin-only").hasRole("ADMIN")
                        .requestMatchers("/jefe-only").hasRole("JEFE")
                        .requestMatchers("/usuario-only").hasRole("USUARIO")
                        .requestMatchers("/visitante-only").hasRole("VISITANTE")
                        .requestMatchers("/ticket").hasAnyRole("USUARIO","ADMIN","VISITANTE", "JEFE")
                        .requestMatchers("/admin-tickets").hasAnyRole( "ADMIN")

                        .anyRequest().authenticated()  // siempre al final

                )
                .exceptionHandling(exception -> exception
                        .accessDeniedPage("/error")
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .usernameParameter("correo")
                        .passwordParameter("contrasena")
                        .defaultSuccessUrl("/home", true)
                        .permitAll()
                )
                .httpBasic(Customizer.withDefaults())
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                );

        return http.build();
    }

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
