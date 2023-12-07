package com.cst438.airlinereservation.config;


import com.cst438.airlinereservation.domain.Flight;
import com.cst438.airlinereservation.domain.FlightRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Date;

@Configuration
public class DataLoader {


    @Bean
    public CommandLineRunner loadFlights(FlightRepository flightRepository) {
        return args -> {
            // Add sample flights here
            Flight flight1 = new Flight();
            flight1.setCode("FL001");
            flight1.setSrc("CityA");
            flight1.setDst("CityB");
            flight1.setDepartureTime(Date.valueOf("2023-12-10"));
            flight1.setArrivalTime(Date.valueOf("2023-12-11"));
            flight1.setCapacity(100);
            flight1.setAvailableSeats(100);


            Flight flight2 = new Flight();
            flight2.setCode("FL002");
            flight2.setSrc("CityC");
            flight2.setDst("CityD");
            flight2.setDepartureTime(Date.valueOf("2023-12-12"));
            flight2.setArrivalTime(Date.valueOf("2023-12-13"));
            flight2.setCapacity(120);
            flight2.setAvailableSeats(120);

            Flight flight3 = new Flight();
            flight2.setCode("FL002");
            flight2.setSrc("CityE");
            flight2.setDst("CityF");
            flight2.setDepartureTime(Date.valueOf("2023-12-14"));
            flight2.setArrivalTime(Date.valueOf("2023-12-15"));
            flight2.setCapacity(120);
            flight2.setAvailableSeats(120);


            flightRepository.save(flight1);
            flightRepository.save(flight2);
            flightRepository.save(flight3);

        };
    }
}
