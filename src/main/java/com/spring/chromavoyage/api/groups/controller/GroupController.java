package com.spring.chromavoyage.api.groups.controller;

import com.spring.chromavoyage.api.groups.entity.Group;
import com.spring.chromavoyage.api.groups.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/groups") // "/groups" 경로를 컨트롤러 레벨에서 지정
public class GroupController {

    @Autowired
    private GroupService groupService;

    @PostMapping("/create") // "/groups/create" 경로를 POST 요청으로 처리
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

            // 그룹 생성 및 초대할 사용자 등록
            long groupId = groupService.createGroup(request.getGroup_name(), invitedUserIds);

            // 성공 응답
            return ResponseEntity.status(HttpStatus.CREATED).body(response("201", "Group Created Successfully", groupId));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response("500", "Internal Server Error"));
        }
    }

    // 응답 메시지를 생성하는 helper 메소드
    private Map<String, Object> response(String responseCode, String description, long groupId) {
        Map<String, Object> response = new HashMap<>();
        response.put("ResponseCode", responseCode);
        response.put("description", description);
        response.put("group_id", groupId);
        return response;
    }

    private Map<String, Object> response(String responseCode, String description) {
        Map<String, Object> response = new HashMap<>();
        response.put("ResponseCode", responseCode);
        response.put("description", description);
        return response;
    }
}
