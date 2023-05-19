package com.safetynet.alerts.dto.resource;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.safetynet.alerts.dto.response.CoveredPersonResponse;

public class FireStationDTO {

    @JsonProperty("person")
    private List<CoveredPersonResponse> coveredPersons;

    @JsonProperty("number_of_adults")
    private int numberOfAdults;

    @JsonProperty("number_of_children")
    private int numberOfChildren;

    public List<CoveredPersonResponse> getCoveredPersons() {
        return coveredPersons;
    }

    public void setCoveredPersons(List<CoveredPersonResponse> coveredPersons) {
        this.coveredPersons = coveredPersons;
    }

    public int getNumberOfAdults() {
        return numberOfAdults;
    }

    public void setNumberOfAdults(int numberOfAdults) {
        this.numberOfAdults = numberOfAdults;
    }

    public int getNumberOfChildren() {
        return numberOfChildren;
    }

    public void setNumberOfChildren(int numberOfChildren) {
        this.numberOfChildren = numberOfChildren;
    }

}
