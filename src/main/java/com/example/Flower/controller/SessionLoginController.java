package com.example.Flower.controller;

import com.example.Flower.dto.LoginRequest;
import com.example.Flower.entity.User;
import com.example.Flower.entity.UserRole;
import com.example.Flower.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/session-login") // 기본 URL을 /session-login으로 설정
public class SessionLoginController extends SessionCheckController{

    @Autowired // UserService Bean 객체를 주입
    private UserService userService;

    // 홈 페이지로 이동
    @GetMapping(value = {"", "/"}) // "http://localhost:8081/session-login"으로 접근 시 실행
    public String home(Model model){
        return "sessionLogin/home"; // sessionLogin/home 템플릿을 반환
    }

    // 회원가입 페이지로 이동
    @GetMapping("/join")
    public String joinPage(Model model) {
        return "sessionLogin/join"; // sessionLogin/join 템플릿을 반환
    }

    // 로그인 페이지로 이동
    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("loginRequest", new LoginRequest()); // 로그인 요청 객체를 모델에 추가
        return "sessionLogin/login"; // sessionLogin/login 템플릿을 반환
    }

    // 로그인 처리
    @PostMapping("/login")
    public String login(@ModelAttribute LoginRequest loginRequest,
                        HttpServletRequest httpServletRequest, Model model) {
        User user = userService.login(loginRequest); // 로그인 서비스 호출

        httpServletRequest.getSession().invalidate(); // 기존 세션 무효화
        HttpSession session = httpServletRequest.getSession(true); // 새로운 세션 생성
        session.setAttribute("userId", user.getId()); // 세션에 사용자 ID 저장
        session.setMaxInactiveInterval(1800); // 세션 유효 시간 30분 설정
        return "redirect:/session-login"; // 홈 페이지로 리다이렉트
    }

    // 사용자 정보 페이지로 이동
    @GetMapping("/info")
    public String userInfo(@SessionAttribute(name="userId", required = false) Long userId, Model model) {
        User loginUser = userService.getLoginUserById(userId); // 로그인한 사용자 정보 조회
        if (loginUser == null) {
            return "redirect:/session-login/login"; // 로그인되지 않았으면 로그인 페이지로 리다이렉트
        }
        model.addAttribute("user", loginUser); // 사용자 정보를 모델에 추가
        return "sessionLogin/info"; // sessionLogin/info 템플릿을 반환
    }

    // 로그아웃 처리
    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false); // 세션이 존재하면 가져옴
        if (session != null) {
            session.invalidate(); // 세션 무효화
        }
        return "redirect:/"; // 홈 페이지로 리다이렉트
    }

    // 마이 페이지로 이동
    @GetMapping("/mypage")
    public String myPage(Model model) {
        return "sessionLogin/mypage"; // sessionLogin/mypage 템플릿을 반환
    }

    // 관리자 페이지로 이동
    @GetMapping("/admin")
    public String adminPage(@SessionAttribute(name="userId", required = false) Long userId, Model model) {
        User loginUser = userService.getLoginUserById(userId); // 로그인한 사용자 정보 조회
        if (loginUser == null) {
            return "redirect:/session-login/login"; // 로그인되지 않았으면 로그인 페이지로 리다이렉트
        }
        if (!loginUser.getRole().equals(UserRole.ADMIN)) { // 사용자가 관리자가 아니면
            return "redirect:/session-login/mypage"; // 마이 페이지로 리다이렉트
        }
        return "sessionLogin/admin"; // sessionLogin/admin 템플릿을 반환
    }

}