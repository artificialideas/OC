package com.openclassrooms.SafetyNet.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "firestations")
public class Firestation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String address;
    private int station;

    /*public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public int getStation() {
        return station;
    }
    public void setStation(int station) {
        this.station = station;
    }*/
}
