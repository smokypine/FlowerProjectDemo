package com.example.Flower.device;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class UdpCon {
    private volatile List<String> latestDataList;
    private DatagramSocket datagramSocket;
    private boolean isRunning;

    public UdpCon() {
        this.latestDataList = new ArrayList<>();
    }

    public void start(String serverIp, int serverPort) {
        isRunning = true;

        new Thread(() -> {
            try {
                latestDataList.clear();  // 기존 데이터를 비웁니다.
                datagramSocket = new DatagramSocket();
                InetAddress serverAddress = InetAddress.getByName(serverIp);
                byte[] msg = new byte[100];
                DatagramPacket outPacket = new DatagramPacket(msg, 1, serverAddress, serverPort);
                DatagramPacket inPacket = new DatagramPacket(msg, msg.length);

                datagramSocket.send(outPacket);
                datagramSocket.receive(inPacket);
                String receivedData = new String(inPacket.getData()).trim();
                latestDataList.add(receivedData);
                isRunning = false;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void stop() {
        isRunning = false;
        if (datagramSocket != null) {
            datagramSocket.close();
        }
    }

    public synchronized List<String> getLatestDataList() {
        return new ArrayList<>(latestDataList);
    }

    public synchronized String getLatestData() {
        return latestDataList.isEmpty() ? null : latestDataList.get(latestDataList.size() - 1);
    }
}
