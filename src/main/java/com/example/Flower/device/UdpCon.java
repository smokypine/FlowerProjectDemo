
package com.example.Flower.device;

import com.example.Flower.entity.ScreenShot;
import com.example.Flower.repository.ScreenShotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class UdpCon {
    private volatile List<String> latestDataList;
    private DatagramSocket datagramSocket;
    private boolean isRunning;

    @Autowired
    private ScreenShotRepository screenshotRepository;

    public UdpCon() {
        // 최신 데이터를 저장하기 위한 리스트를 초기화합니다.
        this.latestDataList = new ArrayList<>();
    }

    public void start(String serverIp, int serverPort) {
        isRunning = true;

        new Thread(() -> {
            try {
                // UDP 소켓을 생성하고 데이터를 수신합니다.
                latestDataList.clear();  // 기존 데이터를 비웁니다.
                datagramSocket = new DatagramSocket();
                InetAddress serverAddress = InetAddress.getByName(serverIp);
                byte[] msg = new byte[100];
                DatagramPacket outPacket = new DatagramPacket(msg, 1, serverAddress, serverPort);
                DatagramPacket inPacket = new DatagramPacket(msg, msg.length);

                datagramSocket.send(outPacket);
                datagramSocket.receive(inPacket);
                String receivedData = new String(inPacket.getData()).trim();
                processReceivedData(receivedData);

                isRunning = false;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void stop() {
        isRunning = false;
        if (datagramSocket != null) {
            // UDP 소켓을 닫습니다.
            datagramSocket.close();
        }
    }

    public synchronized List<String> getLatestDataList() {
        // 최신 데이터 리스트의 복사본을 반환합니다.
        return new ArrayList<>(latestDataList);
    }

    public synchronized String getLatestData() {
        // 최신 데이터를 반환합니다.
        return latestDataList.isEmpty() ? null : latestDataList.get(latestDataList.size() - 1);
    }

    private void processReceivedData(String data) {
        // 정규 표현식을 수정하여 '토양수분' 필드가 선택적임을 반영합니다.
        if (data.matches("\\[tag:0,\\{\\d+\\.\\d+,\\d+\\.\\d+,\\d+,\\d+(,\\d+)?\\}\\]")) {
            latestDataList.add(data);
            try {
                byte[] screenshotData = getScreenshotFromStreamUrl("http://175.123.202.85:20800/screenshot");
                saveScreenshotToDatabase(screenshotData);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("Invalid data format: " + data);
        }
    }

    private byte[] getScreenshotFromStreamUrl(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        try (InputStream inputStream = connection.getInputStream();
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            return outputStream.toByteArray();
        } finally {
            connection.disconnect();
        }
    }

    private void saveScreenshotToDatabase(byte[] screenshotData) {
        ScreenShot screenshot = new ScreenShot();
        screenshot.setImage(screenshotData);
        screenshot.setTimestamp(LocalDateTime.now());
        screenshotRepository.save(screenshot);
    }
}