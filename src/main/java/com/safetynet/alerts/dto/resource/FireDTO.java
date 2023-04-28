package com.safetynet.alerts.dto.resource;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.safetynet.alerts.dto.response.InhabitantResponse;

public class FireDTO {

    @JsonProperty("inhabitants")
    private List<InhabitantResponse> inhabitants;

    @JsonProperty("station_number")
    private int stationCovering;

    public List<InhabitantResponse> getInhabitants() {
        return inhabitants;
    }

    public void setInhabitants(List<InhabitantResponse> inhabitants) {
        this.inhabitants = inhabitants;
    }

    public int getCoveringStation() {
        return stationCovering;
    }

    public void setCoveringStation(int stationCovering) {
        this.stationCovering = stationCovering;
    }
}
