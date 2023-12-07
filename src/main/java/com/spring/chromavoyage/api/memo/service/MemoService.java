package com.spring.chromavoyage.api.memo.service;


import com.spring.chromavoyage.api.memo.dto.MemoDTO;
import com.spring.chromavoyage.api.memo.entitiy.MemoEntity;
import com.spring.chromavoyage.api.memo.repository.MemoRepository;
import com.spring.chromavoyage.api.place.entity.PlaceEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemoService {

    @Autowired
    private MemoRepository memoRepository;

    public void save(MemoDTO memoDTO) {
        MemoEntity memoEntity = new MemoEntity();
        memoEntity.setMemoContent(memoDTO.getMemoContent());
        memoEntity.setColoringLocationId((memoDTO.getColoringLocationId()));
        memoEntity.setGroupId(memoDTO.getGroupId());
        memoEntity.setLocationId(memoDTO.getLocationId());
        memoEntity.setStartTime(memoDTO.getStartTime());
        memoEntity.setEndTime(memoDTO.getEndTime());
        memoEntity.setMemoDate(memoDTO.getMemoDate());
        memoEntity.setCreatedDate(LocalDateTime.now());
        memoRepository.save(memoEntity);
    }

    public List<MemoEntity> findAll() {
        return memoRepository.findAll();
    }

    public List<MemoEntity> findPlaceByGroupIdAndColoringLocationId(Long groupId, Long coloringLocationId) {
        return memoRepository.findByGroupIdAndColoringLocationId(groupId, coloringLocationId);

    }
}
