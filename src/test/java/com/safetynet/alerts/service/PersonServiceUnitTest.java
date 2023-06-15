package com.safetynet.alerts.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.safetynet.alerts.dto.node.PersonsDTO;
import com.safetynet.alerts.repository.DataRepository;
import com.safetynet.alerts.util.JsonDataRepositoryTestUtil;
import com.safetynet.alerts.util.TestRepository;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(SpringExtension.class)
public class PersonServiceUnitTest {

    PersonService service;

    DataRepository repo = new JsonDataRepositoryTestUtil();

    @BeforeEach
    public void setup() throws Exception {
        TestRepository.jsonDataInitializer();
        service = new PersonService(repo);
    }

    @Test
    public void givenPersonsDTO_whenAddPerson_thenPersonsSizeGrows() {
        // given
        final PersonsDTO person = new PersonsDTO();
        final int numberOfPersons = repo.getAllPersons().size();
        // when
        service.addPerson(person);
        // then
        assertThat(repo.getAllPersons()).hasSizeGreaterThan(numberOfPersons);
    }

    @Test
    public void givenExistingNameAndNewAddress_whenModifyPerson_thenOnlyValuePassedIsModified() {
        // given
        final String firstName = "John";
        final String lastName = "Boyd";
        final String newAddress = "a new address";
        final int currentZip = repo.findPersonWithName(firstName, lastName).getZip();
        // when
        service.modifyPerson(firstName, lastName, newAddress, null, null, null, null);
        // then
        assertThat(repo.findPersonWithName(firstName, lastName).getAddress()).isEqualTo(newAddress);
        assertThat(repo.findPersonWithName(firstName, lastName).getZip()).isEqualTo(currentZip);
    }

    @Test
    public void givenUnknownName_whenModifyPerson_thenRuntimeExceptionIsThrown() {
        // given
        final String unknownFirstName = "an unknown firt name";
        final String unknownLastName = "an unknown last name";
        // when
        // then
        assertThrows(RuntimeException.class, () -> { service.modifyPerson(unknownFirstName, unknownLastName, null, null, null, null, null); },
                "No station corresponding to address " + unknownFirstName + " " + unknownLastName);
    }

    @Test
    public void givenExistingName_whenDeletePerson_thenPersonsSizeDecreases() {
        // given
        final String firstName = "John";
        final String lastName = "Boyd";
        final int numberOfPersons = repo.getAllPersons().size();
        // when
        final Optional<PersonsDTO> optionalPerson = repo.getAllPersons().stream().filter(f -> f.personExists(firstName, lastName)).findFirst();
        if (optionalPerson.isPresent()) {
            repo.getAllPersons().remove(optionalPerson.get());
        }
        // then
        assertThat(repo.getAllPersons()).hasSizeLessThan(numberOfPersons);
    }

    @Test
    public void givenUnknownName_whenDeletePerson_thenNoValueIsFiltered() {
        // given
        final String unknownFirstName = "an unknown firt name";
        final String unknownLastName = "an unknown last name";
        final int numberOfPersons = repo.getAllPersons().size();
        // when
        final Optional<PersonsDTO> optionalPerson = repo.getAllPersons().stream().filter(f -> f.personExists(unknownFirstName, unknownLastName)).findFirst();
        // then
        assertThat(optionalPerson).isEmpty();
        assertThat(repo.getAllPersons()).hasSize(numberOfPersons);
    }

}
