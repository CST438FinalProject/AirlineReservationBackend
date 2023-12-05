package com.cst438.airlinereservation.domain;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.*;

import com.cst438.airlinereservation.controller.FlightController;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Entity
@Table(name= "USERTABLE")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private long id;
    private String username;
    private String password;


    @OneToMany(mappedBy = "user" , cascade = CascadeType.ALL)
    private List<Flight> bookedFlights;

    public long getUserid() {
        return id;
    }

    public void setUserid(long userid) {
        id = userid;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    public List<Flight> getBookedFlights() {
        return bookedFlights;
    }

    public void setBookedFlights(List<Flight> bookedFlights) {
        this.bookedFlights = bookedFlights;
    }



}

