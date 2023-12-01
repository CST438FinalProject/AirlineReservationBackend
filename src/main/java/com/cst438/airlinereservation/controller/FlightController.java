package com.cst438.airlinereservation.controller;
import java.security.Principal;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

import com.cst438.airlinereservation.domain.*;
import com.cst438.airlinereservation.services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.security.core.Authentication;


@RestController
@CrossOrigin
public class FlightController {
    @Autowired
    FlightRepository flightRepository;
    @Autowired
    UserRepository userRepository;

    //todo already done
    //todo Navya Shetty : get all flights from db
    @GetMapping
    public List<Flight> getFlightList() {
        return (List<Flight>) flightRepository.findAll();
    }

    //todo Navya Shetty : isFull denotes if flight is to be show if there are no seats available
    @GetMapping("/routes/{isFull}")
    public List<Flight> getFlightRoutes(
            @RequestParam String src,
            @RequestParam String dst,
            @PathVariable boolean isFull
    ) {
        if (isFull) {
            // Implement logic to retrieve all flights for a given route (available or unavailable)
            // Modify the query according to your data model and requirements
            return flightRepository.findBySrcAndDst(src, dst);
        } else {
            // Implement logic to retrieve available flights for a given route
            // Modify the query according to your data model and requirements
            return flightRepository.findBySrcAndDstAndAvailableSeatsGreaterThan(src, dst, 0);
        }
    }

//todo Priya Sawant
    @PostMapping
    public Flight addFlight(@RequestBody Flight flightDetails) {
        if(flightDetails.getSrc() == null || flightDetails.getDst() == null){
            throw new IllegalArgumentException("Source and destination are required for adding a flight.");
        }


        return flightRepository.save(flightDetails);
    }

    //todo Priya Sawant
    //todo create method to user to cancel flight
    @DeleteMapping("{flightId}/{userId}")
    public ResponseEntity<String> cancelUserFlight(@PathVariable Long flightId, @PathVariable Long userId) {
        Optional<Flight> optionalFlight = flightRepository.findById(flightId);
        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalFlight.isPresent() && optionalUser.isPresent()) {
            Flight flight = optionalFlight.get();

            // Check if the user has the authority to cancel this flight (add your authorization logic here)
            if (userHasAuthorityToCancelFlight(optionalUser.get(), flight)) {
                flightRepository.delete(flight);
                return ResponseEntity.ok("Flight with ID " + flightId + " has been successfully canceled by User ID " + userId + ".");
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User ID " + userId + " does not have the authority to cancel this flight.");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Flight with ID " + flightId + " or User with ID " + userId + " not found.");
        }
    }
    private boolean userHasAuthorityToCancelFlight(User user, Flight flight) {
        return user.getAdmin().contains("ROLE_ADMIN");
    }
    @DeleteMapping("{flightId}")
    public boolean deleteFlight(@PathVariable Long flightId) { //todo change method name to delete flight
        // Implement logic to cancel a flight
        // Return true if the cancellation is successful, false otherwise


        Optional<Flight> optionalFlight = flightRepository.findById(flightId);
        if (optionalFlight.isPresent()) {
            flightRepository.delete(optionalFlight.get());
            return true;
        }
        return false;
    }

    //todo Aditya Saraf : EXTERNAL API

    /*
    todo test APIs using postman ~ friday Nov 30
    todo jUnit testing ~ friday Nov 30
    todo end to end testing ~ friday Nov 30
    todo integration testing  ~ saturday Dec 1
    todo AWS ~ Sunday Dec 2
     */
}