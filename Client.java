import java.io.DataInputStream;
import java.io.PrintStream;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
  public static void main(String[] args) {

    Socket clientSocket = null;
    DataInputStream is = null;
    PrintStream os = null;
    DataInputStream inputLine = null;

    /*
     * Open a socket on port 2222. here as well to connect to server
     */
    try {
      clientSocket = new Socket("localhost", 2222);
      os = new PrintStream(clientSocket.getOutputStream());
      is = new DataInputStream(clientSocket.getInputStream());
      inputLine = new DataInputStream(new BufferedInputStream(System.in));
	System.out.println("Available for Connection.");
    } catch (UnknownHostException e) {
      System.err.println(" ABC");
    } catch (IOException e) {
      System.err.println("no connection");
    }

}
}
