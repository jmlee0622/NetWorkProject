package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class SocketServer {

    private ServerSocket serverSocket;
    private Vector<SocketThread> vc;
    private int count =0; // 첫 번째 클라이언트를 위한 플래그
    public SocketServer() {
        try {
            serverSocket = new ServerSocket(5000);
            vc = new Vector<>();
            while (true) {
                Socket socket = serverSocket.accept();
                SocketThread st = new SocketThread(socket);
                count +=1;
                System.out.println(count);
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
                username = reader.readLine(); // 사용자 이름 받기
                if (count==1) {
                    for (SocketThread st : vc) {
                       
                            st.writer.write(username+ "님 입장했습니다.");
                            st.writer.newLine();
                            st.writer.flush();
                        
                    }
                       
                }
               
                    else {
                 
                        for (SocketThread st : vc) {
                         
                                st.writer.write(username + "님 입장했습니다.");
                                st.writer.newLine();
                                st.writer.flush();
                            
                        }
                    }

                // 채팅 메시지 처리
                while ((line = reader.readLine()) != null) {
                    // 채팅 메시지를 받은 후, 모든 클라이언트에게 메시지 전송
                    for (SocketThread st : vc) {
                        st.writer.write(username + ":" + line);
                        st.writer.newLine();
                        st.writer.flush();
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
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
        new SocketServer();
    }
}
