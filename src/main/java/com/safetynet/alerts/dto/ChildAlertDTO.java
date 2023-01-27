package com.safetynet.alerts.dto;

import java.util.ArrayList;

public class ChildAlertDTO {

    private String firstName;

    private String lastName;

    private int age;

    private ArrayList<FamilyDTO> family;

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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public ArrayList<FamilyDTO> getFamily() {
        return family;
    }

    public void setFamily(ArrayList<FamilyDTO> family) {
        this.family = family;
    }
}
