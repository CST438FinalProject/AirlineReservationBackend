package com.cst438.airlinereservation.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.cst438.airlinereservation.domain.*;
import com.cst438.airlinereservation.services.JwtService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;


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


    //todo Aditya Saraf : EXTERNAL API

    @GetMapping("/external")
    public String getExternalFlights() {

        // url to retrieve api key
        final String tokenURI = "https://api.lufthansa.com/v1/oauth/token";
        RestTemplate token = new RestTemplate();
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // adding parameters to retrieve key
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("client_id", "tmmbykwzxu8h37jshe95dhf5p");
        map.add("client_secret", "gxBYREvVxr");
        map.add("grant_type", "client_credentials");

        // creating request and requesting a new key

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, header);
        String apiKeyResponse = token.postForObject(tokenURI, request, String.class);
        JSONObject json = new JSONObject(apiKeyResponse);

        String apiKey = json.getString("access_token");

        RestTemplate externalApi = new RestTemplate();

        ObjectMapper om = new ObjectMapper();
        om.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        MappingJackson2HttpMessageConverter mc = new MappingJackson2HttpMessageConverter();
        mc.setObjectMapper(om);

        externalApi.getMessageConverters().add(mc);

        HttpHeaders head = new HttpHeaders();
        head.setAccept(List.of(MediaType.APPLICATION_JSON));
        head.set("Authorization", "Bearer " + apiKey);

        String externalURI = "https://api.lufthansa.com/v1/flight-schedules/flightschedules/passenger?airlines=LH&startDate=01JAN19&endDate=01JAN34&daysOfOperation=1234567&timeMode=UTC&origin=FRA&destination=SFO";


        HttpEntity<?> externalEntity = new HttpEntity<>(head);
        String response = externalApi.exchange(externalURI,
                HttpMethod.GET, externalEntity, String.class).getBody();

        List<ExtFlight> list = new ArrayList<>();
        try{
            list = om.readValue(response, new TypeReference<List<ExtFlight>>() {});

        }catch(Exception e){
            System.out.println(e);
        }
        System.out.println(list);

        List<Flight> result = new ArrayList<>();

        for(ExtFlight ef : list){
            Flight f = ef.externalToFlight();
            result.add(f);
        }

        System.out.println(result);

        return response;
    }

}