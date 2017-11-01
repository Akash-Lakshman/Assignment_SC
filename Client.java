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
      System.err.println(" HOST unknown	 ");
    } catch (IOException e) {
      System.err.println("Couldn't get I/O for the connection to host");
    }


     /* opened a connection to on port 2222. */
     
    if (clientSocket != null && os != null && is != null) {
      try {

        //Starting the Client
        System.out.println("The client started. Type any text. To quit it type 'Ok'.");
        String responseLine;
        os.println(inputLine.readLine());
        while ((responseLine = is.readLine()) != null) {
          System.out.println(responseLine);
          if (responseLine.indexOf("Ok") != -1) {
            break;
          }
          os.println(inputLine.readLine());
        }

        
         //Close the output stream, close the input stream, close the socket.
         
        os.close();
        is.close();

        clientSocket.close();
      } catch (UnknownHostException e) {
        System.err.println("Trying to connect to unknown host: " + e);
      } catch (IOException e) {
        System.err.println("IOException:  " + e);
      }
    }
  }
}

