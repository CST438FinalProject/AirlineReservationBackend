package com.cst438.airlinereservation.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cst438.airlinereservation.domain.UserRepository;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.cst438.airlinereservation.domain.User user = repository.findByUsername(username);

        User.UserBuilder builder = null;
        if (user!=null) {
            builder = org.springframework.security.core.userdetails.User.withUsername(username);
            builder.password(user.getPassword());
            //builder.roles(user.getRole());
        } else {
            throw new UsernameNotFoundException("User not found.");
        }

        return builder.build();
    }
}