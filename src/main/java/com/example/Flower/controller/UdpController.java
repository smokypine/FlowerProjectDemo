
package com.example.Flower.controller;

import com.example.Flower.device.UdpCon;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.UnknownHostException;

// REST 컨트롤러 클래스
@RestController
public class UdpController {
    // UdpCon 인스턴스 생성
    private final UdpCon udpCon;
    public UdpController() {
        this.udpCon = new UdpCon();
    }

    // 센서 데이터를 가져오는 엔드포인트
    @GetMapping("/udp/sensor")
    public String[] getSensorData(@RequestParam String ipAddress, @RequestParam int port) {
        try {
            // UdpCon의 start 메소드를 호출하여 센서 데이터 요청 및 반환
            return udpCon.start(ipAddress, port, 0); // 0은 센서 데이터를 의미
        } catch (UnknownHostException e) {
            // UnknownHostException 발생 시, 예외 메시지 반환
            return new String[]{"UnknownHostException: " + e.getMessage()};
        } catch (IOException e) {
            // IOException 발생 시, 예외 메시지 반환
            return new String[]{"IOException: " + e.getMessage()};
        }
    }

    // 액추에이터 데이터를 가져오는 엔드포인트
    @GetMapping("/udp/actuator")
    public String[] getActuatorData(@RequestParam String ipAddress, @RequestParam int port) {
        try {
            // UdpCon의 start 메소드를 호출하여 액추에이터 데이터 요청 및 반환
            return udpCon.start(ipAddress, port, 1); // 1은 액추에이터 데이터를 의미
        } catch (UnknownHostException e) {
            // UnknownHostException 발생 시, 예외 메시지 반환
            return new String[]{"UnknownHostException: " + e.getMessage()};
        } catch (IOException e) {
            // IOException 발생 시, 예외 메시지 반환
            return new String[]{"IOException: " + e.getMessage()};
        }
    }

    @GetMapping("/udp/screenshot")
    public String captureScreenshot() {
        try {
            // 스트리밍 중인 영상의 스크린샷을 찍기
            String screenshotBase64 = udpCon.captureScreenshot("http://175.123.202.85:20800/stream");
            return screenshotBase64;
        } catch (IOException e) {
            // IOException 발생 시, 예외 메시지 반환
            return "Error capturing screenshot: " + e.getMessage();
        }
    }
}

// 페이지 컨트롤러 클래스
@Controller
class PageController {
    // 스트리밍 페이지를 보여주는 엔드포인트
    @GetMapping("/streaming")
    public String showStreamingPage() {
        // "stream" 템플릿 반환
        return "stream";
    }
}