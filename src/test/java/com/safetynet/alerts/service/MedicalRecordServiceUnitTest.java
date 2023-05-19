package com.safetynet.alerts.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.safetynet.alerts.dto.node.MedicalRecordsDTO;
import com.safetynet.alerts.repo.DataRepository;
import com.safetynet.alerts.util.JsonDataRepositoryTestUtil;
import com.safetynet.alerts.util.TestRepository;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(SpringExtension.class)
public class MedicalRecordServiceUnitTest {

    MedicalRecordService service;

    DataRepository repo = new JsonDataRepositoryTestUtil();

    @BeforeEach
    public void setup() throws Exception {
        TestRepository.jsonDataInitializer();
        service = new MedicalRecordService(repo);
    }

    @Test
    public void givenMedicalRecordsDTO_whenAddMedicalRecord_thenMedicalRecordsSizeGrows() {
        // given
        final MedicalRecordsDTO medicalRecord = new MedicalRecordsDTO();
        final int numberOfMedicalRecords = repo.getAllMedicalRecords().size();
        // when
        service.addMedicalRecord(medicalRecord);
        // then
        assertThat(repo.getAllMedicalRecords()).hasSizeGreaterThan(numberOfMedicalRecords);
    }

    @Test
    public void givenExistingNameAndNewMedications_whenModifyMedicalRecord_thenOnlyValuePassedIsModified() {
        // given
        final String firstName = "John";
        final String lastName = "Boyd";
        final List<String> newMedications = new ArrayList<>();
        final List<String> currentAllergies = repo.findMedicalRecordFromName(firstName, lastName).getAllergies();
        // when
        service.modifyMedicalRecord(firstName, lastName, null, newMedications, null);
        // then
        assertThat(repo.findMedicalRecordFromName(firstName, lastName).getMedications()).isEqualTo(newMedications);
        assertThat(repo.findMedicalRecordFromName(firstName, lastName).getAllergies()).isEqualTo(currentAllergies);
    }

    @Test
    public void givenUnknownName_whenModifyMedicalRecord_thenRuntimeExceptionIsThrown() {
        // given
        final String unknownFirstName = "an unknown firt name";
        final String unknownLastName = "an unknown last name";
        // when
        // then
        assertThrows(RuntimeException.class, () -> { service.modifyMedicalRecord(unknownFirstName, unknownLastName, null, null, null); },
                "No station corresponding to address " + unknownFirstName + " " + unknownLastName);
    }

    @Test
    public void givenExistingName_whenDeleteMedicalRecord_thenMedicalRecordsSizeDecreases() {
        // given
        final String firstName = "John";
        final String lastName = "Boyd";
        final int numberOfMedicalRecords = repo.getAllMedicalRecords().size();
        // when
        final Optional<MedicalRecordsDTO> optionalMedicalRecord = repo.getAllMedicalRecords().stream().filter(f -> f.medicalRecordExists(firstName, lastName))
                .findFirst();
        if (optionalMedicalRecord.isPresent()) {
            repo.getAllMedicalRecords().remove(optionalMedicalRecord.get());
        }
        // then
        assertThat(repo.getAllMedicalRecords()).hasSizeLessThan(numberOfMedicalRecords);
    }

    @Test
    public void givenUnknownName_whenDeleteMedicalRecord_thenNoValueIsFiltered() {
        // given
        final String unknownFirstName = "an unknown firt name";
        final String unknownLastName = "an unknown last name";
        final int numberOfMedicalRecords = repo.getAllMedicalRecords().size();
        // when
        final Optional<MedicalRecordsDTO> optionalMedicalRecord = repo.getAllMedicalRecords().stream()
                .filter(f -> f.medicalRecordExists(unknownFirstName, unknownLastName)).findFirst();
        // then
        assertThat(optionalMedicalRecord).isEmpty();
        assertThat(repo.getAllMedicalRecords()).hasSize(numberOfMedicalRecords);
    }

}
