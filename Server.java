// Code that server application that will receive the message and send back to client.

import java.io.DataInputStream;
import java.io.PrintStream;
import java.io.IOException;
import java.net.Socket;
import java.net.ServerSocket;

public class Server {
  public static void main(String args[]) {

    ServerSocket echoServer = null;
    String line;
    DataInputStream is;
    PrintStream os;
    Socket clientSocket = null;

    /*
     * Open a server socket on port 2222. 
     */
    try {
      echoServer = new ServerSocket(2222);
	System.out.println("Server Socket created under port 2222");
    } catch (IOException e) {
      System.out.println(e);
    }

}	
}
