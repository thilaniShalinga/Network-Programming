import java.rmi.Remote; 
import java.rmi.RemoteException;  

// Creating Remote interface for our application 
public interface RemoteInt extends Remote {  
   void printMsg() throws RemoteException;  
} 