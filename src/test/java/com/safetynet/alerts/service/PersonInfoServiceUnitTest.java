package com.safetynet.alerts.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.safetynet.alerts.dto.resource.PersonInfoDTO;
import com.safetynet.alerts.dto.response.MedicalHistory;
import com.safetynet.alerts.dto.response.PersonResponse;
import com.safetynet.alerts.repository.DataRepository;
import com.safetynet.alerts.util.JsonDataRepositoryTestUtil;
import com.safetynet.alerts.util.TestRepository;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(SpringExtension.class)
public class PersonInfoServiceUnitTest {

    PersonInfoService service;

    DataRepository repo = new JsonDataRepositoryTestUtil();

    @BeforeEach
    public void setup() throws Exception {
        TestRepository.jsonDataInitializer();
        service = new PersonInfoService(repo);
    }

    @Test
    public void givenExistingNames_whenGetPersonsWithNames_thenDTOIsAsExpected() {
        // given
        final String firstName = "Litle";
        final String lastName = "Child";
        final PersonInfoDTO expectedDTO = new PersonInfoDTO();
        final MedicalHistory medicalHistory = new MedicalHistory();
        final List<String> allergies = new ArrayList<>();
        final List<String> medications = new ArrayList<>();
        medications.add("paracetamol:500mg");
        medicalHistory.setAllergies(allergies);
        medicalHistory.setMedications(medications);
        final PersonResponse person = new PersonResponse();
        person.setAddress("15 Culver St");
        person.setAge(13);
        person.setEmailAddress("an@email.com");
        person.setFirstName(firstName);
        person.setLastName(lastName);
        person.setMedicalHistory(medicalHistory);
        final List<PersonResponse> persons = new ArrayList<>();
        expectedDTO.setPersons(persons);
        // when
        final PersonInfoDTO actualDTO = service.getPersonsWithNames(firstName, lastName);
        // then
        assertThat(actualDTO).usingRecursiveComparison().isEqualTo(expectedDTO);
    }

    @Test
    public void givenUnknownNames_whenGetPersonsWithNames_thenDTOIsEmpty() {
        // given
        final String unknownFirstName = "Unknown";
        final String unknownLastName = "Names";
        // when
        final PersonInfoDTO actualDTO = service.getPersonsWithNames(unknownFirstName, unknownLastName);
        // then
        assertThat(actualDTO.getPersons()).isEmpty();
    }

}
