import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;

class ThreadPooledDayTimeClient {

    public final static int SERVICE_PORT = 13;

    public static void main(String[] args) {

        System.out.println("warmup...");
        connectServer(10, 1);

        System.out.println("perf measure : ");
        connectServer(100, 100);
    }

    public static void connectServer(int IIterations, int iRequestsPerIte) {
        for (int i = 0; i < IIterations; i++) {
            Date dStart = new Date();

            for (int j = 0; j < iRequestsPerIte; j++) {
                try (Socket socClient = new Socket("localhost", ThreadPooledDayTimeServer.SERVICE_PORT)) {
                    // Send something to server
                    PrintWriter out = new PrintWriter(socClient.getOutputStream(), true);
                    out.println("Bye.."); // Write data time to the client

                    // Get time from server
                    BufferedReader br = new BufferedReader(new InputStreamReader(socClient.getInputStream()));
                    String time = br.readLine();

                    // Optionally, print the received time
                    // System.out.println("Received from server: " + time);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            Date dEnd = new Date();
            long diffInMillis = dEnd.getTime() - dStart.getTime();
            System.out.print(diffInMillis + ", ");
        }
    }
}
