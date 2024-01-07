package com.br.frete.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

        private static final String[] AUTH_WHITELIST = {
                "/swagger-resources",
                "/swagger-resources/**",
                "/configuration/ui",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**",
                "/v3/api-docs/**",
                "/api/public/**",
                "/api/public/authenticate",
                "/actuator/*",
                "/swagger-ui/**"
        };

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
                http.authorizeHttpRequests(
                                auth -> auth
                                                // .requestMatchers("/**").permitAll()
                                                .requestMatchers(AUTH_WHITELIST).permitAll()
                                                .requestMatchers("/home").permitAll()
                                                .requestMatchers("/empresa/logar", "/entregador/logar",
                                                                "/usuario/logar")
                                                .permitAll()
                                                .requestMatchers("/empresa/cadastrar", "/entregador/cadastrar",
                                                                "/usuario/cadastrar")
                                                .permitAll()
                                                .requestMatchers("/empresa/cadastrarProduto/**", "/empresa/home/**",
                                                                "/empresa/consultar/**",
                                                                "/empresa/frete/**", "/empresa/produtosFrete")
                                                .hasAuthority("EMPRESA")
                                                .requestMatchers("/entregador/home/**", "/entregador/consultar/**",
                                                                "/entregador/frete/**", "/entregador/consultaFretes/**",
                                                                "/entregador/produtosFretes/**",
                                                                "/entregador/consultar/**", "/entregador/teste/**", "/entregador/fretesConcluidos/**")
                                                .hasAuthority("ENTREGADOR")
                                                .requestMatchers("/usuario/home/**", "/usuario/consultar/**",
                                                                "/usuario/frete/**", "/usuario/consultaFretes/**", "/usuario/solicitarFrete/**",
                                                                "/usuario/produtosFretes/**", "/usuario/consultar/**")
                                                .hasAuthority("USUARIO")
                                                .anyRequest().authenticated())
                                .csrf(AbstractHttpConfigurer::disable);
                return http.build();
        }

        @Bean
        public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
                        throws Exception {
                return authenticationConfiguration.getAuthenticationManager();
        }

}