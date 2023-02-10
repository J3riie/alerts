package com.safetynet.alerts.resource;

import static java.time.ZoneId.systemDefault;
import static org.springframework.http.HttpStatus.OK;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.alerts.App;
import com.safetynet.alerts.dto.node.FireStationsDTO;
import com.safetynet.alerts.dto.node.MedicalRecordsDTO;
import com.safetynet.alerts.dto.node.PersonsDTO;
import com.safetynet.alerts.dto.resource.StationsDTO;
import com.safetynet.alerts.dto.response.CoveredHouseResponse;
import com.safetynet.alerts.dto.response.InhabitantResponse;
import com.safetynet.alerts.dto.response.MedicalHistory;

@RestController
@RequestMapping(value = "/flood", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class Flood {
    private static final Logger logger = LogManager.getLogger(Flood.class);

    @GetMapping(value = "/stations")
    ResponseEntity<StationsDTO> stations(@RequestParam(name = "stations") List<Integer> stations) {
        logger.info("List of the persons covered by one of the stations number {} with their medical history :", stations);
        final ArrayList<String> stationAddresses = getFirestationAddressesFromNumbers(stations);

        final ArrayList<CoveredHouseResponse> coveredHouses = new ArrayList<>();
        for (final String s : stationAddresses) {
            final ArrayList<InhabitantResponse> inhabitants = getInfoFromAddress(s);
            setPersonsMedicalHistory(inhabitants);
            final CoveredHouseResponse coveredHouse = new CoveredHouseResponse();
            coveredHouse.setAddress(s);
            coveredHouse.setInhabitants(inhabitants);
            coveredHouses.add(coveredHouse);
        }

        final StationsDTO response = new StationsDTO();
        response.setCoveredHouses(coveredHouses);

        for (final CoveredHouseResponse c : coveredHouses) {
            logger.info("{}", c.getAddress());
            for (final InhabitantResponse i : c.getInhabitants()) {
                logger.info("{} {} {} {} {}", i.getFirstName(), i.getLastName(), i.getPhone(), i.getAge(), i.getMedicalHistory());
            }
        }
        return ResponseEntity.status(OK).body(response);
    }

    private ArrayList<String> getFirestationAddressesFromNumbers(List<Integer> stations) {
        final ArrayList<String> stationAddresses = new ArrayList<>();
        for (final int s : stations) {
            for (final FireStationsDTO f : App.getFirestations()) {
                if (f.getStation() == s && !stationAddresses.contains(f.getAddress())) {
                    stationAddresses.add(f.getAddress());
                }
            }
        }
        return stationAddresses;
    }

    private ArrayList<InhabitantResponse> getInfoFromAddress(String address) {
        final ArrayList<InhabitantResponse> inhabitants = new ArrayList<>();
        for (final PersonsDTO p : App.getPersons()) {
            if (p.getAddress().equals(address)) {
                final InhabitantResponse inhabitant = new InhabitantResponse();
                inhabitant.setFirstName(p.getFirstName());
                inhabitant.setLastName(p.getLastName());
                inhabitant.setPhone(p.getPhone());
                inhabitants.add(inhabitant);
            }
        }
        for (final InhabitantResponse i : inhabitants) {
            for (final MedicalRecordsDTO m : App.getMedicalrecords()) {
                if (m.getFirstName().equals(i.getFirstName()) && m.getLastName().equals(i.getLastName())) {
                    final LocalDate birthDate = m.getBirthdate().toInstant().atZone(systemDefault()).toLocalDate();
                    final LocalDate now = LocalDate.now();
                    final long yearsBetween = ChronoUnit.YEARS.between(birthDate, now);
                    final int age = (int) yearsBetween;
                    i.setAge(age);
                }
            }
        }
        return inhabitants;
    }

    private void setPersonsMedicalHistory(List<InhabitantResponse> inhabitants) {
        for (final InhabitantResponse i : inhabitants) {
            for (final MedicalRecordsDTO m : App.getMedicalrecords()) {
                if (m.getFirstName().equals(i.getFirstName()) && m.getLastName().equals(i.getLastName())) {
                    final MedicalHistory medicationResponse = new MedicalHistory();
                    medicationResponse.setMedications(m.getMedications());
                    medicationResponse.setAllergies(m.getAllergies());
                    i.setMedicalHistory(medicationResponse);
                }
            }
        }
    }

}
