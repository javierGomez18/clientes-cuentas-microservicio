package org.javierGomez18.clientes.cuentas.microservicio.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

    http.csrf(AbstractHttpConfigurer::disable)
        .sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(
            auth ->
                auth.requestMatchers("/api/v1/clientes-cuentas/actuator/**")
                    .permitAll()
                    .requestMatchers(HttpMethod.GET, "/api/v1/clientes-cuentas/clientes/**")
                    .hasRole("clientes.read")
                    .requestMatchers(HttpMethod.POST, "/api/v1/clientes-cuentas/cuentas/**")
                    .access(
                        new WebExpressionAuthorizationManager(
                            "hasRole('clientes.read') and hasRole('cuentas.read')"))
                    .requestMatchers(HttpMethod.PUT, "/api/v1/clientes-cuentas/cuentas/**")
                    .access(
                        new WebExpressionAuthorizationManager(
                            "hasRole('clientes.read') and hasRole('cuentas.read')"))
                    .requestMatchers(HttpMethod.GET, "/api/v1/clientes-cuentas/movimientos/**")
                    .access(
                        new WebExpressionAuthorizationManager(
                            "hasRole('clientes.read') and hasRole('cuentas.read')"))
                    .anyRequest()
                    .authenticated())
        .oauth2ResourceServer(
            oauth ->
                oauth.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())));

    return http.build();
  }

  @Bean
  public JwtAuthenticationConverter jwtAuthenticationConverter() {
    JwtGrantedAuthoritiesConverter rolesConverter = new JwtGrantedAuthoritiesConverter();
    rolesConverter.setAuthoritiesClaimName("resource_access.clientes-cuentas-service.roles");
    rolesConverter.setAuthorityPrefix("ROLE_");

    JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
    converter.setJwtGrantedAuthoritiesConverter(rolesConverter);
    return converter;
  }
}
