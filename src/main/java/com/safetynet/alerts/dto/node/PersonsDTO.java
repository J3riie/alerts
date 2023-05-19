package com.safetynet.alerts.dto.node;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "firstName", "lastName", "address", "city", "zip", "phone", "email" })
public class PersonsDTO {

    @JsonProperty("firstName")
    @NotBlank
    private String firstName;

    @JsonProperty("lastName")
    @NotBlank
    private String lastName;

    @JsonProperty("address")
    @NotBlank
    private String address;

    @JsonProperty("city")
    @NotBlank
    private String city;

    @JsonProperty("zip")
    @NotNull
    private Integer zip;

    @JsonProperty("phone")
    @NotBlank
    private String phone;

    @JsonProperty("email")
    @Email(message = "Please use a valid mail")
    @NotBlank
    private String email;

    public boolean personExists(String firstName, String lastName) {
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getZip() {
        return zip;
    }

    public void setZip(int zip) {
        this.zip = zip;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public int hashCode() {
        return Objects.hash(address, city, email, firstName, lastName, phone, zip);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof PersonsDTO))
            return false;
        final PersonsDTO other = (PersonsDTO) obj;
        return Objects.equals(address, other.address) && Objects.equals(city, other.city) && Objects.equals(email, other.email)
                && Objects.equals(firstName, other.firstName) && Objects.equals(lastName, other.lastName) && Objects.equals(phone, other.phone)
                && zip == other.zip;
    }

}