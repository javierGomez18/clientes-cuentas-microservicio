package org.javierGomez18.clientes.cuentas.microservicio.infrastructure.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.javierGomez18.clientes.cuentas.microservicio.infrastructure.config.ClientWhitelistProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
public class HeaderValidationFilter extends OncePerRequestFilter {

    private final ClientWhitelistProperties whitelist;

    public HeaderValidationFilter(ClientWhitelistProperties whitelist) {
        this.whitelist = whitelist;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        String clientId = request.getHeader("X-Client-Id");

        if (clientId == null || clientId.isBlank()) {
            log.warn("[SECURITY] Missing X-Client-Id header");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Missing required header: X-Client-Id");
            return;
        }

        if (!whitelist.getAllowedClients().contains(clientId)) {
            log.warn("[SECURITY] Client '{}' rejected — not in whitelist", clientId);
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("Client not allowed: " + clientId);
            return;
        }

        filterChain.doFilter(request, response);
    }
}
