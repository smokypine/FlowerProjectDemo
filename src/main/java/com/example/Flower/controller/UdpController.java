package com.example.Flower.controller;

import com.example.Flower.device.UdpCon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UdpController {

    @Autowired
    private UdpCon udpCon;

    @GetMapping("/api/start-udp")
    @ResponseBody
    public void startUdp() {
        udpCon.start("175.123.202.85", 20920);
    }

    @GetMapping("/api/udp-data")
    @ResponseBody
    public Map<String, List<String>> getUdpData() {
        Map<String, List<String>> response = new HashMap<>();
        List<String> data = udpCon.getLatestDataList();
        response.put("udpData", data);

        // 서버 콘솔에 데이터 출력
        System.out.println("서버 콘솔 로그: " + data);

        return response;
    }
}
