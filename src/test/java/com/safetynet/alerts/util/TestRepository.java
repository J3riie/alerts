package com.safetynet.alerts.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.dto.node.DataJson;
import com.safetynet.alerts.dto.node.FireStationsDTO;
import com.safetynet.alerts.dto.node.MedicalRecordsDTO;
import com.safetynet.alerts.dto.node.PersonsDTO;

public class TestRepository {

    private static final Logger logger = LoggerFactory.getLogger(TestRepository.class);

    private static DataJson dataJson;

    public static void jsonDataInitializer() throws Exception {
        final Resource resource = new ClassPathResource("data.json");
        final ObjectMapper mapper = new ObjectMapper();
        final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        mapper.setDateFormat(sdf);
        try {
            setDataJson(mapper.readValue(resource.getFile(), DataJson.class));
        } catch (final IOException e) {
            logger.error("Json file not found", e);
        }
    }

    public static DataJson getDataJson() {
        return dataJson;
    }

    private static void setDataJson(DataJson dataJson) {
        TestRepository.dataJson = dataJson;
    }

    public static List<FireStationsDTO> getFirestations() {
        return dataJson.getFirestations();
    }

    public static List<PersonsDTO> getPersons() {
        return dataJson.getPersons();
    }

    public static List<MedicalRecordsDTO> getMedicalRecords() {
        return dataJson.getMedicalrecords();
    }
}
