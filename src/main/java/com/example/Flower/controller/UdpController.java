package com.example.Flower.controller;

import com.example.Flower.device.UdpCon;
import com.example.Flower.dto.ScreenShotDTO;
import com.example.Flower.entity.ScreenShot;
import com.example.Flower.repository.ScreenShotRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
public class UdpController {
    @Autowired
    private UdpCon udpCon;

    @Autowired
    private ScreenShotRepository screenshotRepository;

    @GetMapping("/api/start-udp")
    @ResponseBody
    public void startUdp() {
        // UDP 통신을 시작합니다.
        udpCon.start("175.123.202.85", 20920);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/api/udp-data")
    @ResponseBody
    public List<ScreenShotDTO> getUdpData() {
        // 최신 스크린샷 데이터를 가져와서 반환합니다.
        List<ScreenShot> screenshots = screenshotRepository.findAll();

        return screenshots.stream().map(screenshot -> {
            ScreenShotDTO dto = new ScreenShotDTO();
            dto.setId(screenshot.getId());
            dto.setImageBase64(Base64.getEncoder().encodeToString(screenshot.getImage()));
            dto.setTimestamp(screenshot.getTimestamp());
            return dto;
        }).collect(Collectors.toList());
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/api/udp-stop")
    public void getUdpStop() {
        log.info("stop udp");
        udpCon.stop();
    }

    @Autowired
    private HttpServletRequest request;

    // 사용자가 로그인한 사용자 ID를 모델에 추가합니다.
    @ModelAttribute("loggedInUserId")
    public String loggedInUserId() {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("userId") != null) {
            return (String) session.getAttribute("userId");
        } else {
            return null;
        }
    }

    // 사용자의 로그인 상태를 모델에 추가합니다.
    @ModelAttribute("loggedIn")
    public boolean loggedIn() {
        HttpSession session = request.getSession(false);
        return session != null && session.getAttribute("userId") != null;
    }
}
