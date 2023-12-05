package com.cst438.airlinereservation;

import com.cst438.airlinereservation.domain.Flight;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@AutoConfigureMockMvc
class FlightControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetFlightList() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.get("/flights"))
                .andReturn()
                .getResponse();

        assertEquals(200, response.getStatus());
        assertEquals(MediaType.APPLICATION_JSON_VALUE, response.getContentType());

        // Parse JSON response to a List of Flight objects
        List<Flight> flights = objectMapper.readValue(response.getContentAsString(), new TypeReference<List<Flight>>() {});
        assertNotNull(flights);
        assertTrue(flights.size() > 0);
        // Add more assertions as needed
    }

    @Test
    void testGetReservedFlights() throws Exception {
        Long userId = 1L;

        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.get("/user/{userId}/reservedFlights", userId))
                .andReturn()
                .getResponse();

        assertEquals(200, response.getStatus());
        assertEquals(MediaType.APPLICATION_JSON_VALUE, response.getContentType());

        // Parse JSON response to a List of Flight objects
        List<Flight> reservedFlights = objectMapper.readValue(response.getContentAsString(), new TypeReference<List<Flight>>() {});
        assertNotNull(reservedFlights);
        // Add more assertions as needed
    }

    @Test
    void testBookUserFlight() throws Exception {
        Long userId = 1L;
        Long flightId = 1L;

        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.post("/bookFlight/{flightId}/{userId}", flightId, userId))
                .andReturn()
                .getResponse();

        assertEquals(200, response.getStatus());
        assertTrue(response.getContentAsString().contains("successfully booked"));
        // Add more assertions as needed
    }

    @Test
    void testCancelUserFlight() throws Exception {
        Long userId = 1L;
        Long flightId = 1L;

        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.delete("/cancelFlight/{flightId}/{userId}", flightId, userId))
                .andReturn()
                .getResponse();

        assertEquals(200, response.getStatus());
        assertTrue(response.getContentAsString().contains("successfully canceled"));
        // Add more assertions as needed
    }
}
