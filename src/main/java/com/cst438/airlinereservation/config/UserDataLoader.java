package com.cst438.airlinereservation.config;

import com.cst438.airlinereservation.domain.User;
import com.cst438.airlinereservation.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class UserDataLoader implements CommandLineRunner {

    private final UserRepository userRepository;

    @Autowired
    public UserDataLoader(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) {
        loadUserData();
    }

    private void loadUserData() {
        User user1 = new User();
        user1.setUsername("user1@csumb.edu");
        user1.setPassword("$2a$10$SVtqhyL3dPbMYUNbCj/2.eT4DPSiqciWByHGepK/p9hzfy/dCoTB."); // password1

        User user2 = new User();
        user2.setUsername("user2@csumb.edu");
        user2.setPassword("$2a$10$F3LdzxwexyeMFrfjBLdmTesVtjzjibz7sISrT0WU.lahFKiocEtb."); // password2

        User user3 = new User();
        user3.setUsername("user3@csumb.edu");
        user3.setPassword("$2a$10$xuFNQPm2a/hB2G1qGDRMgu2NVkUxwMVZ0TnMq4ihwykoD94fcsZO2"); // adminPassword


        // Save users to the database
        userRepository.saveAll(Arrays.asList(user1, user2));
    }
}
