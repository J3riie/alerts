package com.safetynet.alerts.dto.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ChildResponse {

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    @JsonProperty("age")
    private long age;

    @JsonProperty("family")
    private List<FamilyResponse> family;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public long getAge() {
        return age;
    }

    public void setAge(long age) {
        this.age = age;
    }

    public List<FamilyResponse> getFamily() {
        return family;
    }

    public void setFamily(List<FamilyResponse> family) {
        this.family = family;
    }

}
