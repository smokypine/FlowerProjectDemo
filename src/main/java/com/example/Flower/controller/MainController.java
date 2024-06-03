package com.example.Flower.controller;

import com.example.Flower.device.UdpCon;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Controller
public class MainController extends SessionController{

    @Autowired
    private UdpCon udpCon;

    @GetMapping("/")
    public String home() {
        // 홈 페이지로 이동합니다.
        return "main";
    }

    @GetMapping("/streaming")
    public String main(Model model) {
        // UDP 데이터를 가져와서 모델에 추가합니다.
        List<String> dataList = udpCon.getLatestDataList();
        System.out.println("서버 콘솔 로그 : " + dataList);
        model.addAttribute("udpData", dataList);
        // 스트리밍 페이지로 이동합니다.
        return "stream";
    }

    @GetMapping("/other-page")
    public String otherPage() {
        // 다른 페이지로 이동하고 UDP 연결을 종료합니다.
        udpCon.stop();
        return "other-page";
    }

    @Autowired
    private HttpServletRequest request;

//    // 사용자가 로그인한 사용자 ID를 모델에 추가합니다.
//    @ModelAttribute("loggedInUserId")
//    public String loggedInUserId() {
//        HttpSession session = request.getSession(false);
//        if (session != null && session.getAttribute("userId") != null) {
//            return (String) session.getAttribute("userId");
//        } else {
//            return null;
//        }
//    }
//
//    // 사용자의 로그인 상태를 모델에 추가합니다.
//    @ModelAttribute("loggedIn")
//    public boolean loggedIn() {
//        HttpSession session = request.getSession(false);
//        return session != null && session.getAttribute("userId") != null;
//    }
}
