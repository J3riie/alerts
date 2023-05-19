package com.safetynet.alerts.dto.resource;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.safetynet.alerts.dto.response.ChildResponse;

public class ChildAlertDTO {

    @JsonProperty("children")
    private List<ChildResponse> children;

    public List<ChildResponse> getChildren() {
        return children;
    }

    public void setChildren(List<ChildResponse> children) {
        this.children = children;
    }
}
