package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

import server.SocketServer.SocketThread;

public class GameServer {

   private ServerSocket serverSocket;
   private Vector<SocketThread> vc; 
   private Vector<SocketThread> UserVec = new Vector<>();
   private String name="";
   
   public GameServer() {

      try {
    	  System.out.println("게임서버 실헹");
         serverSocket = new ServerSocket(6000);
         vc = new Vector<>();
         while (true) {
            Socket socket = serverSocket.accept(); 
            SocketThread st = new SocketThread(socket);
            st.start();
            vc.add(st); //새 클라이언트 연결시 해당 소켓 객체들을 벡터에 추가
         }
      } catch (Exception e) {
         e.printStackTrace();
         
      }
   }

   class SocketThread extends Thread {
      private Socket socket;
      private BufferedWriter writer;
      private BufferedReader reader;
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
            
            while ((line = reader.readLine())!= null) {// 클라이언트로부터 메시지를 읽어들임.
            	for (SocketThread st : vc) {
               	  st.writer.write(String.valueOf(vc.size()+":"));// 클라이언트 벡터 크기로 구별
               	  st.writer.write(line);// 읽어들인 line 모든 클라이언트로 전송
               	  st.writer.newLine();
                     st.writer.flush(); 
                }
             	if (line.contains("Dead")) {//"Dead" 메시지 읽어들일시,게임서버 클라이언트 소켓을 닫음
             		String[] lines = line.split(":");
                 	name = lines[1];
                    if (name.equals("Dead")) {
                            socket.close();
                            vc.remove(this);
                            System.out.println("남은 인원: " + vc.size());
                    }
                }
                
            }
         } catch (IOException e) { 
        	 e.printStackTrace(); 
         }
      }
      
   }

   public static void main(String[] args) {
      new GameServer();
   }

}