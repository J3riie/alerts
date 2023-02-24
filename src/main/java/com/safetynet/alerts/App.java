package com.safetynet.alerts;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.Resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.dto.node.DataJson;
import com.safetynet.alerts.dto.node.FireStationsDTO;
import com.safetynet.alerts.dto.node.MedicalRecordsDTO;
import com.safetynet.alerts.dto.node.PersonsDTO;

@SpringBootApplication
public class App implements CommandLineRunner {

    private static final Logger logger = LogManager.getLogger(App.class);

    private static DataJson dataJson;

    @Value("classpath:data.json")
    Resource resource;

    public static void main(String[] args) {
        logger.info("Starting app");
        SpringApplication.run(App.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
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
        App.dataJson = dataJson;
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