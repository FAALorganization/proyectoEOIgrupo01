package com.grupo01.java6.faal.config;

import com.grupo01.java6.faal.services.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configuración de seguridad para la aplicación.
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

    // Por simplicidad mientras no tengas passwords codificadas
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
        // Más adelante puedes usar BCryptPasswordEncoder para mayor seguridad
        // return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // Rutas públicas
                        .requestMatchers("/login", "/loginFaal", "/login?error", "/login?logout", "/css/**", "/js/**", "/images/**", "/gestionVRes/aprobar-justificacion").permitAll()
                        // Rutas restringidas (ejemplo de rol ADMIN)
                        //esto es un ejemplo para poner las rutas restringidas como para jefe o admin
                        .requestMatchers("/admin-only").hasRole("ADMIN")
                        .requestMatchers("/jefe-only").hasRole("JEFE")
                        .requestMatchers("usuario-only").hasRole("USUARIO")
                        .requestMatchers("visitante-only").hasRole("VISITANTE")
                        // Otras rutas requieren autenticación
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login") // Página login GET
                        .loginProcessingUrl("/login") // POST para procesar login
                        .usernameParameter("correo") // Nombre del campo usuario en el form
                        .passwordParameter("contrasena") // Nombre del campo password en el form
                        .defaultSuccessUrl("/home", true) // Página tras login exitoso
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


    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);

        authBuilder
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder());

        return authBuilder.build();
    }

}
