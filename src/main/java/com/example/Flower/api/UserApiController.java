package com.example.Flower.api;

import com.example.Flower.dto.JoinRequest;
import com.example.Flower.dto.UserForm;
import com.example.Flower.entity.User;
import com.example.Flower.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/users") // 기본 경로 설정
public class UserApiController {

    @Autowired
    private UserService userService; // UserService 의존성 주입

    //admin페이지에서 직접 사용자 추가할 때
    @PostMapping("/create")
    public User createUser(@RequestBody UserForm form) {
        // 새로운 사용자를 생성
        log.info(form.toString());
        User user = form.toEntity();
        log.info(user.toString());
        return userService.saveUser(user);
    }

    //회원가입할 때
    @PostMapping("/join")
    public User joinUser(@RequestBody UserForm form) {
        // 회원가입
        log.info(form.toString());
        User user = form.toEntity();
        log.info(user.toString());
        return userService.saveUser(user);
    }

    @PostMapping("/update")
    public ResponseEntity<User> updateUser(@RequestParam Long id, @RequestBody UserForm form, HttpServletRequest request) {
        // 특정 사용자의 정보를 업데이트
        log.info("Updating user with ID: " + id);
        HttpSession session = request.getSession(false);
        Long userId = (Long) session.getAttribute("userId");

        if (userId == null) {
            throw new IllegalStateException("로그인이 필요합니다.");
        }

        User updatedUser = userService.updateUser(id, form);
        if (updatedUser == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedUser);
    }

    //사용자 id(loginId) 중복체크
    @GetMapping("/check-duplicate")
    public ResponseEntity<Map<String, Boolean>> checkDuplicateLoginId(@RequestParam String loginId) {
        boolean isDuplicate = userService.isLoginIdDuplicate(loginId);
        Map<String, Boolean> response = new HashMap<>();
        response.put("isDuplicate", isDuplicate);
        return ResponseEntity.ok(response);
    }
    //사용자 닉네임 중복체크
    @GetMapping("/check-nickname-duplicate")
    public ResponseEntity<Map<String, Boolean>> checkDuplicateNickname(@RequestParam String nickname) {
        boolean isDuplicate = userService.isNicknameDuplicate(nickname);
        Map<String, Boolean> response = new HashMap<>();
        response.put("isDuplicate", isDuplicate);
        return ResponseEntity.ok(response);
    }
}
