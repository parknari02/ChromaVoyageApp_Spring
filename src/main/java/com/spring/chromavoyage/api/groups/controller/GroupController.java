package com.spring.chromavoyage.api.groups.controller;

import com.spring.chromavoyage.api.groups.entity.Group;
import com.spring.chromavoyage.api.groups.entity.GroupMember;
import com.spring.chromavoyage.api.groups.repository.GroupMemberRepository;
import com.spring.chromavoyage.api.groups.repository.GroupRepository;
import com.spring.chromavoyage.api.groups.service.GroupService;
import com.spring.chromavoyage.api.groups.service.UserInvitationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/groups")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @Autowired // GroupRepository를 주입받을 수 있도록 추가
    private GroupRepository groupRepository;

    @Autowired
    private GroupMemberRepository groupMemberRepository;

    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> createGroup(@RequestBody CreateGroupRequest request) {
        try {
            // 필수 파라미터인 그룹 이름과 초대할 사용자 정보가 입력되었는지 확인
            if (request.getGroup_name() == null || request.getGroup_name().isEmpty() || request.getInvited_users().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response("400", "Bad Request"));
            }

            // 사용자 정보를 확인하고 존재하지 않는 사용자가 있는지 확인
            List<Long> invitedUserIds = request.getInvited_users().stream()
                    .map(InvitedUser::getUser_id)
                    .collect(Collectors.toList());

            if (!groupService.areAllUsersExist(invitedUserIds)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response("403", "Forbidden"));
            }

            if (groupService.isGroupNameExists(request.getGroup_name())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(response("409", "Conflict"));
            }

            // 그룹 생성 및 초대할 사용자 등록
            long groupId = groupService.createGroup(request.getGroup_name(), invitedUserIds);

            // 성공 응답
            return ResponseEntity.status(HttpStatus.CREATED).body(response("201", "Group Created Successfully", groupId));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response("500", "Internal Server Error"));
        }
    }

    @PostMapping("/invite/{group_id}")
    public ResponseEntity<Map<String, Object>> inviteUserToGroup(
            @PathVariable("group_id") Long groupId,
            @RequestBody InviteUserRequest request) {
        try {
            // 필수 파라미터인 이메일 정보가 입력되었는지 확인
            if (request.getEmail() == null || request.getEmail().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response("400", "Bad Request"));
            }

            // 초대할 사용자 정보가 유효한지 확인
            GroupInvitationResponse groupInvitationResponse = groupService.inviteUserToGroup(groupId, request.getEmail());

            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("responseCode", "201");
            responseBody.put("description", "User Invited Successfully");
            responseBody.put("userId", groupInvitationResponse.getUserId());
            responseBody.put("email", groupInvitationResponse.getEmail());
            responseBody.put("userName", groupInvitationResponse.getUserName());
            responseBody.put("profileImagePath", groupInvitationResponse.getProfileImagePath());

            // 초대 성공 응답
            return ResponseEntity.status(HttpStatus.CREATED).body(responseBody);
        } catch (UserInvitationException e) {
            // 예외 처리 - 해당 예외에 따라 적절한 응답을 반환하도록 구현
            String responseCode = e.getResponseCode();
            String description = e.getDescription();
            return ResponseEntity.status(e.getHttpStatus()).body(response(responseCode, description));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response("500", "Internal Server Error"));
        }
    }

    @PatchMapping("/{group_id}")
    public ResponseEntity<Map<String, Object>> setGroupAsFavorite(
            @PathVariable("group_id") Long groupId,
            @RequestBody SetFavoriteRequest request) {
        try {
            // 그룹이 존재하는지 확인합니다.
            Group group = groupRepository.findById(groupId)
                    .orElseThrow(() -> new UserInvitationException("403", "Forbidden"));

            // 권한 확인 로직이 있다면 추가할 수 있습니다. (예: 사용자가 그룹에 속해있는지 확인 등)

            // 즐겨찾기 설정을 업데이트합니다.
            group.setPin(request.getPin());
            groupRepository.save(group);

            // 성공 응답
            return ResponseEntity.status(HttpStatus.OK).body(response("200", "Successfully Saved"));
        } catch (UserInvitationException e) {
            // 예외 처리 - 해당 예외에 따라 적절한 응답을 반환하도록 구현
            String responseCode = e.getResponseCode();
            String description = e.getDescription();
            return ResponseEntity.status(e.getHttpStatus()).body(response(responseCode, description));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response("500", "Internal Server Error"));
        }
    }

    @DeleteMapping("/{group_id}")
    public ResponseEntity<Map<String, Object>> deleteGroup(
            @PathVariable("group_id") Long groupId) {
        try {
            // 그룹이 존재하는지 확인합니다.
            Group group = groupRepository.findById(groupId)
                    .orElseThrow(() -> new UserInvitationException("403", "Forbidden"));

            // "groupmember" 테이블에서 해당 그룹과 관련된 데이터를 먼저 삭제합니다.
            List<GroupMember> groupMembers = groupMemberRepository.findByGroup(group);
            groupMemberRepository.deleteAll(groupMembers);

            // 그룹을 삭제합니다.
            groupRepository.delete(group);

            // 성공 응답
            return ResponseEntity.status(HttpStatus.OK).body(response("200", "Group Deleted Successfully"));
        } catch (UserInvitationException e) {
            // 예외 처리 - 해당 예외에 따라 적절한 응답을 반환하도록 구현
            String responseCode = e.getResponseCode();
            String description = e.getDescription();
            return ResponseEntity.status(e.getHttpStatus()).body(response(responseCode, description));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response("500", "Internal Server Error"));
        }
    }

    // 응답 메시지를 생성하는 helper 메소드
    private Map<String, Object> response(String responseCode, String description, Object data) {
        Map<String, Object> response = new HashMap<>();
        response.put("ResponseCode", responseCode);
        response.put("description", description);
        response.put("group_id", data);
        return response;
    }

    private Map<String, Object> response(String responseCode, String description) {
        Map<String, Object> response = new HashMap<>();
        response.put("ResponseCode", responseCode);
        response.put("description", description);
        return response;
    }
}

