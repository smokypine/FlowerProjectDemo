package com.example.Flower.device;

import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.*;
import java.util.Base64;

@Component
public class UdpCon {
    // UDP 연결 시작 메서드
    public String[] start(String ipAddress, int ipPort) throws IOException, UnknownHostException {
        DatagramSocket datagramSocket = new DatagramSocket();
        InetAddress serverAddress = InetAddress.getByName(ipAddress);

        // 데이터를 저장할 바이트 배열 생성
        byte[] rmsg = new byte[100];
        String senTag = "0";
        byte[] msg = senTag.getBytes();

        DatagramPacket outPacket = new DatagramPacket(msg, 1, serverAddress, ipPort);
        DatagramPacket inPacket = new DatagramPacket(rmsg, rmsg.length);

        datagramSocket.send(outPacket); // DatagramPacket을 전송
        datagramSocket.receive(inPacket); // DatagramPacket을 수신

        String receivedData = new String(inPacket.getData()).trim();
        System.out.println("recvData : " + receivedData);

        String[] parsedData = this.parseData(receivedData);
        datagramSocket.close();
        return parsedData;
    }

    // 데이터를 파싱하는 메서드
    public String[] parseData(String stringData) {
        String[] dataArr = stringData.trim().split(",");

        if (dataArr[ScForm.TAG].equals(ScForm.TAGSENSOR)) {
            // 소켓 종류가 센서일 때
            System.out.println("tag : " + dataArr[ScForm.TAG]);
            System.out.println("temp : " + dataArr[ScForm.TEMP]);
            System.out.println("wet : " + dataArr[ScForm.WET]);
            System.out.println("light : " + dataArr[ScForm.LIGHT]);
            System.out.println("daylight : " + dataArr[ScForm.DAYLIGHT]);
        } else if (dataArr[ScForm.TAG].equals(ScForm.TAGACTUATOR)) {
            // 소켓 종류가 액추에이터일 때
            System.out.println("tag : " + dataArr[ScForm.TAG]);
            System.out.println("moter : " + dataArr[ScForm.MOTERTYPE]);
            System.out.println("result : " + dataArr[ScForm.RESULT]);
        }

        return dataArr;
    }

    // 액추에이터 동작 메서드
    public String[] actuator(String ipAddress, int ipPort, String moterType) throws IOException, UnknownHostException {
        DatagramSocket datagramSocket = new DatagramSocket();
        InetAddress serverAddress = InetAddress.getByName(ipAddress);

        moterType = "1," + moterType;
        byte[] msg = moterType.getBytes();
        byte[] rmsg = new byte[100];

        DatagramPacket outPacket = new DatagramPacket(msg, msg.length, serverAddress, ipPort);
        DatagramPacket inPacket = new DatagramPacket(rmsg, rmsg.length);

        datagramSocket.send(outPacket); // DatagramPacket을 전송
        datagramSocket.receive(inPacket); // DatagramPacket을 수신

        String receivedData = new String(inPacket.getData()).trim();
        System.out.println("recvData : " + receivedData);

        String[] parsedData = this.parseData(receivedData);
        datagramSocket.close();
        return parsedData;
    }

    //스크린샷
    private String latestScreenshotBase64;

    public synchronized String getLatestScreenshotBase64() {
        return latestScreenshotBase64;
    }

    public void receiveAndProcessData() {
        try {
            // 스크린샷을 캡처합니다.
            latestScreenshotBase64 = getScreenshotFromStreamUrl("http://175.123.202.85:20800/screenshot");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getScreenshotFromStreamUrl(String urlString) throws IOException {
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

            byte[] imageBytes = outputStream.toByteArray();
            return Base64.getEncoder().encodeToString(imageBytes);
        } finally {
            connection.disconnect();
        }
    }

}