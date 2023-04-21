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

import com.safetynet.alerts.dto.node.FireStationsDTO;
import com.safetynet.alerts.repo.DataRepository;
import com.safetynet.alerts.service.FireStationService;
import com.safetynet.alerts.util.JsonDataRepositoryTestUtil;
import com.safetynet.alerts.util.TestRepository;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(SpringExtension.class)
public class FireStationServiceUnitTest {

    FireStationService service;

    DataRepository repo = new JsonDataRepositoryTestUtil();

    @BeforeEach
    public void setup() throws Exception {
        TestRepository.jsonDataInitializer();
        service = new FireStationService(repo);
    }

    @Test
    public void givenFireStationsDTO_whenAddFireStation_thenFireStationsSizeGrows() {
        // given
        final FireStationsDTO firestation = new FireStationsDTO();
        final int numberOfFirestations = repo.getAllFirestations().size();
        // when
        service.addFireStation(firestation);
        // then
        assertThat(repo.getAllFirestations()).hasSizeGreaterThan(numberOfFirestations);
    }

    @Test
    public void givenExistingAdressStationPair_whenModifyFireStation_thenAdressIsPairedWithStation() {
        // given
        final String address = "1509 Culver St";
        final int stationNumber = 1;
        // when
        service.modifyFireStation(address, stationNumber);
        // then
        assertThat(repo.findFireStationFromAddress(address).getStation()).isEqualTo(1);
    }

    @Test
    public void givenUnknownAddress_whenModifyFireStation_thenRuntimeExceptionIsThrown() {
        // given
        final String unknownAddress = "an unknown street";
        // when
        // then
        assertThrows(RuntimeException.class, () -> { service.modifyFireStation(unknownAddress, 1); }, "No station corresponding to address " + unknownAddress);
    }

    @Test
    public void givenExistingAddress_whenDeleteFireStation_thenFireStationsSizeDecreases() {
        // given
        final String address = "1509 Culver St";
        final int numberOfFirestations = repo.getAllFirestations().size();
        // when
        final Optional<FireStationsDTO> optionalFirestation = repo.getAllFirestations().stream().filter(f -> f.addressMappingExists(address)).findFirst();
        if (optionalFirestation.isPresent()) {
            repo.getAllFirestations().remove(optionalFirestation.get());
        }
        // then
        assertThat(repo.getAllFirestations()).hasSizeLessThan(numberOfFirestations);
    }

    @Test
    public void givenUnknownAddress_whenDeleteFireStation_thenNoValueIsFiltered() {
        // given
        final String address = "an unknown street";
        final int numberOfFirestations = repo.getAllFirestations().size();
        // when
        final Optional<FireStationsDTO> optionalFirestation = repo.getAllFirestations().stream().filter(f -> f.addressMappingExists(address)).findFirst();
        // then
        assertThat(optionalFirestation).isEmpty();
        assertThat(repo.getAllFirestations()).hasSize(numberOfFirestations);
    }

    @Test
    public void givenExistingStationNumber_whenDeleteFireStation_thenFireStationsSizeDecreases() {
        // given
        final int stationNumber = 1;
        final int numberOfFirestations = repo.getAllFirestations().size();
        // when
        Optional<FireStationsDTO> optionalFirestation = repo.getAllFirestations().stream().filter(f -> f.stationMappingExists(stationNumber)).findFirst();
        while (optionalFirestation.isPresent()) {
            repo.getAllFirestations().remove(optionalFirestation.get());
            optionalFirestation = repo.getAllFirestations().stream().filter(f -> f.stationMappingExists(stationNumber)).findFirst();
        }
        // then
        assertThat(repo.getAllFirestations()).hasSizeLessThan(numberOfFirestations);
    }

    @Test
    public void givenUnknownStationNumber_whenDeleteFireStation_thenNoValueIsFiltered() {
        // given
        final int stationNumber = 0;
        final int numberOfFirestations = repo.getAllFirestations().size();
        // when
        Optional<FireStationsDTO> optionalFirestation = repo.getAllFirestations().stream().filter(f -> f.stationMappingExists(stationNumber)).findFirst();
        while (optionalFirestation.isPresent()) {
            repo.getAllFirestations().remove(optionalFirestation.get());
            optionalFirestation = repo.getAllFirestations().stream().filter(f -> f.stationMappingExists(stationNumber)).findFirst();
        }
        // then
        assertThat(optionalFirestation).isEmpty();
        assertThat(repo.getAllFirestations()).hasSize(numberOfFirestations);
    }

}
