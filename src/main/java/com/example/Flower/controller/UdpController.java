package com.example.Flower.controller;

import com.example.Flower.device.UdpCon;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.example.Flower.device.UdpCon;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;


@Slf4j
@RestController
public class UdpController extends SessionController{
    @Autowired
    private UdpCon udpCon;

    @GetMapping("/api/start-udp")
    @ResponseBody
    public void startUdp() {
        // UDP 통신을 시작합니다.
        udpCon.start("175.123.202.85", 20920);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/api/udp-data")
    @ResponseBody
    public Map<String, Object> getUdpData() {
        Map<String, Object> response = new HashMap<>();
        // 최신 UDP 데이터를 가져와서 반환합니다.
        List<String> data = udpCon.getLatestDataList();
        String screenshotBase64 = udpCon.getLatestScreenshotBase64();
        response.put("udpData", data);
        response.put("screenshot", screenshotBase64);

        // 서버 콘솔에 데이터를 출력합니다.
        System.out.println("서버 콘솔 로그: " + data);

        return response;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/api/udp-stop")
    public void getUdpStop() {
        log.info("stop udp");
        udpCon.stop();
    }

    @Autowired
    private HttpServletRequest request;


}
