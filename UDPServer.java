import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;

public class UDPServer {
    public final static int MCAST_PORT = 50001;
    public static void main(String[] args) {
        try{

            //1. Create a DatagramSocket object
            DatagramSocket serverSocket = new DatagramSocket(MCAST_PORT);

            // 2. Craete Buffers for storing datagram data in DatagramPacket object
            byte[] buffReceiveData = new byte[1024]; //for incoming data
            byte[] buffSendData    = new byte[1024]; //for outgoing data

            // 3. Create DatagramPacket obj 4 wrapping e incoming packet (datagram)
            DatagramPacket packetIn = new DatagramPacket(buffReceiveData, buffReceiveData.length);

            // 4. Receive incoming datagram in to DatagramPacket object.
            try{
                serverSocket.receive(packetIn); //Program system call.
            } catch (IOException e) { // until a datagram is received.
                e.printStackTrace();
            }

            // 5. Get the data from received packet
            String strInData = new String(packetIn.getData(), 0, packetIn.getLength());
            System.out.println("SERVER RECEIVED DATA :" + strInData);
            
            //Process the comma-seperated string
            String[] numbers = strInData.split(",");
            double sum = 0;

            for(String number : numbers){
                try{
                    double num = Double.parseDouble(number.trim()); 
                    sum += num;
                } catch (NumberFormatException e) {
                    System.err.println("Invalid number format: ");
                }
            }

            double average = sum / strInData.length();

            String responseData = "Sum: " + sum + ", Average: " + average;
            buffSendData = responseData.getBytes();
            System.out.println(responseData + "Client Ip: " + packetIn.getAddress() + " Client Port: "+ packetIn.getPort() );

            //Create DatagramPacket to send data to the client
            DatagramPacket packetOut = new DatagramPacket(buffSendData, buffSendData.length, packetIn.getAddress(), packetIn.getPort());

            //Send the packet to the client
            try{
                serverSocket.send(packetOut);
            } catch (IOException e) {
                e.printStackTrace();
            }

            // close the DatagramSocket
            serverSocket.close();

            
        } catch (SocketException e) { 
            e.printStackTrace();
        }
    } 

} 