package com.spring.chromavoyage.api.groups.service;

import com.spring.chromavoyage.api.groups.entity.Group;
import com.spring.chromavoyage.api.groups.entity.GroupMember;
import com.spring.chromavoyage.api.groups.entity.User;
import com.spring.chromavoyage.api.groups.repository.GroupMemberRepository;
import com.spring.chromavoyage.api.groups.repository.GroupRepository;
import com.spring.chromavoyage.api.groups.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class GroupService {
    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupMemberRepository groupMemberRepository;

    // 그룹 생성 및 초대할 사용자 등록 메서드
    public long createGroup(String groupName, List<Long> invitedUserIds) {
        // 그룹 생성
        Group newGroup = new Group(groupName, LocalDateTime.now(), false);
        groupRepository.save(newGroup);

        // 초대할 사용자 등록
        List<User> invitedUsers = new ArrayList<>();
        for (Long userId : invitedUserIds) {
            User invitedUser = userRepository.findById(userId).orElse(null);
            if (invitedUser != null) {
                invitedUsers.add(invitedUser);
            }
        }

        for (User user : invitedUsers) {
            GroupMember groupMember = new GroupMember(newGroup, user);
            groupMemberRepository.save(groupMember);
        }

        return newGroup.getGroupId();
    }

    // 사용자들이 모두 존재하는지 확인하는 메서드 구현
    public boolean areAllUsersExist(List<Long> userIds) {
        List<User> existingUsers = userRepository.findAllById(userIds);
        return existingUsers.size() == userIds.size();
    }
}
