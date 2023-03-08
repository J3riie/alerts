package com.safetynet.alerts.util;

import com.safetynet.alerts.dto.node.FireStationsDTO;

public class NodeConstructorTestUtil {

    public FireStationsDTO createValidFireStationsDTO() {
        final FireStationsDTO fireStationsDTO = new FireStationsDTO();
        fireStationsDTO.setAddress("default address");
        fireStationsDTO.setStation(1);
        return fireStationsDTO;
    }

    public FireStationsDTO createCustomFireStationsDTO(String address) {
        final FireStationsDTO fireStationsDTO = new FireStationsDTO();
        fireStationsDTO.setAddress(address);
        fireStationsDTO.setStation(1);
        return fireStationsDTO;
    }

    public FireStationsDTO createCustomFireStationsDTO(Integer stationNumber) {
        final FireStationsDTO fireStationsDTO = new FireStationsDTO();
        fireStationsDTO.setAddress("default address");
        fireStationsDTO.setStation(stationNumber);
        return fireStationsDTO;
    }

    public FireStationsDTO createCustomFireStationsDTO(String address, Integer stationNumber) {
        final FireStationsDTO fireStationsDTO = new FireStationsDTO();
        fireStationsDTO.setAddress(address);
        fireStationsDTO.setStation(stationNumber);
        return fireStationsDTO;
    }
}
