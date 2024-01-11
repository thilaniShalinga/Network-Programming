import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPooledDayTimeServer {
    public final static int SERVICE_PORT = 13;
    public final static int THREAD_POOL_SIZE = 3;

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        ServerSocket socServerSocket = null;

        try {
            socServerSocket = new ServerSocket(SERVICE_PORT);

            for (;;) {
                Socket socClient = socServerSocket.accept();
                RequestProcessorThread reqProcessor = new RequestProcessorThread(socClient);

                System.out.println("RequestProcessor created and handed over the connection."
                        + "Thr [" + reqProcessor.toString() + "] Soc [" + socClient.toString() + "]");

                executorService.execute(reqProcessor);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (socServerSocket != null && !socServerSocket.isClosed()) {
                    socServerSocket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            executorService.shutdown(); // This will make the executor accept no new threads
            while (!executorService.isTerminated()) {
            } // wait until all threads are finish
            System.out.println("Finished all threads");
        }
    }
}
