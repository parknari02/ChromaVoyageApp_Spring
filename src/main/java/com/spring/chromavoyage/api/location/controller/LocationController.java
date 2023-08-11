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
import java.util.List;

@RestController
@RequestMapping("/locations")
public class LocationController {
    @Autowired
    private FindService findService;
    @Autowired
    LocationRepository locationRepository;
    @Autowired
    ColoringLocationRepository coloringLocationRepository;
    @Autowired
    GroupMemberRepository groupMemberRepository;
    @Autowired
    UserColoringRepository userColoringRepository;

    @PostMapping(" ")
    public ResponseEntity<List<String>> findLocations(@RequestBody FindLocationsRequest request) {
        return ResponseEntity.ok().body(findService.findLocations(request.getUserId()));

    }

    @PostMapping("/group")
    public ResponseEntity<List<String>> findGroupLocations(@RequestParam("group_id") Long groupId) {
        return ResponseEntity.ok().body(findService.findGroupLocations(groupId));

    }

    @PostMapping("/add")
    public ResponseEntity addLocations(@RequestBody AddLocationsRequest request, @RequestParam("group_id") Long groupId){
        //location_name => location_id로 찾기
        List<Long> locationId = new ArrayList<>();
        List<GroupMember> groupMembers = groupMemberRepository.findByGroupId(groupId);

        for(String locationName : request.getLocationName()){
            Location location = locationRepository.findLocationByLocationName(locationName);
            locationId.add(location.getLocationId());
            ColoringLocation coloringLocation = new ColoringLocation();
            coloringLocation.setLocationId(location.getLocationId());
            coloringLocation.setStartDate(request.getStartDate());
            coloringLocation.setEndDate(request.getEndDate());
            coloringLocation.setGroupId(groupId);
            coloringLocationRepository.save(coloringLocation);

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
        }
        return ResponseEntity.ok().body(request);
    }
}
