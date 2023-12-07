package com.cst438.airlinereservation.services;

import com.cst438.airlinereservation.domain.Flight;
import com.cst438.airlinereservation.domain.FlightRepository;
import com.cst438.airlinereservation.domain.User;
import com.cst438.airlinereservation.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FlightRepository flightRepository;

    public ResponseEntity<String> bookFlightForUser(Long userId, Long flightId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        Optional<Flight> optionalFlight = flightRepository.findById(flightId);

        if (optionalUser.isPresent() && optionalFlight.isPresent()) {
            User user = optionalUser.get();
            Flight flight = optionalFlight.get();

            // Check if the user has already booked this flight
            if (user.getBookedFlights().contains(flight)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User ID " + userId + " is already booked on Flight ID " + flightId + ".");
            }

            // Book the flight for the user
            user.getBookedFlights().add(flight);
            flight.setAvailableSeats(flight.getAvailableSeats() - 1);

            // Save the user and flight entities
            userRepository.save(user);
            flightRepository.save(flight);

            return ResponseEntity.ok("User ID " + userId + " has successfully booked Flight ID " + flightId + ".");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Flight with ID " + flightId + " or User with ID " + userId + " not found.");
        }
    }

    public ResponseEntity<List<Flight>> getReservedFlightsForUser(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            List<Flight> reservedFlights = user.getBookedFlights();
            return ResponseEntity.ok(reservedFlights);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
