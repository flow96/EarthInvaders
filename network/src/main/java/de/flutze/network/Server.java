package de.flutze.network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class Server implements Runnable {

    private DatagramSocket receiveSocket, sendSocket;
    private DatagramPacket rcvPacket;
    private byte[] buffer;

    public Server() {
        setUpServer();
    }

    private void setUpServer() {
        try {
            receiveSocket = new DatagramSocket(1234);
            sendSocket = new DatagramSocket();
            buffer = new byte[1024];
            rcvPacket = new DatagramPacket(buffer, buffer.length);
            new Thread(this).start();
        } catch (SocketException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        while(true){
            try {
                receiveSocket.receive(rcvPacket);
                String msg = new String(buffer, 0, rcvPacket.getLength());
                System.out.println("\r\n >> Received: " + msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
