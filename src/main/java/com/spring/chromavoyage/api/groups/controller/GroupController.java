package com.spring.chromavoyage.api.groups.controller;

import com.spring.chromavoyage.api.groups.domain.*;
import com.spring.chromavoyage.api.groups.entity.Group;
import com.spring.chromavoyage.api.groups.entity.GroupMember;
import com.spring.chromavoyage.api.groups.repository.GroupMemberRepository;
import com.spring.chromavoyage.api.groups.repository.GroupRepository;
import com.spring.chromavoyage.api.groups.service.GroupService;
import com.spring.chromavoyage.api.groups.service.UserInvitationException;
import com.spring.chromavoyage.api.groups.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> createGroup(@RequestBody CreateGroupRequest request) {
        try {
            // 필수 파라미터인 그룹 이름과 초대할 사용자 이메일 정보가 입력되었는지 확인
            if (request.getGroup_name() == null || request.getGroup_name().isEmpty() || request.getInvited_emails().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response("400", "Bad Request"));
            }

            if (groupService.isGroupNameExists(request.getGroup_name())) {
                throw new UserInvitationException("409", "Conflict");
            }


            // 그룹 생성 및 초대할 사용자 등록
            long groupId = groupService.createGroup(request.getGroup_name(), request.getInvited_emails());

            // 성공 응답
            return ResponseEntity.status(HttpStatus.CREATED).body(response("201", "Group Created Successfully", groupId));
        } catch (UserInvitationException e) {
            // Handle the conflict response for existing group name
            String responseCode = e.getResponseCode();
            String description = e.getDescription();
            return ResponseEntity.status(e.getHttpStatus()).body(response(responseCode, description));
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
            InviteUserResponse inviteUserResponse = groupService.inviteUserToGroup(groupId, request.getEmail());

            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("responseCode", "201");
            responseBody.put("description", "User Invited Successfully");
            responseBody.put("userId", inviteUserResponse.getUserId()); // 수정된 부분
            responseBody.put("email", inviteUserResponse.getEmail());
            responseBody.put("userName", inviteUserResponse.getUserName());
            responseBody.put("profileImagePath", inviteUserResponse.getProfileImagePath());

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
                    .orElseThrow(() -> new UserInvitationException("404", "Not Found"));


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
                    .orElseThrow(() -> new UserInvitationException("404", "Not Found"));

            // "groupmember" 테이블에서 해당 그룹과 관련된 데이터를 먼저 삭제합니다.
            List<GroupMember> groupMembers = groupMemberRepository.findByGroupId(groupId);
            groupMemberRepository.deleteAll(groupMembers);

            // 그룹을 삭제합니다.
            groupRepository.delete(group);

            // 성공 응답
            return ResponseEntity.status(HttpStatus.OK).body(response("204", "Group Deleted Successfully"));
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

