package com.safetynet.alerts.dto.resource;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.safetynet.alerts.dto.response.CoveredHouseResponse;

public class FloodDTO {

    @JsonProperty("covered_houses")
    private List<CoveredHouseResponse> coveredHouses;

    public List<CoveredHouseResponse> getCoveredHouses() {
        return coveredHouses;
    }

    public void setCoveredHouses(List<CoveredHouseResponse> coveredHouses) {
        this.coveredHouses = coveredHouses;
    }

}
