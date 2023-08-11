package com.spring.chromavoyage.api.profiles.service;

import com.spring.chromavoyage.api.profiles.entity.ProfileEntity;
import com.spring.chromavoyage.api.profiles.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProfileService {
    @Autowired
    private final ProfileRepository profileRepository;

    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public ProfileEntity updateProfileName(Long userId, String userName) {
        ProfileEntity profile = profileRepository.findByUserId(userId);
        profile.setUser_name(userName);
        profileRepository.save(profile);
        return profile;
    }
    public ProfileEntity updateProfileImg(Long userId, String file_path) {
        ProfileEntity profile = profileRepository.findByUserId(userId);
        profile.setProfileImg_path(file_path);
        profileRepository.save(profile);
        return profile;
    }

    public void deleteProfileImg(Long userId) {
        ProfileEntity profile = profileRepository.findByUserId(userId);
        profile.setProfileImg_path(null);
        profileRepository.save(profile);
    }

}
