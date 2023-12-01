package com.cst438.airlinereservation.domain;


import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface FlightRepository extends CrudRepository<Flight , Long> {

    List<Flight> findBySrcAndDstAndAvailableSeatsGreaterThan(String src, String dst, int availableSeats);

    List<Flight> findBySrcAndDst(String src, String dst);

}
