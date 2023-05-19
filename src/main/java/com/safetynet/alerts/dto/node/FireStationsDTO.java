package com.safetynet.alerts.dto.node;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "address", "station" })
public class FireStationsDTO {

    @JsonProperty("address")
    @NotBlank
    private String address;

    @JsonProperty("station")
    @NotNull
    @Min(value = 1)
    private Integer station;

    public String getAddress() {
        return address;
    }

    public boolean addressMappingExists(String address) {
        return this.address.equals(address);
    }

    public boolean stationMappingExists(Integer station) {
        return this.station.equals(station);
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