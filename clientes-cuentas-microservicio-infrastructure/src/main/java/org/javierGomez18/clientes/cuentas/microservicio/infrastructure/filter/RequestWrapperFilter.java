package org.javierGomez18.clientes.cuentas.microservicio.infrastructure.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.javierGomez18.clientes.cuentas.microservicio.infrastructure.wrapper.CachedBodyHttpServletRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class RequestWrapperFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        CachedBodyHttpServletRequest wrappedRequest = new CachedBodyHttpServletRequest(request);

        filterChain.doFilter(wrappedRequest, response);
    }
}
