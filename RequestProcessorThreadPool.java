import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class RequestProcessorThreadPool implements Runnable {
    private final Socket clientSocket;

    public RequestProcessorThreadPool(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            System.out.println(this.toString() + ": Thread started. Processing client" + clientSocket);

            // Read data from the client
            BufferedReader br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String clientData = br.readLine();
            System.out.println("Received from client: " + clientData);

            // Process data (you can add your processing logic here)

            // Send response to the client
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            out.println("Response from server");

            // Close the socket after processing
            clientSocket.close();
            System.out.println(this.toString() + ": Thread exiting...");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
