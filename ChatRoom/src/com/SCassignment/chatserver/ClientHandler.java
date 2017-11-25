package com.SCassignment.chatserver;

import java.io.PrintStream;

public class ClientHandler extends Thread {
	private PrintStream os = null;
	private String[] s;


	public ClientHandler(PrintStream os1, String[] s1) {
		this.s = s1;
		this.os = os1;
	}

	public void run() {
		System.out.println("Client Writer "+Thread.currentThread().getId()+ " : Created a thread for input "+s[0]);
		Functions func = new Functions();

		if(s[0].startsWith("JOIN_CHATROOM: ")) {
			System.out.println("****Start "+Thread.currentThread().getId()+" WriterThread: join chatroom if block");
			func.processJoinMessage(s[0],s[1],s[2],s[3],os);
			System.out.println("****End "+Thread.currentThread().getId()+" WriterThread:  join chatroom if block");
		}
			else if(s[0].startsWith("LEAVE_CHATROOM: ")) {
			System.out.println("****Start "+Thread.currentThread().getId()+"  WriterThread:  leave chatroom if block");
			func.processLeaveMessage(s[0],s[1],s[2],os);
			return;
		}
			else if(s[0].startsWith("CHAT: ")) {
			System.out.println("****Start  "+Thread.currentThread().getId()+" WriterThread:  chat if block");
			func.processChatMessage(s[0],s[1],s[2],s[3],os);
			System.out.println("****End "+Thread.currentThread().getId()+"  WriterThread:  chat if block");
		}
			else if(s[0].startsWith("HELO ")) {
			System.out.println("****Start  "+Thread.currentThread().getId()+" WriterThread:  hello if block");
			func.processHeloMessage(s[0],os);
			System.out.println("****End  "+Thread.currentThread().getId()+" WriterThread:  hello chatroom if block");
			return;
		}
	}
}
