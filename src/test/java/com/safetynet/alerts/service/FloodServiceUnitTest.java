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

import com.safetynet.alerts.dto.resource.FloodDTO;
import com.safetynet.alerts.dto.response.CoveredHouseResponse;
import com.safetynet.alerts.dto.response.InhabitantResponse;
import com.safetynet.alerts.dto.response.MedicalHistory;
import com.safetynet.alerts.repo.DataRepository;
import com.safetynet.alerts.util.JsonDataRepositoryTestUtil;
import com.safetynet.alerts.util.TestRepository;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(SpringExtension.class)
public class FloodServiceUnitTest {

    FloodService service;

    DataRepository repo = new JsonDataRepositoryTestUtil();

    @BeforeEach
    public void setup() throws Exception {
        TestRepository.jsonDataInitializer();
        service = new FloodService(repo);
    }

    @Test
    public void givenExistingStations_whenGetInfoFromPersonsCoveredByStations_thenDTOIsAsExpected() {
        // given
        final List<Integer> stations = new ArrayList<>();
        stations.add(2);
        final FloodDTO expectedDTO = new FloodDTO();
        final CoveredHouseResponse house = new CoveredHouseResponse();
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
        house.setAddress("15 Culver St");
        house.setInhabitants(inhabitants);
        final List<CoveredHouseResponse> coveredHouses = new ArrayList<>();
        coveredHouses.add(house);
        expectedDTO.setCoveredHouses(coveredHouses);
        // when
        final FloodDTO actualDTO = service.getInfoFromPersonsCoveredByStations(stations);
        // then
        assertThat(actualDTO).usingRecursiveComparison().isEqualTo(expectedDTO);
    }

    @Test
    public void givenUnknownStations_whenGetInfoFromPersonsCoveredByStations_thenDTOIsEmpty() {
        // given
        final List<Integer> unknownStations = new ArrayList<>();
        unknownStations.add(2);
        // when
        final FloodDTO actualDTO = service.getInfoFromPersonsCoveredByStations(unknownStations);
        // then
        assertThat(actualDTO.getCoveredHouses()).hasSize(1);
    }
}
