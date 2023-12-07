package com.cst438.airlinereservation.controller;

import java.util.List;
import java.util.Optional;
import java.security.Principal;

import com.cst438.airlinereservation.domain.*;
import com.cst438.airlinereservation.services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;



@RestController
@CrossOrigin
public class FlightController {
    @Autowired
    FlightRepository flightRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    private JwtService jwtService;


    @GetMapping("/flights")
    public ResponseEntity<List<Flight>> getAllFlights() {
        List<Flight> flights = (List<Flight>) flightRepository.findAll();
        return ResponseEntity.ok(flights);
    }

    @GetMapping("/user/{userId}/bookedFlight")
    public ResponseEntity<List<Flight>> getBookedFlights(@PathVariable Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            List<Flight> bookedFlights = user.getBookedFlights();
            return ResponseEntity.ok(bookedFlights);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/bookFlight/{flightId}/{userId}")
    public ResponseEntity<String> bookUserFlight(@PathVariable Long flightId, @PathVariable Long userId) {
        Optional<Flight> optionalFlight = flightRepository.findById(flightId);
        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalFlight.isPresent() && optionalUser.isPresent()) {
            Flight flight = optionalFlight.get();
            User user = optionalUser.get();
            flight.setUser(user);

            // Check if the user has already booked a flight
            if (user.getBookedFlights().isEmpty()) {
                // Book the flight for the user
                user.getBookedFlights().add(flight);
                flightRepository.save(flight);
                userRepository.save(user);

                return ResponseEntity.ok("User ID " + userId + " has successfully booked Flight ID " + flightId + ".");
            } else {
                // User has already booked a flight
                return ResponseEntity.badRequest().body("User ID " + userId + " has already booked a flight.");
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/cancelFlight/{flightId}/{userId}")
    public ResponseEntity<String> cancelUserFlight(@PathVariable Long flightId, @PathVariable Long userId) {
        Optional<Flight> optionalFlight = flightRepository.findById(flightId);
        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalFlight.isPresent() && optionalUser.isPresent()) {
            Flight flight = optionalFlight.get();
            User user = optionalUser.get();

            if (user.getBookedFlights().contains(flight)) {
                user.getBookedFlights().remove(flight);
                flight.setAvailableSeats(flight.getAvailableSeats() + 1);
                userRepository.save(user);
                flightRepository.save(flight);

                return ResponseEntity.ok("User ID " + userId + " has successfully canceled the booking for Flight ID " + flightId + ".");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User ID " + userId + " is not booked on Flight ID " + flightId + ".");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Flight with ID " + flightId + " or User with ID " + userId + " not found.");
        }
    }

}