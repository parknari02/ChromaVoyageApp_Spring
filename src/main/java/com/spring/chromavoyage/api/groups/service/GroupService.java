package com.spring.chromavoyage.api.groups.service;

import com.spring.chromavoyage.api.groups.controller.GroupInvitationResponse;
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

    public boolean isGroupNameExists(String groupName) {
        return groupRepository.existsByGroupName(groupName);
    }

    public GroupInvitationResponse inviteUserToGroup(Long groupId, String email) throws UserInvitationException {
        // 해당 그룹과 사용자 정보를 확인하여 초대 가능한지 여부를 판단하고 처리하는 로직을 구현합니다.
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new UserInvitationException("404", "Group not found"));

        User invitedUser = userRepository.findByEmail(email);
        if (invitedUser == null) {
            throw new UserInvitationException("404", "User not found");
        }

        if (isAlreadyMember(group, invitedUser)) {
            throw new UserInvitationException("409", "User is already a member of the group");
        }

        // 초대 성공 시, 초대한 사용자의 정보를 응답 데이터로 포함하여 반환합니다.
        GroupMember groupMember = new GroupMember(group, invitedUser);
        groupMemberRepository.save(groupMember);

        GroupInvitationResponse response = new GroupInvitationResponse();
        response.setUserId(invitedUser.getUserId());
        response.setEmail(invitedUser.getEmail());
        response.setUserName(invitedUser.getUserName());
        response.setProfileImagePath(invitedUser.getPicture());
        return response;
    }

    private boolean isAlreadyMember(Group group, User user) {
        // 해당 그룹에 이미 해당 사용자가 멤버로 속해있는지 여부를 확인하는 로직을 구현합니다.
        // 예를 들어, groupMemberRepository를 사용하여 확인할 수 있습니다.
        // (이 예시에서는 groupMemberRepository가 선언되어 있다고 가정합니다.)
        return groupMemberRepository.existsByGroupAndUser(group, user);
    }


}
