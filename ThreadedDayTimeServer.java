import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.ServerSocket;
import java.util.Date;


public class ThreadedDayTimeServer {
    public final static int SERVER_PORT = 13;

    public static void main(String[] args){

        ServerSocket socServerSocket = null;
        Socket soClient = null;

        try{
            socServerSocket = new ServerSocket(SERVER_PORT);

            for(;;){
                soClient = socServerSocket.accept();
                RequestProcessorThread thread = new RequestProcessorThread(soClient);

                System.out.println("Thread created and handed over the connection. " +
                "Thr["+thread.toString() +"] Soc [" + soClient.toString() +"]");

                Thread t = new Thread(thread);
                t.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

