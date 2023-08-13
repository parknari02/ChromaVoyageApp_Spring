package com.spring.chromavoyage.api.place.controller;

import com.spring.chromavoyage.api.place.dto.PlaceDTO;
import com.spring.chromavoyage.api.place.entity.PlaceEntity;
import com.spring.chromavoyage.api.place.service.PlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/places")
@RequiredArgsConstructor
public class PlaceController {
    // 생성자 주입
    private PlaceService placeService;

    @PostMapping("/save")
    public ResponseEntity<?> savePlace(@RequestBody PlaceDTO placeDTO) {
        // 장소 저장
        placeService.save(placeDTO);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/totalList")
    public ResponseEntity<List<PlaceEntity>> findAll() {
        // 장소 모두 조회
        List<PlaceEntity> placesList = placeService.findAll();
        return ResponseEntity.ok(placesList);
    }

    @GetMapping("/list}")
    public ResponseEntity<?> searchPlace( @RequestParam("group_id") Long groupId, @RequestParam("coloring_location_id") Long coloringLocationId)
    {
        List<PlaceEntity> places = placeService.findPlaceByGroupIdAndColoringLocationId(groupId, coloringLocationId);

        if (!places.isEmpty()) {
            return ResponseEntity.ok(places);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No places found.");
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteByPlaceName(@RequestParam String placeName) {
        placeService.deleteByPlaceName(placeName);
        return ResponseEntity.ok("Place deleted.");
    }

}