package com.example.Flower.device;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.*;
import java.util.Base64;

public class UdpCon {
    // UDP 통신을 수행하는 클래스
    public String[] start(String ipAddress, int ipPort, int dataType) throws IOException, UnknownHostException {
        //dataType은 센서 데이터(0), 엑추데이터(1)로 구분

        // DatagramSocket 및 InetAddress 객체 생성
        DatagramSocket datagramSocket = new DatagramSocket();
        InetAddress serverAddress = InetAddress.getByName(ipAddress);

        // 데이터를 저장할 바이트 배열 생성
        byte[] msg = new byte[100];
        msg[0] = (byte) dataType;

        // 전송용 DatagramPacket 및 수신용 DatagramPacket 생성
        DatagramPacket outPacket = new DatagramPacket(msg, 1, serverAddress, ipPort);
        DatagramPacket inPacket = new DatagramPacket(msg, msg.length);

        // DatagramPacket 전송
        datagramSocket.send(outPacket);
        // DatagramPacket 수신
        datagramSocket.receive(inPacket);

        // 수신된 데이터 출력 및 파싱
        System.out.println("Received Data: " + new String(inPacket.getData()).trim());
        String tmp = new String(inPacket.getData());

        String[] parsedData = this.parseData(tmp);

        // DatagramSocket 닫기
        datagramSocket.close();
        return parsedData;
    }

    // 수신된 데이터를 파싱하는 메소드
    public String[] parseData(String stringData) {
        // 수신된 문자열 데이터를 쉼표로 분리하여 배열에 저장
        String[] dataArr = stringData.trim().split(",");

        // 데이터의 타입에 따라 다른 처리 수행
        if (dataArr[ScForm.TAG].equals(ScForm.TAGSENSOR)) {
            // 센서 데이터인 경우
            System.out.println("tag : " + dataArr[ScForm.TAG]);
            System.out.println("temp : " + dataArr[ScForm.TEMP]);
            System.out.println("wet : " + dataArr[ScForm.WET]);
            System.out.println("light : " + dataArr[ScForm.LIGHT]);
            System.out.println("daylight : " + dataArr[ScForm.DAYLIGHT]);
        } else if (dataArr[ScForm.TAG].equals(ScForm.TAGACTUATOR)) {
            // 액추에이터 데이터인 경우
            System.out.println("tag : " + dataArr[ScForm.TAG]);
            System.out.println("moter : " + dataArr[ScForm.MOTERTYPE]);
            System.out.println("result : " + dataArr[ScForm.RESULT]);
            System.out.println("stat : " + dataArr[ScForm.STATUS]);
        }
        return dataArr;
    }

    // 스트리밍 중인 영상의 스크린샷을 찍는 메소드
    public String captureScreenshot(String streamUrl) throws IOException {
        URL url = new URL(streamUrl);
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
    // 화면에 스크린샷 이미지를 출력하는 메소드
    public void displayScreenshot(String base64Image) throws IOException {
        // Base64로 인코딩된 이미지를 디코딩하여 Image 객체로 변환
        byte[] imageBytes = Base64.getDecoder().decode(base64Image);
        ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes);
        Image image = ImageIO.read(bis);

        // 이미지를 화면에 출력
        JFrame frame = new JFrame("Screenshot");
        JLabel label = new JLabel(new ImageIcon(image));
        frame.getContentPane().add(label);
        frame.pack();
        frame.setVisible(true);
    }
}