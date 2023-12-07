
package com.cst438.airlinereservation;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoder {

    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String password = "adminPassword";
        String encrpted_password = encoder.encode(password);
        System.out.println(encrpted_password);
    }


}