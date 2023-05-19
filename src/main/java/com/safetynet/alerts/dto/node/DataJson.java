package com.safetynet.alerts.dto.node;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "persons", "firestations", "medicalrecords" })
public class DataJson {

    @JsonProperty("persons")
    private List<PersonsDTO> persons = new ArrayList<>();

    @JsonProperty("firestations")
    private List<FireStationsDTO> firestations = new ArrayList<>();

    @JsonProperty("medicalrecords")
    private List<MedicalRecordsDTO> medicalrecords = new ArrayList<>();

    public List<PersonsDTO> getPersons() {
        return persons;
    }

    public void setPersons(List<PersonsDTO> persons) {
        this.persons = persons;
    }

    public List<FireStationsDTO> getFirestations() {
        return firestations;
    }

    public void setFirestations(List<FireStationsDTO> firestations) {
        this.firestations = firestations;
    }

    public List<MedicalRecordsDTO> getMedicalrecords() {
        return medicalrecords;
    }

    public void setMedicalrecords(List<MedicalRecordsDTO> medicalrecords) {
        this.medicalrecords = medicalrecords;
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = ((result * 31) + ((this.firestations == null) ? 0 : this.firestations.hashCode()));
        result = ((result * 31) + ((this.persons == null) ? 0 : this.persons.hashCode()));
        result = ((result * 31) + ((this.medicalrecords == null) ? 0 : this.medicalrecords.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof DataJson)) {
            return false;
        }
        final DataJson rhs = ((DataJson) other);
        return ((((this.firestations == rhs.firestations) || ((this.firestations != null) && this.firestations.equals(rhs.firestations)))
                && ((this.persons == rhs.persons) || ((this.persons != null) && this.persons.equals(rhs.persons))))
                && ((this.medicalrecords == rhs.medicalrecords) || ((this.medicalrecords != null) && this.medicalrecords.equals(rhs.medicalrecords))));
    }

}