package com.safetynet.alerts.resource;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.alerts.dto.resource.FireDTO;
import com.safetynet.alerts.dto.response.APIResponse;
import com.safetynet.alerts.dto.response.InhabitantResponse;
import com.safetynet.alerts.service.resource.FireService;

@RestController
@RequestMapping(value = "/fire", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class Fire {

    private static final Logger logger = LogManager.getLogger(Fire.class);

    @Autowired
    FireService fireService;

    @GetMapping
    APIResponse<FireDTO> index(@RequestParam(name = "address") String address) {
        logger.info("List of the persons living at {}, their medical history and the station covering them :", address);

        final int stationNumber = fireService.getFirestationNumberFromAddress(address);
        final ArrayList<InhabitantResponse> inhabitants = fireService.getInfoFromAddress(address);
        fireService.setPersonsMedicalHistory(inhabitants);

        final FireDTO response = new FireDTO();
        response.setInhabitants(inhabitants);
        response.setCoveringStation(stationNumber);

        for (final InhabitantResponse i : inhabitants) {
            logger.info("{} {} {} {} {}", i.getFirstName(), i.getLastName(), i.getPhone(), i.getAge(), i.getMedicalHistory());
        }
        if (stationNumber == -1) {
            logger.error("The referenced address isn't covered by any station");
        } else {
            logger.info("Firestation serving {} is the station number {}.", address, stationNumber);
        }
        return new APIResponse<>(HttpStatus.OK.value(),
                String.format("List of the persons living at %s, their medical history and the station covering them", address), response);
    }

}
