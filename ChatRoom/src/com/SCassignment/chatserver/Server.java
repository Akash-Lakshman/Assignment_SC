package com.SCassignment.chatserver;

//Code that server application that will receive the message and send back to client like a group chat.
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.IOException;
import java.net.Socket;
import java.net.ServerSocket;


public class Server {

// The server socket.
public static ServerSocket serverSocket = null;
// The client socket.
public static Socket clientSocket = null;
public static BufferedReader is=null;
// This chat server can accept up to maxClientsCount clients' connections.
public static final int maxClientsCount = 20;
public static final clientThread[] threads = new clientThread[maxClientsCount];
public static Boolean cnt = true;

public static void main(String args[]) {

	Storage.charRoomsIndex=0;
	// The default port number.
  int portNumber = 4444;
  String ip= "127.0.0.1";
  
  if (args.length < 2) {
    System.out
        .println("Usage: Multi Thread ChatServer <portNumber>\n"
            + "Now using port number=" + portNumber);
  } else {
    portNumber = Integer.valueOf(args[0]).intValue();
    ip=args[1];
  }
  if(ip.isEmpty()){
		System.out.println("IP address is empty");
		return;
  }
  if(portNumber<1024){
		System.out.println("Invalid Port Number");
		return;
  }
  
  Functions.loadProperties(ip,portNumber);

  
  /*
   * Open a server socket on the portNumber (default 4444). */
  try {
    serverSocket = new ServerSocket(portNumber);
    System.out.println("Server has been successfully Started on IP: "+ ip+ " & Port: "+portNumber);
    
  } catch (IOException e) {
	  System.out.println("Error in Opening socket");
	  System.out.println(e);
	  e.printStackTrace();
    
  }

  
  // Create a client socket for each connection and pass to a new client thread

  while (cnt) {
    try {
    	System.out.println("Waiting for connection");
		if((cnt==true) && (null!=serverSocket) && !(serverSocket.isClosed()))
		{
    	
      clientSocket = serverSocket.accept();
       is = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		String s = new String();
		s = is.readLine();
		System.out.println("Got a connection... creating thread...");
      int i = 0;
      for (i = 0; i < maxClientsCount; i++) {
        if (threads[i] == null) {
          (threads[i] = new clientThread(clientSocket, threads,s,is)).start();
          break;
        }
      }
	}
	
	else 
	{
			System.out.println("Stopping");
			return;
	}
    } 
    catch (IOException e) {
		System.out.println("Exception in main method !!!");
		System.out.println(e);
		e.printStackTrace();
		System.out.println("Exception in main method !!!");
		return;
	}
  }
}
}
/*
* The chat client thread. To echo data back to other clients
*/
	
class clientThread extends Thread {
 
Socket clientSocket = null;
BufferedReader is;

PrintStream os;

private final clientThread[] threads;
private int maxClientsCount;
String[] s = new String[100] ;
Boolean flag = false;

public clientThread(Socket clientSocket, clientThread[] threads, String s, BufferedReader is) {
  this.clientSocket = clientSocket;
  this.threads = threads;
  maxClientsCount = threads.length;
  this.s[0]=s; 
  this.is=is;
}

public void run() {
	System.out.println("Main Thread "+Thread.currentThread().getId()+" : Thread Created");
	flag=false;
  try {
    /*
     * input and output streams for this client.
     */
    os = new PrintStream(clientSocket.getOutputStream());
    while (true) {

    	System.out.println("Waiting for input....");
		if(flag){
			s[0]=is.readLine();
		}
		System.out.println("FirstLine: "+s[0]+"\n");
		if(null != s[0] && s[0].startsWith("JOIN_CHATROOM: ")) {
			s[1] = is.readLine();
			s[2] = is.readLine();
			s[3] = is.readLine();
			System.out.println("Input JOIN_CHATROOM Message:\n"+s[0]+s[1]+s[2]+s[3]);
		}else if(null != s[0] && s[0].startsWith("LEAVE_CHATROOM: ")) {
			s[1] = is.readLine();
			s[2] = is.readLine();
			System.out.println("Input LEAVE_CHATROOM Message:\n"+s[0]+s[1]+s[2]);
		}else if(null != s[0] && s[0].startsWith("CHAT: ")) {
			s[1] = is.readLine();
			s[2] = is.readLine();
			int i = 3;
			while(true){
				s[i] = is.readLine();
				if(s[i].isEmpty()){
					break;
				}
				i++;
			}
			System.out.println("Input CHAT Message:\n"+s[0]+s[1]+s[2]+s[3]+s[4]);
		}else if(null != s[0] && s[0].startsWith("KILL_SERVICE")) {
			System.out.println("Input KILL_SERVICE Message:\n"+s[0]);
			/*
			* Clean up. 
			*/
			synchronized (this) {
				for (int i = 0; i < maxClientsCount; i++) {
					if (threads[i] != null && threads[i] !=this) {
						System.out.println("Closing thread: "+i);
						System.out.println("PRE: socket closed?: "+threads[i].clientSocket.isClosed());
						threads[i].is.close();
						threads[i].os.close();
						threads[i].clientSocket.close();
						System.out.println("POST: socket closed?: "+threads[i].clientSocket.isClosed());
						threads[i] = null;
					}
				}
			}
			Server.cnt=false;
			System.out.println("Closing IS");
			is.close();
			System.out.println("Closing OS");
			os.close();
			System.out.println("Closing IS in main");
			Server.is.close();
			System.out.println("Closing clientsocket in main");
			Server.clientSocket.close();
			System.out.println("Closing serversocket in main");
			Server.serverSocket.close();
			System.out.println("Closing serversocket in main again");
			Server.serverSocket.close();
			System.out.println("Setting it to null");
			Server.serverSocket=null;
			System.out.println("Complete!!");
			System.exit(0);
			break;
		}else if(null != s[0] && s[0].startsWith("HELO ")) {
			System.out.println("Input HELO Message:\n"+s[0]);
		}
		else if(null != s[0] && s[0].startsWith("DISCONNECT: ")){
			s[1] = is.readLine();
			s[2] = is.readLine();
			Functions hf = new Functions();
			hf .processDisconnectMessage(s[0],s[1],s[2],os);
			clientSocket.close();
			return;
		}else if(null == s[0]){
			break;
		}else {
			System.out.println("Input ERROR Message:\n"+s[0]);
			Functions hf = new Functions();
			hf .processErrorMessage(s[0],os);
		}
		new ClientHandler(os,s).start();
		flag=true;
	}
} catch (IOException e) {
	System.out.println("IO Exception in main Thread::"+e +"::");
	e.printStackTrace();
}
}
}
