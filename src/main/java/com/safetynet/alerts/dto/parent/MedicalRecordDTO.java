package com.safetynet.alerts.dto.parent;

import java.util.ArrayList;
import java.util.List;

public class MedicalRecordDTO {

    private String firstName;

    private String lastName;

    private String birthdate;

    private ArrayList<String> medications;

    private ArrayList<String> allergies;

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

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public List<String> getMedications() {
        return medications;
    }

    public void addMedication(String medication) {
        this.medications.add(medication);
    }

    public List<String> getAllergies() {
        return allergies;
    }

    public void addAllergies(String allergy) {
        this.allergies.add(allergy);
    }
}
