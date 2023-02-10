package com.safetynet.alerts.dto.response;

import java.util.List;

public class ChildResponse {

    private String firstName;

    private String lastName;

    private long age;

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
