package com.spring.chromavoyage.api.memo.controller;


import com.spring.chromavoyage.api.memo.dto.MemoDTO;
import com.spring.chromavoyage.api.memo.entitiy.MemoEntity;
import com.spring.chromavoyage.api.memo.service.MemoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/memos")
@RequiredArgsConstructor
public class MemoController {

    private final MemoService memoService;

    @PostMapping("/save")
    public ResponseEntity<?> saveMemo(@RequestBody MemoDTO memoDTO) {

        memoService.save(memoDTO);
        return ResponseEntity.ok("Memo saved.");
    }

    @GetMapping("/list")
    public ResponseEntity<?> searchMemo(@RequestParam("group_id") Long groupId, @RequestParam("coloring_location_id") Long coloringLocationId)
    {
        List<MemoEntity> memos = memoService.findPlaceByGroupIdAndColoringLocationId(groupId, coloringLocationId);

        if (!memos.isEmpty()) {
            return ResponseEntity.ok(memos);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No places found.");
        }
    }
}
