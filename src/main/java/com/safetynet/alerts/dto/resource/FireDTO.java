package com.safetynet.alerts.dto.resource;

import java.util.List;

import com.safetynet.alerts.dto.response.InhabitantResponse;

public class FireDTO {

    private List<InhabitantResponse> inhabitants;

    private int stationCovering;

    public List<InhabitantResponse> getInhabitants() {
        return inhabitants;
    }

    public void setInhabitants(List<InhabitantResponse> inhabitants) {
        this.inhabitants = inhabitants;
    }

    public int getStationCovering() {
        return stationCovering;
    }

    public void setCoveringStation(int stationCovering) {
        this.stationCovering = stationCovering;
    }
}
