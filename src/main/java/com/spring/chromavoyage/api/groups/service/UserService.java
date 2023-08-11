package com.spring.chromavoyage.api.groups.service;

import com.spring.chromavoyage.api.groups.entity.User;
import com.spring.chromavoyage.api.groups.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Long getUserIdByEmail(String email) {
        User user = userRepository.findByEmail(email);
        return user != null ? user.getUserId() : null;
    }
}
