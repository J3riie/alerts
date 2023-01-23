package com.safetynet.alerts.dto.child;

public class ChildAlertDTO {

    private String firstName;

    private String lastName;

    private String age;

    private String[] famille;

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

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String[] getFamille() {
        return famille;
    }

    public void setFamille(String[] famille) {
        this.famille = famille;
    }
}
