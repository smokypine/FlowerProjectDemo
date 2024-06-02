package com.example.Flower.controller;

import com.example.Flower.entity.Users;
import com.example.Flower.repository.UsersRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    @Autowired
    private UsersRepository userRepository;

    // 로그인 페이지로 이동
    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    // 로그인 처리
    @PostMapping("/login")
    public String login(HttpServletRequest request, Model model) {
        String userId = request.getParameter("userId");
        String password = request.getParameter("password");

        // 데이터베이스에서 사용자 정보를 가져와서 비교
        Users user = userRepository.findByUserId(userId);
        if (user != null && user.getPassword().equals(password)) {
            HttpSession session = request.getSession();
            session.setAttribute("userId", userId);
            return "redirect:/"; // 로그인 성공 시 홈 페이지로 리다이렉트
        } else {
            model.addAttribute("error", "Invalid userId or password");
            return "login"; // 로그인 실패 시 다시 로그인 폼으로 이동
        }
    }

    // 로그아웃
    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate(); // 세션 무효화
        return "redirect:/"; // 로그아웃 후 홈 페이지로 리다이렉트
    }

    // 로그인 상태를 뷰로 전달합니다.
    @ModelAttribute("loggedIn")
    public boolean loggedIn(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return session != null && session.getAttribute("userId") != null;
    }

    // 사용자가 로그인한 사용자 ID를 모델에 추가합니다.
    @ModelAttribute("loggedInUserId")
    public String loggedInUserId(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("userId") != null) {
            return (String) session.getAttribute("userId");
        } else {
            return null;
        }
    }
}