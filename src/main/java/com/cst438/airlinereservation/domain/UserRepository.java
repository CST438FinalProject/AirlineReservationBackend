package com.cst438.airlinereservation.domain;

import org.springframework.data.repository.CrudRepository;


public interface UserRepository extends
        CrudRepository<User, Integer>{

    User findByUsername(String username);
}