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

import com.safetynet.alerts.dto.resource.FireDTO;
import com.safetynet.alerts.dto.response.InhabitantResponse;
import com.safetynet.alerts.dto.response.MedicalHistory;
import com.safetynet.alerts.repository.DataRepository;
import com.safetynet.alerts.util.JsonDataRepositoryTestUtil;
import com.safetynet.alerts.util.TestRepository;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(SpringExtension.class)
public class FireServiceUnitTest {

    FireService service;

    DataRepository repo = new JsonDataRepositoryTestUtil();

    @BeforeEach
    public void setup() throws Exception {
        TestRepository.jsonDataInitializer();
        service = new FireService(repo);
    }

    @Test
    public void givenExistingAddress_whenGetPersonsInfoAtAddress_thenDTOIsAsExpected() {
        // given
        final String address = "15 Culver St";
        final FireDTO expectedDTO = new FireDTO();
        final InhabitantResponse inhabitant = new InhabitantResponse();
        final MedicalHistory medicalHistory = new MedicalHistory();
        final List<String> allergies = new ArrayList<>();
        final List<String> medications = new ArrayList<>();
        medications.add("paracetamol:500mg");
        medicalHistory.setAllergies(allergies);
        medicalHistory.setMedications(medications);
        inhabitant.setAge(13);
        inhabitant.setFirstName("Little");
        inhabitant.setLastName("Child");
        inhabitant.setMedicalHistory(medicalHistory);
        inhabitant.setPhone("7777");
        final List<InhabitantResponse> inhabitants = new ArrayList<>();
        inhabitants.add(inhabitant);
        expectedDTO.setInhabitants(inhabitants);
        expectedDTO.setCoveringStation(2);
        // when
        final FireDTO actualDTO = service.getPersonsInfoAtAddress(address);
        // then
        assertThat(actualDTO).usingRecursiveComparison().isEqualTo(expectedDTO);
    }

    @Test
    public void givenUnknownAddress_whenGetEmailAddressesFromCity_thenDTOIsEmpty() {
        // given
        final String unknownAddress = "unknown address";
        // when
        final FireDTO actualDTO = service.getPersonsInfoAtAddress(unknownAddress);
        // then
        assertThat(actualDTO.getInhabitants()).isEmpty();
        assertThat(actualDTO.getCoveringStation()).isEqualTo(-1);
    }
}
