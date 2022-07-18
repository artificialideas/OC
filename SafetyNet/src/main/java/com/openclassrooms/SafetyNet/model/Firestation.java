package com.openclassrooms.SafetyNet.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Firestation {

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
