package com.example.Flower.controller;

import com.example.Flower.device.UdpCon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MainController {

    @Autowired
    private UdpCon udpCon;

    @GetMapping("/")
    public String home() {
        return "main";
    }

    @GetMapping("/streaming")
    public String main(Model model) {
        List<String> dataList = udpCon.getLatestDataList();
        System.out.println("서버 콘솔 로그 : " + dataList);
        model.addAttribute("udpData", dataList);
        return "stream";
    }

    @GetMapping("/other-page")
    public String otherPage() {
        udpCon.stop();
        return "other-page";
    }
}