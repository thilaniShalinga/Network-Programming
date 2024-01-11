import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.InetAddress;
import java.rmi.UnknownHostException;

public class UDPClient{
    public final static int MCAST_PORT = 50001;
    public static void main(String[] args){
        try{
            // 1. Create a DatagramSocket object
            DatagramSocket clientSocket = new DatagramSocket();

            // 2. Find the IP address of the server by name
            InetAddress IPAddress = null;
            try{
                IPAddress = InetAddress.getByName("localhost");
            } catch(java.net.UnknownHostException e) {
                e.printStackTrace();
            }

            // 3. Create Buffers for storing datagram data in DatagramPacket object
            byte[] buffReceiveData = new byte[1024]; // for incoming data
            byte[] buffSendData    = new byte[1024]; // for outgoing data

            // 4. Data to send to the server
            String sendData = "1,245,10054,65,70,2345";
            buffSendData = sendData.getBytes();
            System.out.println("CLIENT SENDING DATA: " + sendData);
           

            //Create a DatagramPacket to send data to the server 
            DatagramPacket packetOut = new DatagramPacket(buffSendData, buffSendData.length, IPAddress, MCAST_PORT);
            
            try{     // 6. Send datagram to the above specified destination
                clientSocket.send(packetOut);

            } catch (IOException e) {
                e.printStackTrace();
            }

            // 7.Craete Datagrampacket obj 4 wrapping e incoming packet(datagram)
            DatagramPacket receivePacket = new DatagramPacket(buffReceiveData, buffReceiveData.length);

            // 8. Receive incoming datagram in to DatagramPacket object.
            try{           // This is a blocking system call.
                clientSocket.receive(receivePacket); //Program blocks here
            } catch (IOException e) {
                e.printStackTrace();
            }

            // 9. Display the received data
            String receivedData = new String(receivePacket.getData(), 0, receivePacket.getLength());
            System.out.println("CLIENT RECEIVED DATA: " +receivedData);


            // 10. Close the datagramSocket
            clientSocket.close();
        } catch (SocketException e) { // Top Level Try-Catch
            e.printStackTrace();

        }
    } 
}