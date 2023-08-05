package com.spring.chromavoyage.api.groups.service;

import com.spring.chromavoyage.api.groups.controller.InviteUserResponse;
import com.spring.chromavoyage.api.groups.entity.Group;
import com.spring.chromavoyage.api.groups.entity.GroupMember;
import com.spring.chromavoyage.api.groups.entity.User;
import com.spring.chromavoyage.api.groups.repository.GroupMemberRepository;
import com.spring.chromavoyage.api.groups.repository.GroupRepository;
import com.spring.chromavoyage.api.groups.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Autowired
    private UserService userService;

    // 그룹 생성 및 초대할 사용자 등록 메서드
    public long createGroup(String groupName, List<String> invitedEmails) {
        // 그룹 생성


        Group newGroup = new Group();
        newGroup.setGroupName(groupName);
        newGroup.setCreatedDate(LocalDateTime.now());
        newGroup.setPin(false); // Replace "your_pin_value" with the actual pin value you want to set
        groupRepository.save(newGroup);

        // 초대할 사용자 이메일로 사용자 ID를 조회하여 등록
        List<User> invitedUsers = new ArrayList<>();
        for (String email : invitedEmails) {
            Long userId = userService.getUserIdByEmail(email);
            if (userId != null) {
                User invitedUser = userRepository.findById(userId).orElse(null);
                if (invitedUser != null) {
                    invitedUsers.add(invitedUser);
                }
            }
        }

        for (User user : invitedUsers) {
            GroupMember groupMember = new GroupMember(newGroup.getGroupId(), user.getUserId());
            groupMemberRepository.save(groupMember);
        }



        return newGroup.getGroupId();
    }


    public boolean isGroupNameExists(String groupName) {
        return groupRepository.existsByGroupName(groupName);
    }

    public InviteUserResponse inviteUserToGroup(Long groupId, String email) throws UserInvitationException {

        // 해당 그룹과 사용자 정보를 확인하여 초대 가능한지 여부를 판단하고 처리하는 로직을 구현합니다.
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new UserInvitationException("404", "Group not found"));

        User invitedUser = userRepository.findByEmail(email);
        if (invitedUser == null) {
            throw new UserInvitationException("404", "User not found");
        }

        if (isAlreadyMember(groupId, invitedUser.getUserId())) {
            throw new UserInvitationException("409", "User is already a member of the group");
        }

        // 초대 성공 시, 초대한 사용자의 정보를 응답 데이터로 포함하여 반환합니다.
        GroupMember groupMember = new GroupMember(group.getGroupId(), invitedUser.getUserId());
        groupMemberRepository.save(groupMember);

        InviteUserResponse response = new InviteUserResponse();
        response.setUserId(invitedUser.getUserId());
        response.setEmail(email);
        response.setUserName(invitedUser.getUsername());
        response.setProfileImagePath(invitedUser.getPicture());

        return response;
    }

    private boolean isAlreadyMember(Long groupId, Long userId) {
        return groupMemberRepository.existsByGroupIdAndUserId(groupId, userId);
    }


}
