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

import com.safetynet.alerts.dto.resource.ChildAlertDTO;
import com.safetynet.alerts.dto.response.ChildResponse;
import com.safetynet.alerts.dto.response.FamilyResponse;
import com.safetynet.alerts.repo.DataRepository;
import com.safetynet.alerts.util.JsonDataRepositoryTestUtil;
import com.safetynet.alerts.util.TestRepository;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(SpringExtension.class)
public class ChildAlertServiceUnitTest {

    ChildAlertService service;

    DataRepository repo = new JsonDataRepositoryTestUtil();

    @BeforeEach
    public void setup() throws Exception {
        TestRepository.jsonDataInitializer();
        service = new ChildAlertService(repo);
    }

    @Test
    public void givenExistingAddress_whenGetChildrenAtAddress_thenDTOIsAsExpected() {
        // given
        final String address = "15 Culver St";
        final ChildAlertDTO expectedDTO = new ChildAlertDTO();
        final List<FamilyResponse> family = new ArrayList<>();
        final FamilyResponse member = new FamilyResponse();
        member.setFirstName("Little");
        member.setLastName("Child");
        family.add(member);
        final List<ChildResponse> children = new ArrayList<>();
        final ChildResponse child = new ChildResponse();
        child.setFirstName("Little");
        child.setLastName("Child");
        child.setAge(13);
        child.setFamily(family);
        children.add(child);
        expectedDTO.setChildren(children);
        // when
        final ChildAlertDTO actualDTO = service.getChildrenAtAddress(address);
        // then
        assertThat(actualDTO).usingRecursiveComparison().isEqualTo(expectedDTO);
    }

    @Test
    public void givenUnknownAddress_whenGetChildrenAtAddress_thenDTOIsEmpty() {
        // given
        final String unknownAddress = "unknown address";
        // when
        final ChildAlertDTO actualDTO = service.getChildrenAtAddress(unknownAddress);
        // then
        assertThat(actualDTO.getChildren()).isEmpty();
    }

}
