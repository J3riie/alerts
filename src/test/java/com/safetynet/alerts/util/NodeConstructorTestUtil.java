package com.safetynet.alerts.util;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.safetynet.alerts.dto.resource.ChildAlertDTO;
import com.safetynet.alerts.dto.resource.CommunityEmailDTO;
import com.safetynet.alerts.dto.resource.FireDTO;
import com.safetynet.alerts.dto.resource.FireStationDTO;
import com.safetynet.alerts.dto.resource.FloodDTO;
import com.safetynet.alerts.dto.resource.PersonInfoDTO;
import com.safetynet.alerts.dto.resource.PhoneAlertDTO;
import com.safetynet.alerts.dto.response.ChildResponse;
import com.safetynet.alerts.dto.response.CoveredHouseResponse;
import com.safetynet.alerts.dto.response.CoveredPersonResponse;
import com.safetynet.alerts.dto.response.FamilyResponse;
import com.safetynet.alerts.dto.response.InhabitantResponse;
import com.safetynet.alerts.dto.response.PersonResponse;

public class NodeConstructorTestUtil {

    public String createValidFireStationAsJson() throws JSONException {
        final JSONObject payload = new JSONObject();
        payload.put("address", "a new address");
        payload.put("station", 1);
        return payload.toString();
    }

    public String createInvalidFireStationAsJson() throws JSONException {
        final JSONObject payload = new JSONObject();
        payload.put("address", "");
        payload.put("station", 0);
        return payload.toString();
    }

    public String createValidMedicalRecordAsJson() throws JSONException, InterruptedException {
        final JSONObject payload = new JSONObject();
        final JSONArray medications = new JSONArray("[\"doliprane\"]");
        final JSONArray allergies = new JSONArray("[\"pollen\", \"efferalgan\"]");
        payload.put("firstName", "Robin");
        payload.put("lastName", "Hugues");
        payload.put("birthdate", "10/24/1998");
        payload.put("medications", medications);
        payload.put("allergies", allergies);
        return payload.toString();
    }

    public String createInvalidMedicalRecordAsJson() throws JSONException {
        final JSONObject payload = new JSONObject();
        final JSONArray medications = new JSONArray("[\"doliprane\"]");
        final JSONArray allergies = new JSONArray("[\"pollen\", \"efferalgan\"]");
        payload.put("firstName", "Robin");
        payload.put("lastName", "Hugues");
        payload.put("birthdate", "10-24-1998");
        payload.put("medications", medications);
        payload.put("allergies", allergies);
        return payload.toString();
    }

    public String createValidPersonAsJson() throws JSONException {
        final JSONObject payload = new JSONObject();
        payload.put("firstName", "Robin");
        payload.put("lastName", "Hugues");
        payload.put("address", "an address");
        payload.put("city", "Culver");
        payload.put("zip", 97451);
        payload.put("phone", "000000");
        payload.put("email", "a@a.a");
        return payload.toString();
    }

    public String createInvalidPersonAsJson() throws JSONException {
        final JSONObject payload = new JSONObject();
        payload.put("firstName", "Robin");
        payload.put("lastName", "Hugues");
        payload.put("address", "an address");
        payload.put("city", "Culver");
        payload.put("zip", 97451);
        payload.put("phone", "000000");
        payload.put("email", "aaa");
        return payload.toString();
    }

    public FireStationDTO createInitialisedFireStationDTO() {
        final FireStationDTO dto = new FireStationDTO();
        final CoveredPersonResponse coveredPerson = new CoveredPersonResponse();
        coveredPerson.setAddress("an address");
        coveredPerson.setFirstName("First Name");
        coveredPerson.setLastName("Last Name");
        coveredPerson.setPhone("0000");
        final List<CoveredPersonResponse> coveredPersons = new ArrayList<>();
        coveredPersons.add(coveredPerson);
        dto.setCoveredPersons(coveredPersons);
        dto.setNumberOfAdults(1);
        dto.setNumberOfChildren(0);
        return dto;
    }

    public FireStationDTO createEmptyFireStationDTO() {
        final FireStationDTO dto = new FireStationDTO();
        final List<CoveredPersonResponse> coveredPersons = new ArrayList<>();
        dto.setCoveredPersons(coveredPersons);
        dto.setNumberOfAdults(0);
        dto.setNumberOfChildren(0);
        return dto;
    }

    public ChildAlertDTO createInitialisedChildAlertDTO() {
        final ChildAlertDTO dto = new ChildAlertDTO();
        final ChildResponse child = new ChildResponse();
        final FamilyResponse familyMember = new FamilyResponse();
        familyMember.setFirstName("First Name");
        familyMember.setLastName("Last Name");
        final List<FamilyResponse> family = new ArrayList<>();
        child.setAge(6);
        child.setFamily(family);
        child.setFirstName("First Name");
        child.setLastName("Last Name");
        final List<ChildResponse> children = new ArrayList<>();
        children.add(child);
        dto.setChildren(children);
        return dto;
    }

    public ChildAlertDTO createEmptyChildAlertDTO() {
        final ChildAlertDTO dto = new ChildAlertDTO();
        final List<ChildResponse> children = new ArrayList<>();
        dto.setChildren(children);
        return dto;
    }

    public CommunityEmailDTO createInitialisedCommunityEmailDTO() {
        final CommunityEmailDTO dto = new CommunityEmailDTO();
        final List<String> emailAddresses = new ArrayList<>();
        emailAddresses.add("an@email.com");
        dto.setEmailAddresses(emailAddresses);
        return dto;
    }

    public CommunityEmailDTO createEmptyCommunityEmailDTO() {
        final CommunityEmailDTO dto = new CommunityEmailDTO();
        final List<String> emailAddresses = new ArrayList<>();
        dto.setEmailAddresses(emailAddresses);
        return dto;
    }

    public FireDTO createInitialisedFireDTO() {
        final FireDTO dto = new FireDTO();
        final InhabitantResponse inhabitant = new InhabitantResponse();
        dto.setCoveringStation(1);
        final List<InhabitantResponse> inhabitants = new ArrayList<>();
        inhabitants.add(inhabitant);
        dto.setInhabitants(inhabitants);
        return dto;
    }

    public FireDTO createEmptyFireDTO() {
        final FireDTO dto = new FireDTO();
        final List<InhabitantResponse> inhabitants = new ArrayList<>();
        dto.setInhabitants(inhabitants);
        dto.setCoveringStation(0);
        return dto;
    }

    public FloodDTO createInitialisedFloodDTO() {
        final FloodDTO dto = new FloodDTO();
        final CoveredHouseResponse coveredHouse = new CoveredHouseResponse();
        final List<InhabitantResponse> inhabitants = new ArrayList<>();
        coveredHouse.setInhabitants(inhabitants);
        final List<CoveredHouseResponse> coveredHouses = new ArrayList<>();
        coveredHouses.add(coveredHouse);
        dto.setCoveredHouses(coveredHouses);
        return dto;
    }

    public FloodDTO createEmptyFloodDTO() {
        final FloodDTO dto = new FloodDTO();
        final List<CoveredHouseResponse> coveredHouses = new ArrayList<>();
        dto.setCoveredHouses(coveredHouses);
        return dto;
    }

    public PersonInfoDTO createInitialisedPersonInfoDTO() {
        final PersonInfoDTO dto = new PersonInfoDTO();
        final PersonResponse person = new PersonResponse();
        final List<PersonResponse> persons = new ArrayList<>();
        persons.add(person);
        dto.setPersons(persons);
        return dto;
    }

    public PersonInfoDTO createEmptyPersonInfoDTO() {
        final PersonInfoDTO dto = new PersonInfoDTO();
        final List<PersonResponse> persons = new ArrayList<>();
        dto.setPersons(persons);
        return dto;
    }

    public PhoneAlertDTO createInitialisedPhoneAlertDTO() {
        final PhoneAlertDTO dto = new PhoneAlertDTO();
        final List<String> phones = new ArrayList<>();
        phones.add("0000");
        dto.setPhones(phones);
        return dto;
    }

    public PhoneAlertDTO createEmptyPhoneAlertDTO() {
        final PhoneAlertDTO dto = new PhoneAlertDTO();
        final List<String> phones = new ArrayList<>();
        dto.setPhones(phones);
        return dto;
    }
}
