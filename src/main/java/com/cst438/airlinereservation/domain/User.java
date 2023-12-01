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
@Table(name= "user table")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int id;
    private String username;
    private String password;
    private Boolean isAdimn;
    @OneToMany(mappedBy = "user" , cascade = CascadeType.ALL)
    private List<FlightController> bookedFlights;

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

    public Boolean getAdimn() {
        return isAdimn;
    }

    public void setAdimn(Boolean adimn) {
        isAdimn = adimn;
    }

    public List<FlightController> getBookedFlights() {
        return bookedFlights;
    }

    public void setBookedFlights(List<FlightController> bookedFlights) {
        this.bookedFlights = bookedFlights;
    }
}

