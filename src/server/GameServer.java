package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

import server.SocketServer.SocketThread;

public class GameServer {

   private ServerSocket serverSocket;
   private Vector<SocketThread> vc; 

   public GameServer() {

      try {
         serverSocket = new ServerSocket(6000);
         vc = new Vector<>();
         while (true) {
            Socket socket = serverSocket.accept(); 
            SocketThread st = new SocketThread(socket);
            st.start();
            vc.add(st); 
         }
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   class SocketThread extends Thread {
      private Socket socket;
      private BufferedReader reader;
      private BufferedWriter writer;
      private String username;
      public SocketThread(Socket socket) {
         this.socket = socket;
      }

      @Override
      public void run() {
         try {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            String line;
           
            
            username = reader.readLine();
            
            while ((line = reader.readLine()) != null) {
            	//System.out.println(line);
               for (SocketThread st : vc) {
                  st.writer.write(line);
                  st.writer.newLine();
                  st.writer.flush(); 
               }
            }
         } catch (IOException e) {
            e.printStackTrace();
         }
         finally {
             try {
                 socket.close();
                 vc.remove(this);
                 // 클라이언트가 나갔을 때, 나간 클라이언트에게 메시지 전송
                 for (SocketThread st : vc) {
                     st.writer.write(username + "님이 나갔습니다.");
                     st.writer.newLine();
                     st.writer.flush();
                 }
             } catch (IOException e) {
                 e.printStackTrace();
             }
         }
      }

   }

   public static void main(String[] args) {
      new GameServer();
   }

}