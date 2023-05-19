package com.safetynet.alerts.dto.node;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "firstName", "lastName", "birthdate", "medications", "allergies" })
public class MedicalRecordsDTO {

    @JsonProperty("firstName")
    @NotBlank
    private String firstName;

    @JsonProperty("lastName")
    @NotBlank
    private String lastName;

    @JsonProperty("birthdate")
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
    private Date birthdate;

    @JsonProperty("medications")
    private List<String> medications = new ArrayList<>();

    @JsonProperty("allergies")
    private List<String> allergies = new ArrayList<>();

    public boolean medicalRecordExists(String firstName, String lastName) {
        return this.firstName.equals(firstName) && this.lastName.equals(lastName);
    }

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

    public LocalDate getBirthdate() {
        return birthdate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public List<String> getMedications() {
        return medications;
    }

    public void setMedications(List<String> medications) {
        this.medications = medications;
    }

    public List<String> getAllergies() {
        return allergies;
    }

    public void setAllergies(List<String> allergies) {
        this.allergies = allergies;
    }

    @Override
    public int hashCode() {
        return Objects.hash(allergies, birthdate, firstName, lastName, medications);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof MedicalRecordsDTO))
            return false;
        final MedicalRecordsDTO other = (MedicalRecordsDTO) obj;
        return Objects.equals(allergies, other.allergies) && Objects.equals(birthdate, other.birthdate) && Objects.equals(firstName, other.firstName)
                && Objects.equals(lastName, other.lastName) && Objects.equals(medications, other.medications);
    }

}