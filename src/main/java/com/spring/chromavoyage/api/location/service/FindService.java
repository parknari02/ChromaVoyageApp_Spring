package com.spring.chromavoyage.api.location.service;

import com.spring.chromavoyage.api.location.entity.ColoringLocation;
import com.spring.chromavoyage.api.location.entity.UserColoring;
import com.spring.chromavoyage.api.location.repository.ColoringLocationRepository;
import com.spring.chromavoyage.api.location.repository.LocationRepository;
import com.spring.chromavoyage.api.location.repository.UserColoringRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FindService {
    @Autowired
    private ColoringLocationRepository coloringLocationRepository;
    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private UserColoringRepository userColoringRepository;

    public List<String> findLocations(Long userId){
        List<UserColoring> locations = userColoringRepository.findUserColoringByUserId(userId);
        List<String> locationsName = new ArrayList<>();
        for (UserColoring location : locations) {
            Long locationId = location.getLocationId();
            if (!locationsName.contains(locationRepository.findLocationByLocationId(locationId).getLocationName())) {
                locationsName.add(locationRepository.findLocationByLocationId(locationId).getLocationName());
            }
        }
        return locationsName;
    }

    public List<String> findGroupLocations(Long groupId){
        List<ColoringLocation> locations = coloringLocationRepository.findColoringPlaceByGroupId(groupId);
        List<String> locationsName = new ArrayList<>();
        for (ColoringLocation location : locations) {
            Long locationId = location.getLocationId();
            if (!locationsName.contains(locationRepository.findLocationByLocationId(locationId).getLocationName())) {
                locationsName.add(locationRepository.findLocationByLocationId(locationId).getLocationName());
            }
        }
        return locationsName;
    }
}
