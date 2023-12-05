package com.cst438.airlinereservation.controller;
import com.cst438.airlinereservation.domain.LoginResponseDto;
import com.cst438.airlinereservation.domain.User;
import com.cst438.airlinereservation.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cst438.airlinereservation.dto.AccountCredDto;
import com.cst438.airlinereservation.services.JwtService;
@RestController
public class LoginController {
    @Autowired
    private JwtService jwtService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<String> getToken(@RequestBody AccountCredDto credentials) {
        UsernamePasswordAuthenticationToken creds =
                new UsernamePasswordAuthenticationToken(
                        credentials.username(),
                        credentials.password());

        Authentication auth;

        try{
            auth = authenticationManager.authenticate(creds);
        }catch(AuthenticationException ae){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access Denied");
        }

        // Generate token
        String jwts = jwtService.getToken(auth.getName());
        User user = userRepository.findByUsername(credentials.username());

        // Build response with the generated token and user role in the body
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwts)
                .header(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "Authorization")
                .body("Access Granted"); // Assuming role is stored in authorities
    }
}

