package com.example.bancofuncional.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // A configuração do PasswordEncoder continua a mesma
    // @Bean public PasswordEncoder passwordEncoder() { ... }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Desabilita CSRF
            .cors(cors -> cors.configurationSource(corsConfigurationSource())) // HABILITA CORS de forma explícita
            .authorizeHttpRequests(authz -> authz
                // Permite acesso PÚBLICO a todos os nossos endpoints da API
                .requestMatchers("/api/cadastro", "/api/login").permitAll()
                // Permite acesso a qualquer outra rota (útil para desenvolvimento)
                .anyRequest().permitAll() 
            );
        return http.build();
    }

    // Bean de configuração explícita do CORS
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // Permite que qualquer origem (ex: seu arquivo local, um site futuro) acesse a API
        configuration.setAllowedOrigins(Arrays.asList("*")); 
        // Permite os métodos HTTP mais comuns
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        // Permite todos os cabeçalhos
        configuration.setAllowedHeaders(Arrays.asList("*"));
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // Aplica essa configuração a todas as rotas
        return source;
    }
}