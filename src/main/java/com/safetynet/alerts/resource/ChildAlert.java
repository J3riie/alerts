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

import com.safetynet.alerts.dto.resource.ChildAlertDTO;
import com.safetynet.alerts.dto.response.APIResponse;
import com.safetynet.alerts.dto.response.ChildResponse;
import com.safetynet.alerts.dto.response.FamilyResponse;
import com.safetynet.alerts.service.resource.ChildAlertService;

@RestController
@RequestMapping(value = "/childAlert", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class ChildAlert {

    private static final Logger logger = LogManager.getLogger(ChildAlert.class);

    @Autowired
    ChildAlertService childAlertService;

    @GetMapping
    APIResponse<ChildAlertDTO> index(@RequestParam(name = "address") String address) {
        logger.info("List of the children living at {} and their family :", address);
        final ArrayList<FamilyResponse> family = childAlertService.getFamilyMembersFromAddress(address);
        final ArrayList<ChildResponse> children = childAlertService.getChildrenFromFamily(family);

        final ChildAlertDTO response = new ChildAlertDTO();
        response.setChildren(children);

        for (final ChildResponse c : children) {
            logger.info("{} {} {}", c.getFirstName(), c.getLastName(), c.getAge());
            logger.info("Family :");
            for (final FamilyResponse f : c.getFamily()) {
                logger.info("{} {}", f.getFirstName(), f.getLastName());
            }
        }
        return new APIResponse<>(HttpStatus.OK.value(), String.format("List of the children living at %s and their family", address), response);
    }
}
