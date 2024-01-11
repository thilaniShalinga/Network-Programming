import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.*;

public class WebPageReader{

    public static void main(String[] args){
        URL webpage = null;
        URLConnection conn = null;

        try{
            webpage = new URL("http://google.com");
            conn = webpage.openConnection();
            InputStreamReader reader = new InputStreamReader(conn.getInputStream(), "UTF8");
            BufferedReader buffer = 
        } catch(Exception e) {

            e.printStackTrace();
        }
    }
}
