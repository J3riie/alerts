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

import com.safetynet.alerts.dto.resource.PhoneAlertDTO;
import com.safetynet.alerts.repository.DataRepository;
import com.safetynet.alerts.util.JsonDataRepositoryTestUtil;
import com.safetynet.alerts.util.TestRepository;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(SpringExtension.class)
public class PhoneAlertServiceUnitTest {

    PhoneAlertService service;

    DataRepository repo = new JsonDataRepositoryTestUtil();

    @BeforeEach
    public void setup() throws Exception {
        TestRepository.jsonDataInitializer();
        service = new PhoneAlertService(repo);
    }

    @Test
    public void givenExistingStation_whenGetPhoneNumbersFromPersonsCoveredByStation_thenDTOIsAsExpected() {
        // given
        final int station = 2;
        final PhoneAlertDTO expectedDTO = new PhoneAlertDTO();
        final List<String> phones = new ArrayList<>();
        phones.add("7777");
        expectedDTO.setPhones(phones);
        // when
        final PhoneAlertDTO actualDTO = service.getPhoneNumbersFromPersonsCoveredByStation(station);
        // then
        assertThat(actualDTO).usingRecursiveComparison().isEqualTo(expectedDTO);
    }

    @Test
    public void givenUnknownStation_whenGetPhoneNumbersFromPersonsCoveredByStation_thenDTOIsEmpty() {
        // given
        final int unknownStation = 10;
        // when
        final PhoneAlertDTO actualDTO = service.getPhoneNumbersFromPersonsCoveredByStation(unknownStation);
        // then
        assertThat(actualDTO.getPhones()).isEmpty();
    }

}
