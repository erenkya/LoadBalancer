/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


import java.io.IOException;
import java.net.*;
import java.util.Scanner;

import java.io.IOException;
import java.net.*;

/**
 *
 * @author ErenK
 */
import java.io.IOException;
import java.net.*;

public class Server2 {
    private static final int SERVER_2_PORT = 6666;

    public static void main(String[] args) {
        try {
            DatagramSocket socket = new DatagramSocket(SERVER_2_PORT);
            int loadLevel = 0;

            while (true) {
                byte[] receiveData = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                socket.receive(receivePacket);

                String clientMessage = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println("Client message received: " + clientMessage);

                String serverResponse = "";
                int loadLevelThreshold = 0;

                if (clientMessage.equals("1")) {
                    // Directory Listing
                    serverResponse = "We have GET, POST, CHANGE contents.";
                    loadLevel = 0;
                } else if (clientMessage.equals("2")) {
                    // File Transfer
                    serverResponse = "Enter file size (bigfile/smallfile): ";
                    loadLevel = 0;
                } else if (clientMessage.equals("3")) {
                    // Computation
                    serverResponse = "Computation has started (20 sec)";
                    loadLevel = 200;
                    Thread.sleep(20000); // Simulate processing time
                    serverResponse = "Computation ended";
                    loadLevel = 0;
                } else if (clientMessage.equals("4")) {
                    // Video Streaming
                    serverResponse = "20 sec video is playing";
                    loadLevel = 15;
                    Thread.sleep(20000); // Simulate processing time
                    serverResponse = "Video has done";
                    loadLevel = 0;
                }

                // Update the load level threshold in the load balancer
                LoadBalancer.setServer1LoadLevelThreshold(loadLevelThreshold);

                // Send server response back to the load balancer
                byte[] sendData = serverResponse.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length,
                        receivePacket.getAddress(), receivePacket.getPort());
                socket.send(sendPacket);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
