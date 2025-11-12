package com.mandeepa.das_backend.config;

import jakarta.servlet.http.*;
import org.springframework.lang.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import jakarta.servlet.FilterChain;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
class AuthDebugFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest req,
                                    @NonNull HttpServletResponse res,
                                    @NonNull FilterChain chain)
            throws jakarta.servlet.ServletException, java.io.IOException {

        var auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("REQ " + req.getMethod() + " " + req.getRequestURI() + " | auth=" + (auth!=null?auth.getName():"none") + " roles=" + (auth!=null?auth.getAuthorities():"[]"));
        chain.doFilter(req, res);
    }
}
