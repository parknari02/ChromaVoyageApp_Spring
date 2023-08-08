package com.chromavoyage.api.place.controller;


import com.chromavoyage.api.place.entity.PlaceEntity;
import com.chromavoyage.api.place.service.PlaceService;
import com.chromavoyage.api.place.dto.PlaceDTO;
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
    private final PlaceService placeService;

    @PostMapping("/save")
    public ResponseEntity<?> savePlace(@RequestBody PlaceDTO placeDTO) {
        // 장소 저장
        placeService.save(placeDTO);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<List<PlaceEntity>> findAll() {
        // 장소 모두 조회
        List<PlaceEntity> placesList = placeService.findAll();
        return ResponseEntity.ok(placesList);
    }

    @GetMapping("/{group_id}/{coloring_location_id}")
    public ResponseEntity<?> searchPlace( @PathVariable("group_id") int groupId,
                                          @PathVariable("coloring_location_id") int coloringLocationId) {
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