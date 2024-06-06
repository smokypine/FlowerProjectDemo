package com.example.Flower.api;

import com.example.Flower.device.ScForm;
import com.example.Flower.device.UdpCon;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/udp")
public class UdpApiController {
    @GetMapping("/sensor")
    public Map<String, Object> getSensorData() {
        Map<String, Object> response = new HashMap<>();
        try {
            String[] data = new UdpCon().start("175.123.202.85", 20920);
            response.put("sensorData", data);
        } catch (Exception e) {
            response.put("error", e.getMessage());
        }
        return response;
    }

    @GetMapping("/rotater")
    public Map<String, Object> getRotaterData() {
        Map<String, Object> response = new HashMap<>();
        try {
            String[] data = new UdpCon().actuator("175.123.202.85", 20920, ScForm.ROTATER);
            response.put("rotaterData", data);
        } catch (Exception e) {
            response.put("error", e.getMessage());
        }
        return response;
    }

    @GetMapping("/sprinkler")
    public Map<String, Object> getSprinklerData() {
        Map<String, Object> response = new HashMap<>();
        try {
            String[] data = new UdpCon().actuator("175.123.202.85", 20920, ScForm.SPRINKLER);
            response.put("sprinklerData", data);
        } catch (Exception e) {
            response.put("error", e.getMessage());
        }
        return response;
    }

    @GetMapping("/led")
    public Map<String, Object> getLedData() {
        Map<String, Object> response = new HashMap<>();
        try {
            String[] data = new UdpCon().actuator("175.123.202.85", 20920, ScForm.LED);
            response.put("ledData", data);
        } catch (Exception e) {
            response.put("error", e.getMessage());
        }
        return response;
    }
}