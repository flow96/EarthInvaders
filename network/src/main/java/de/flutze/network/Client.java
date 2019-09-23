package de.flutze.network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client implements Runnable {


    private DatagramSocket socket;
    private Scanner scanner;


    public Client(){
        try {
            scanner = new Scanner(System.in);
            this.socket = new DatagramSocket();
            new Thread(this).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void run() {
        String inp = "";
        while(!inp.equalsIgnoreCase("q")){
            System.out.print("Message: ");
            inp = scanner.nextLine();
            try {
                DatagramPacket packet = new DatagramPacket(inp.getBytes(), inp.getBytes().length, InetAddress.getLocalHost(), 3031);
                socket.send(packet);
                System.out.println("Message sent\r\n");
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
