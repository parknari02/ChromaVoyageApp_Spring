package com.spring.chromavoyage.api.location.controller;

import com.spring.chromavoyage.api.groups.entity.ColoringLocation;
import com.spring.chromavoyage.api.groups.entity.GroupMember;
import com.spring.chromavoyage.api.groups.repository.ColoringLocationRepository;
import com.spring.chromavoyage.api.groups.repository.GroupMemberRepository;
import com.spring.chromavoyage.api.location.domain.AddLocationsRequest;
import com.spring.chromavoyage.api.location.domain.FindLocationsRequest;
import com.spring.chromavoyage.api.location.entity.Location;
import com.spring.chromavoyage.api.location.entity.UserColoring;
import com.spring.chromavoyage.api.location.repository.LocationRepository;
import com.spring.chromavoyage.api.location.repository.UserColoringRepository;
import com.spring.chromavoyage.api.location.service.FindService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/locations")
public class LocationController {
    @Autowired
    private FindService findService;
    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private ColoringLocationRepository coloringLocationRepository;
    @Autowired
    private GroupMemberRepository groupMemberRepository;
    @Autowired
    private UserColoringRepository userColoringRepository;

    @PostMapping(" ")
    public ResponseEntity<List<String>> findLocations(@RequestBody FindLocationsRequest request) {
        return ResponseEntity.ok().body(findService.findLocations(request.getUserId()));

    }

    @PostMapping("/group")
    public ResponseEntity<List<String>> findGroupLocations(@RequestParam("group_id") Long groupId) {
        return ResponseEntity.ok().body(findService.findGroupLocations(groupId));

    }

    @PostMapping("/add")
    public ResponseEntity<List<Map<String, Long>>> addLocations(@RequestBody AddLocationsRequest request, @RequestParam("group_id") Long groupId){
        List<Map<String, Long>> locationIdsList = new ArrayList<>();
        List<GroupMember> groupMembers = groupMemberRepository.findGroupMemberByGroupId(groupId);

        for(String locationName : request.getLocationName()){
            Location location = locationRepository.findLocationByLocationName(locationName);
            if(location != null) { // Check if location is found

                ColoringLocation coloringLocation = new ColoringLocation();
                coloringLocation.setLocationId(location.getLocationId());
                coloringLocation.setStartDate(request.getStartDate());
                coloringLocation.setEndDate(request.getEndDate());
                coloringLocation.setGroupId(groupId);

                // Save the entity and get the saved entity
                coloringLocation = coloringLocationRepository.save(coloringLocation);

                // Get the coloringLocationId from the saved entity
                Long coloringLocationId = coloringLocation.getColoringLocationId();

                for(GroupMember groupMember : groupMembers){
                    Long userId = groupMember.getUserId();
                    List<UserColoring> userColorings = userColoringRepository.findUserColoringByUserId(userId);
                    List<Long> locationIds = new ArrayList<>();
                    for(UserColoring userColoring : userColorings){
                        locationIds.add(userColoring.getLocationId());
                    }
                    if(!locationIds.contains(location.getLocationId())){
                        UserColoring userColoring = new UserColoring();
                        userColoring.setLocationId(location.getLocationId());
                        userColoring.setUserId(userId);
                        userColoringRepository.save(userColoring);
                    }
                }

                // Create a map to store coloringLocationId and locationId
                Map<String, Long> locationIdsMap = new HashMap<>();
                locationIdsMap.put("coloringLocationId", coloringLocationId);
                locationIdsMap.put("locationId", location.getLocationId());
                locationIdsList.add(locationIdsMap);
            }
        }

        // Return the list of coloringLocationIds and locationIds to the client
        return ResponseEntity.ok().body(locationIdsList);
    }


}
