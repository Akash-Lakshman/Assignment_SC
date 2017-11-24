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

                if(s[0].startsWith("JOIN_CHATROOM: "))
                {
                        System.out.println(" Start "+Thread.currentThread().getId()+" WriterThread: join chatroom");
                        func.processJoinMessage(s[0],s[1],s[2],s[3],os);
                        System.out.println("****End "+Thread.currentThread().getId()+" WriterThread:  join chatroom");
                }
                        else if(s[0].startsWith("LEAVE_CHATROOM: ")) {
                        System.out.println(" Start "+Thread.currentThread().getId()+"  WriterThread:  leave chatroom ");
                        func.processLeaveMessage(s[0],s[1],s[2],os);
                        return;
                }
                        else if(s[0].startsWith("CHAT: ")) {
                        System.out.println(" Start  "+Thread.currentThread().getId()+" WriterThread:  chat ");
                        func.processChatMessage(s[0],s[1],s[2],s[3],os);
                        System.out.println(" End "+Thread.currentThread().getId()+"  WriterThread:  chat ");
                }
                        else if(s[0].startsWith("HELO ")) {
                        System.out.println(" Start  "+Thread.currentThread().getId()+" WriterThread:  hello ");
                        func.processHeloMessage(s[0],os);
                        System.out.println(" End  "+Thread.currentThread().getId()+" WriterThread:  hello chatroom");
                        return;
                }
        }
}
