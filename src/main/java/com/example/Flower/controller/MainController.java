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
public class MainController extends SessionCheckController{

    @GetMapping("/")
    public String home() {
        // 홈 페이지로 이동합니다.
        return "main";
    }
    @GetMapping("/streaming")
    public String main(Model model) {
        // 스트리밍 페이지로 이동합니다.
        return "stream";
    }
}
