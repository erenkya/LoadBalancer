

import java.io.IOException;
import java.net.*;

public class LoadBalancer {
    private static final int LOAD_BALANCER_PORT = 8888;
    private static final int SERVER_1_PORT = 5555;
    private static final int SERVER_2_PORT = 6666;
    private static int server1LoadLevelThreshold = 0;
    private static int server2LoadLevelThreshold = 0;

    public static void main(String[] args) {
        try {
            DatagramSocket socket = new DatagramSocket(LOAD_BALANCER_PORT);

            while (true) {
                



                byte[] receiveData = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                socket.receive(receivePacket);

                String clientMessage = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println("Client message received: " + clientMessage);

                String serverResponse = "";
                int selectedServerPort;
                //Dynamic Resource Based
                if(Integer.parseInt(clientMessage) < 5 ){
                    // Check the load level thresholds of the servers and select the appropriate server
                    if (server1LoadLevelThreshold < server2LoadLevelThreshold) {
                        selectedServerPort = SERVER_1_PORT;
                    } else {
                      selectedServerPort = SERVER_2_PORT;
                    }
                }
                else{ //Static Port Hashe
                   int dummy = Integer.parseInt(clientMessage);
                   clientMessage =  String.valueOf((dummy - 4));
                   int S1 = SERVER_1_PORT%2;
                   int S2 = SERVER_2_PORT%2;
                   if(dummy%2 == S1){selectedServerPort = SERVER_1_PORT;}
                   else{selectedServerPort = SERVER_2_PORT;}
                }
                
                // Forward client message to the selected server
                serverResponse = forwardMessageToServer(clientMessage, selectedServerPort);

                // Send server response back to the client
                byte[] sendData = serverResponse.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length,
                        receivePacket.getAddress(), receivePacket.getPort());
                socket.send(sendPacket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String forwardMessageToServer(String message, int serverPort) throws IOException {
        DatagramSocket socket = new DatagramSocket();
        InetAddress serverAddress = InetAddress.getLocalHost();

        byte[] sendData = message.getBytes();
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, serverPort);
        socket.send(sendPacket);

        byte[] receiveData = new byte[1024];
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
        socket.receive(receivePacket);

        return new String(receivePacket.getData(), 0, receivePacket.getLength());
    }

    public static void setServer1LoadLevelThreshold(int threshold) {
        server1LoadLevelThreshold = threshold;
    }

    public static void setServer2LoadLevelThreshold(int threshold) {
        server2LoadLevelThreshold = threshold;
    }
}
