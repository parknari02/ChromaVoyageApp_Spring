package com.spring.chromavoyage.api.location.controller;

import com.spring.chromavoyage.api.location.domain.FindLocationsRequest;
import com.spring.chromavoyage.api.location.service.FindService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/locations")
public class LocationController {
    @Autowired
    private FindService findService;

    @PostMapping(" ")
    public ResponseEntity<List<String>> findLocations(@RequestBody FindLocationsRequest request) {
        return ResponseEntity.ok().body(findService.findLocations(request.getUserId()));

    }

    @PostMapping("/group")
    public ResponseEntity<List<String>> findGroupLocations(@RequestParam("group_id") Long groupId) {
        return ResponseEntity.ok().body(findService.findGroupLocations(groupId));

    }
}
