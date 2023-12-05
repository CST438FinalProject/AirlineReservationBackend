
package com.cst438.airlinereservation;



import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.cst438.airlinereservation.services.JwtService;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.Filter;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {


        // Get token from Authorization header
        String jws = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (jws != null) {
            try {
                // Verify token and get user
                String user = jwtService.getAuthUser(request);

                // Authenticate
                Authentication authentication =
                        new UsernamePasswordAuthenticationToken(user, null, java.util.Collections.emptyList());

                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception e) {
                // Log the exception
                logger.error("Exception in AuthenticationFilter", e);
            }
        }

        filterChain.doFilter(request, response);
    }


}
