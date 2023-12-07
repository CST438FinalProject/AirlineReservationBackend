package com.cst438.airlinereservation.domain;

public record FlightDTO(String src,
                        String dst,
                        String departureTime,
                        String arrivalTime,
                        int capacity,
                        int availableSeats) {
}
