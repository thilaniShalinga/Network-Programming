import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.ServerSocket;
import java.util.Date;

class RequestProcessorThread implements Runnable
{
    private Socket soClient;
    public RequestProcessorThread(Socket soc) {soClient = soc;}

    @Override
    public void run() {
        System.out.println(this.toString() + ": Thread started. Processing client" + soClient);
        try{
            Date today = new Date();     
            try(PrintWriter out = new PrintWriter (soClient.getOutputStream(),true)){
                out.println(today); //Write date time to the client
            }            
            //Wait till client sends some data back to the server.   
            try(
                InputStream is  = soClient.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is))){
                String receivedData = br.readLine();
                System.out.println("Received from client: "+ receivedData);
            }       
            soClient.close(); //Always don't forget to close the socket when you are done
        
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(this.toString()+ " : Thread existing...");
    }
}