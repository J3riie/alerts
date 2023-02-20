package com.safetynet.alerts.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.safetynet.alerts.App;
import com.safetynet.alerts.dto.node.FireStationsDTO;

@Service
public class FireStationService {

    public void addFireStation(FireStationsDTO firestation) {
        App.getFirestations().add(firestation);
    }

    public FireStationsDTO modifyFireStation(String address, Integer station) {
        return App.getFirestations().stream().filter(f -> f.addressMappingExists(address)).findFirst().map(f -> { f.setStation(station); return f; })
                .orElse(null);
    }

    public Optional<FireStationsDTO> deleteFireStation(String address) {
        final Optional<FireStationsDTO> optionalFirestation = App.getFirestations().stream().filter(f -> f.addressMappingExists(address)).findFirst();
        if (optionalFirestation.isPresent()) {
            App.getFirestations().remove(optionalFirestation.get());
        }
        return optionalFirestation;
    }

    public boolean deleteFireStation(Integer station) {
        Optional<FireStationsDTO> optionalFirestation = App.getFirestations().stream().filter(f -> f.stationMappingExists(station)).findFirst();
        boolean isPresent = false;
        while (optionalFirestation.isPresent()) {
            isPresent = true;
            App.getFirestations().remove(optionalFirestation.get());
            optionalFirestation = App.getFirestations().stream().filter(f -> f.stationMappingExists(station)).findFirst();
        }
        return isPresent;
    }
}
