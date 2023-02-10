package com.safetynet.alerts.dto.node;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "address", "station" })
public class FireStationsDTO {

    @JsonProperty("address")
    private String address;

    @JsonProperty("station")
    private int station;

    public String getAddress() {
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
    }

    @Override
    public int hashCode() {
        return Objects.hash(address, station);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof FireStationsDTO))
            return false;
        final FireStationsDTO other = (FireStationsDTO) obj;
        return Objects.equals(address, other.address) && station == other.station;
    }

}