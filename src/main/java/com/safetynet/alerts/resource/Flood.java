package com.safetynet.alerts.resource;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.alerts.dto.resource.StationsDTO;
import com.safetynet.alerts.dto.response.APIResponse;
import com.safetynet.alerts.dto.response.CoveredHouseResponse;
import com.safetynet.alerts.dto.response.InhabitantResponse;
import com.safetynet.alerts.service.resource.FloodService;

@RestController
@RequestMapping(value = "/flood", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class Flood {

    private static final Logger logger = LogManager.getLogger(Flood.class);

    @Autowired
    FloodService floodService;

    @GetMapping(value = "/stations")
    APIResponse<StationsDTO> stations(@RequestParam(name = "stations") List<Integer> stations) {
        logger.info("List of the persons covered by one of the stations number {} with their medical history :", stations);
        final ArrayList<String> stationAddresses = floodService.getFirestationAddressesFromNumbers(stations);

        final ArrayList<CoveredHouseResponse> coveredHouses = new ArrayList<>();
        for (final String s : stationAddresses) {
            final ArrayList<InhabitantResponse> inhabitants = floodService.getInfoFromAddress(s);
            floodService.setPersonsMedicalHistory(inhabitants);
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
        return new APIResponse<>(HttpStatus.OK.value(),
                String.format("List of the persons covered by one of the stations number %s with their medical history", stations), response);
    }
}
