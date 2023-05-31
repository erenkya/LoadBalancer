;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.DatagramPacket;
import java.io.IOException;
import java.util.Scanner;

public class Client {
    private static final int LOAD_BALANCER_PORT = 8888;

    public static void main(String[] args) {
        try {
            DatagramSocket socket = new DatagramSocket();
            InetAddress address = InetAddress.getLocalHost();
            Scanner scanner = new Scanner(System.in);
            System.out.println("1.Static\n2.Dynamic");
            String sord = scanner.nextLine();
            int decision_number = 0;
            //if static
            if (sord.equals("1") ) {
                decision_number = 4;
            }

            // Ask the user for the task decision
            System.out.println("Enter the task number (1. Directory Listing, 2. File Transfer, 3. Computation, 4. Video Streaming): ");
            String task = scanner.nextLine();
            int taskk = Integer.parseInt(task) + decision_number;
            task = String.valueOf(taskk);

            // Send the task decision to the load balancer
            byte[] sendData = task.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, address, LOAD_BALANCER_PORT);
            socket.send(sendPacket);

            // Receive the response from the load balancer
            byte[] receiveData = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            socket.receive(receivePacket);

            String serverResponse = new String(receivePacket.getData(), 0, receivePacket.getLength());
            System.out.println("Server response: " + serverResponse);

            // Handle task-specific operations
            if (task.equals("2" + decision_number)) {
                // File Transfer
                System.out.println("Enter file size (bigfile/smallfile): ");
                String fileSize = scanner.nextLine();

                // Send the file size decision to the load balancer
                sendData = fileSize.getBytes();
                sendPacket = new DatagramPacket(sendData, sendData.length, address, LOAD_BALANCER_PORT);
                socket.send(sendPacket);

                // Receive the response from the load balancer
                receiveData = new byte[1024];
                receivePacket = new DatagramPacket(receiveData, receiveData.length);
                socket.receive(receivePacket);

                serverResponse = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println("Server response: " + serverResponse);
            }

            // Close the socket
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
