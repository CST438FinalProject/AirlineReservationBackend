package com.cst438.airlinereservation;

import com.cst438.airlinereservation.controller.FlightController;
import com.cst438.airlinereservation.domain.Flight;
import com.cst438.airlinereservation.domain.FlightRepository;
import com.cst438.airlinereservation.domain.User;
import com.cst438.airlinereservation.domain.UserRepository;
import com.cst438.airlinereservation.services.JwtService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.transaction.Transactional;
import java.util.*;

import static java.nio.file.Paths.get;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class FlightControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;



    @Test
    void testGetFlightList() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/flights"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray());
    }
    @Test
    void testCancelUserFlight() throws Exception {
        Long userId = 1L;
        Long flightId = 1L;

        Flight mockedFlight = new Flight();
        mockedFlight.setId(flightId);
        mockedFlight.setAvailableSeats(5);

        User mockedUser = new User();
        mockedUser.setId(userId);
        mockedUser.addBookedFlight(mockedFlight);

        mockMvc.perform(MockMvcRequestBuilders.delete("/cancelFlight/{flightId}/{userId}", flightId, userId))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("User ID " + userId + " is not booked on Flight ID " + flightId + "."));

    }

    @Test
    void testBookUserFlight() throws Exception {
        Long userId = 1L;
        Long flightId = 1L;

        mockMvc.perform(MockMvcRequestBuilders.post("/bookFlight/{flightId}/{userId}", flightId, userId))
                .andExpect(status().isOk())
                .andExpect(content().string("User ID " + userId + " has successfully booked Flight ID " + flightId + "."));

    }
    @Test
    void testGetBookedFlights() throws Exception {
        Long userId = 1L;

        mockMvc.perform(MockMvcRequestBuilders.get("/user/{userId}/bookedFlight", userId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));

    }


}