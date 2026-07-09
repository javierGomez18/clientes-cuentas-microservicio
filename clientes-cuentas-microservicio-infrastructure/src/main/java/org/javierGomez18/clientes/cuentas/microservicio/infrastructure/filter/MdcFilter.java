package org.javierGomez18.clientes.cuentas.microservicio.infrastructure.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class MdcFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        try {
            String correlationId = (String) request.getAttribute("X-Correlation-Id");
            String clientId = request.getHeader("X-Client-Id");

            MDC.put("correlationId", correlationId != null ? correlationId : "N/A");
            MDC.put("clientId", clientId != null ? clientId : "N/A");

            filterChain.doFilter(request, response);

        } finally {
            MDC.clear();
        }
    }
}
