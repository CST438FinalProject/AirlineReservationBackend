package com.cst438.airlinereservation.controller;

import java.util.List;
import java.util.Optional;

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

    //todo already done
    //todo Navya Shetty : get all flights from db
    @GetMapping("/flights")
    public List<Flight> getFlightList() {
        return (List<Flight>) flightRepository.findAll();
    }

    @GetMapping("/user/{userId}/reservedFlights")
    public ResponseEntity<List<Flight>> getReservedFlights(@PathVariable Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            List<Flight> reservedFlights = user.getBookedFlights();
            return ResponseEntity.ok(reservedFlights);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


//    //todo Navya Shetty : isFull denotes if flight is to be show if there are no seats available
//    @GetMapping("/routes/{isFull}")
//    public List<Flight> getFlightRoutes(
//            @RequestParam String src,
//            @RequestParam String dst,
//            @PathVariable boolean isFull
//    ) {
//        if (isFull) {
//            // Implement logic to retrieve all flights for a given route (available or unavailable)
//            // Modify the query according to your data model and requirements
//            return flightRepository.findBySrcAndDst(src, dst);
//        } else {
//            // Implement logic to retrieve available flights for a given route
//            // Modify the query according to your data model and requirements
//            return flightRepository.findBySrcAndDstAndAvailableSeatsGreaterThan(src, dst, 0);
//        }
//    }

//todo Priya Sawant : method should check if user is admin
//@PostMapping("/addFlight")
//public Flight addFlight(@RequestBody Flight flightDetails) {
//    // Proceed with adding the flight
//    if (flightDetails.getSrc() == null || flightDetails.getDst() == null) {
//        throw new IllegalArgumentException("Source and destination are required for adding a flight.");
//    }
//    return flightRepository.save(flightDetails);
//}



    //todo Priya Sawant : create a method for user to book flight,  user has a list of booked flights as an attribute
    // add the flight to user's list of flights and decrement the flight's available seats counter
    @PostMapping("/bookFlight/{flightId}/{userId}")
    public ResponseEntity<String> bookUserFlight(@PathVariable Long flightId, @PathVariable Long userId) {
        Optional<Flight> optionalFlight = flightRepository.findById(flightId);
        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalFlight.isPresent() && optionalUser.isPresent()) {
            Flight flight = optionalFlight.get();
            User user = optionalUser.get();
            if (flight.getAvailableSeats() > 0) {
                if (!user.getBookedFlights().contains(flight)) {
                    user.getBookedFlights().add(flight);
                    flight.setAvailableSeats(flight.getAvailableSeats() - 1);
                    userRepository.save(user);
                    flightRepository.save(flight);

                    return ResponseEntity.ok("User ID " + userId + " has successfully booked Flight ID " + flightId + ".");
                } else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User ID " + userId + " is already booked on Flight ID " + flightId + ".");
                }
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No available seats on Flight ID " + flightId + ".");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Flight with ID " + flightId + " or User with ID " + userId + " not found.");
        }
    }



    //todo Priya Sawant
    //todo modify method to user to cancel flight : user can cancel own flights, not any other flights
    // structure for the method is to remove flight from user's flight list and increment available seats counter


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

//    @DeleteMapping("/cancelFlight/{userId}/{flightId}")
//    public ResponseEntity<String> cancelUserFlight(@PathVariable Long flightId, @PathVariable Long userId) {
//        Optional<Flight> optionalFlight = flightRepository.findById(flightId);
//        Optional<User> optionalUser = userRepository.findById(userId);
//
//        if (optionalFlight.isPresent() && optionalUser.isPresent()) {
//            Flight flight = optionalFlight.get();
//            User user = optionalUser.get();
//
//            // Check if the user is an admin and has the authority to cancel any flight
//            if (isUserAdmin(user)) {
//                flightRepository.delete(flight);
//                return ResponseEntity.ok("Flight with ID " + flightId + " has been successfully canceled by User ID " + userId + ".");
//            } else {
//                // User is not an admin, they can only cancel their own flights
//                if (flight.getUser() != null && flight.getUser().getUserid() == user.getUserid()) {
//                    flightRepository.delete(flight);
//                    return ResponseEntity.ok("Flight with ID " + flightId + " has been successfully canceled by User ID " + userId + ".");
//                } else {
//                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User ID " + userId + " does not have the authority to cancel this flight.");
//                }
//            }
//        } else {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Flight with ID " + flightId + " or User with ID " + userId + " not found.");
//        }
//    }






    // todo Priya Sawant : user can cancel own booked flights, this method should be called isUserAdmin

// todo Priya Sawant : method should check if user is admin
//    @DeleteMapping("/{flightId}")
//    public boolean deleteFlight(@PathVariable Long flightId) { //todo change method name to delete flight
//        // Implement logic to cancel a flight
//        // Return true if the cancellation is successful, false otherwise
//        Optional<Flight> optionalFlight = flightRepository.findById(flightId);
//        if (optionalFlight.isPresent()) {
//            flightRepository.delete(optionalFlight.get());
//            return true;
//        }
//        return false;
//    }

    //todo Aditya Saraf : EXTERNAL API

    /*
    todo test APIs using postman ~ friday Nov 30
    todo jUnit testing ~ friday Nov 30
    todo end to end testing ~ friday Nov 30
    todo integration testing  ~ saturday Dec 1
    todo AWS ~ Sunday Dec 2
     */
}