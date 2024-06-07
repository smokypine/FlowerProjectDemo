package com.example.Flower.api;

import com.example.Flower.device.ScForm;
import com.example.Flower.device.UdpCon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/udp")
public class UdpApiController {

    @Autowired
    private UdpCon udpCon;

    @PostMapping("/custom")
    public Map<String, Object> getCustomData(@RequestBody Map<String, String> request) {
        // 응답 데이터를 담을 Map 객체를 생성합니다.
        Map<String, Object> response = new HashMap<>();
        try {
            // 요청에서 IP 주소와 포트 번호를 추출합니다. 기본값은 각각 "175.123.202.85"와 "20920"입니다.
            String ipAddress = request.getOrDefault("ipAddress", "175.123.202.85");
            int ipPort = Integer.parseInt(request.getOrDefault("ipPort", "20920"));

            // 요청에서 'type' 값을 추출합니다. 이 값에 따라 수행할 작업이 결정됩니다.
            String type = request.get("type");

            String[] data;
            // 'type' 값에 따라 다른 메서드를 호출하여 데이터를 가져옵니다.
            if ("sensor".equals(type)) {// 'sensor' 타입 요청 처리
                data = udpCon.start(ipAddress, ipPort);
                response.put("sensorData", data);
            } else if ("rotater".equals(type)) {// 'rotater' 타입 요청 처리
                data = udpCon.actuator(ipAddress, ipPort, ScForm.ROTATER);
                response.put("rotaterData", data);
            } else if ("sprinkler".equals(type)) {// 'sprinkler' 타입 요청 처리
                data = udpCon.actuator(ipAddress, ipPort, ScForm.SPRINKLER);
                response.put("sprinklerData", data);
            } else if ("led".equals(type)) {// 'led' 타입 요청 처리
                data = udpCon.actuator(ipAddress, ipPort, ScForm.LED);
                response.put("ledData", data);
            } else {// 잘못된 'type' 값에 대한 처리
                response.put("error", "Invalid type specified");
            }
        } catch (Exception e) {
            // 예외 발생 시 에러 메시지를 응답에 추가합니다.
            response.put("error", e.getMessage());
        }
        // 최종적으로 응답 데이터를 반환합니다.
        return response;
    }

    @GetMapping("/screenshot")
    public Map<String, Object> getScreenshot() {
        Map<String, Object> response = new HashMap<>();
        try {
            udpCon.receiveAndProcessData();
            String screenshot = udpCon.getLatestScreenshotBase64();
            response.put("screenshot", screenshot);
        } catch (Exception e) {
            response.put("error", e.getMessage());
        }
        return response;
    }

}
