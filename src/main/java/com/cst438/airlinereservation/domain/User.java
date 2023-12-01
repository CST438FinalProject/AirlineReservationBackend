package com.cst438.airlinereservation.domain;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import com.cst438.airlinereservation.controller.FlightController;
@Entity
@Table(name= "USERTABLE")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int id;
    private String username;
    private String password;
    private String isAdmin;
    @OneToMany(mappedBy = "user" , cascade = CascadeType.ALL)
    private List<Flight> bookedFlights;

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public String getAdmin() {
        return isAdmin;
    }

    public void setAdmin(String admin) {
        isAdmin = admin;
    }

    public List<Flight> getBookedFlights() {
        return bookedFlights;
    }

    public void setBookedFlights(List<Flight> bookedFlights) {
        this.bookedFlights = bookedFlights;
    }
}

