package com.safetynet.alerts.endpoint;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.alerts.dto.resource.FloodDTO;
import com.safetynet.alerts.dto.response.CoveredHouseResponse;
import com.safetynet.alerts.dto.response.InhabitantResponse;
import com.safetynet.alerts.service.FloodService;

@RestController
@RequestMapping(value = "/flood", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class FloodEndpoint {

    private static final Logger logger = LoggerFactory.getLogger(FloodEndpoint.class);

    @Autowired
    FloodService floodService;

    @GetMapping(value = "/stations")
    ResponseEntity<FloodDTO> getInfoFromPersonsCoveredByStations(@RequestParam(name = "stations") List<Integer> stations) {
        logger.info("List of the persons covered by one of the stations number {} with their medical history :", stations);
        final FloodDTO response = floodService.getInfoFromPersonsCoveredByStations(stations);

        for (final CoveredHouseResponse c : response.getCoveredHouses()) {
            logger.info("{}", c.getAddress());
            for (final InhabitantResponse i : c.getInhabitants()) {
                logger.info("{} {} {} {} {}", i.getFirstName(), i.getLastName(), i.getPhone(), i.getAge(), i.getMedicalHistory());
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
