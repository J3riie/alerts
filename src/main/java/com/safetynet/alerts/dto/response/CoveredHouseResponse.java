package com.safetynet.alerts.dto.response;

import java.util.List;

public class CoveredHouseResponse {

    private String address;

    private List<InhabitantResponse> inhabitants;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<InhabitantResponse> getInhabitants() {
        return inhabitants;
    }

    public void setInhabitants(List<InhabitantResponse> inhabitants) {
        this.inhabitants = inhabitants;
    }
}
