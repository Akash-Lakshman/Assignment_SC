package com.SCassignment.chatserver;

import java.io.DataInputStream;
import java.io.PrintStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

  // The client socket
  private static Socket clientSocket = null;
  // The output stream
  private static PrintStream os = null;
  // The input stream
  private static DataInputStream is = null;

  public static String join(String chatroomName, String clientName ) {
          String msg = "JOIN_CHATROOM: " + chatroomName + "\nCLIENT_IP: 0\nPORT: 0\nCLIENT_NAME: " + clientName;
          return msg;
  }
  public static String helo() {
         String msg = "HELO BASE_TEST";
          //String msg = "";
          return msg;
  }

  public static String leave(String chatroomName, String clientName ) {
          String msg = "LEAVE_CHATROOM: " + chatroomName + "\nJOIN_ID: 0\nCLIENT_NAME: " + clientName;
          return msg;
  }

  public static void main(String[] args) {

    // The default port.
    int portNumber = 4444;
    // The default host.
    String host = "localhost";
    String msg1 = "",msg2 = "";

    if (args.length < 2) {
      System.out
          .println("Usage: java MultiThreadChatClient <host> <portNumber>\n"
              + "Now using host=" + host + ", portNumber=" + portNumber);
    } else {
      host = args[0];
      portNumber = Integer.valueOf(args[1]).intValue();
    }

    try {
        clientSocket = new Socket(host, portNumber);
         msg1 = join("Whatsapp22","firstclient22");
         msg2 = leave("Whatsapp22","firstclient22");
        //msg = helo();
        //inputLine = new BufferedReader(new InputStreamReader(System.in));
        os = new PrintStream(clientSocket.getOutputStream());
        is = new DataInputStream(clientSocket.getInputStream());

      } catch (UnknownHostException e) {
        System.err.println("Don't know about host " + host);
      } catch (IOException e) {
        System.err.println("Couldn't get I/O for the connection to the host "
            + host);
      }

      /*
       * If everything has been initialized then we want to write some data to the
       * socket we have opened a connection to on the port portNumber.
       */
      if (clientSocket != null && os != null && is != null) {
        try {

          /* Create a thread to read from the server. */
          //new Thread(new Client()).start();
            System.out.println("Sending msg1:: "+msg1);
            os.println(msg1);
            System.out.println("Sending msg2:: "+msg2);
            os.println(msg2);
            System.out.println("Sent");
            while(true) {

                int lines=0;
            String line = null;
            String[] s = new String[10];
                      while(true) {
                              line = is.readLine();
                              s[lines]=line;
                              lines++;
                              System.out.println(line);
                              if(line==null||line.isEmpty())
                                      break;
                      }
            System.out.println(s.toString());
                if(s[0].length()!=0) {
                        System.out.println(s.toString());
                        if (s[0].contains("JOIN_ID"))
                                break;
                        else if(s[0].contains("HELO"))
                                break;
                }
        }

      /*
       * Close the output stream, close the input stream, close the socket.
       */
      os.close();
      is.close();
      clientSocket.close();
    } catch (IOException e) {
      System.err.println("IOException:  " + e);
    }
  }
}

}
