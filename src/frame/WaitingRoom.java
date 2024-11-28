package frame;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Image;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class WaitingRoom extends JFrame {

	private JLabel contentPane;
	private JTextField chatTf;
	public String chat;
	private JTextArea ta;
	private String username;
	private JLabel Woonie;
	private JLabel Bazzi;
	private JButton Ready;
	private JButton Map;
	
	public WaitingRoom() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * Create the frame.
	 */
	public WaitingRoom(String username) {
		 this.username = username;
		    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		    setBounds(100, 100, 800, 655);
		    setLocationRelativeTo(null);

		    // 이미지 로드
		    ImageIcon backgroundIcon = new ImageIcon("Images/Waiting.jpg");
		    Image img = backgroundIcon.getImage(); // 원본 이미지 가져오기
		    Image resizedImg = img.getScaledInstance(getWidth(),620, Image.SCALE_SMOOTH); // 이미지 리사이즈
		    ImageIcon resizedIcon = new ImageIcon(resizedImg); // 리사이즈된 이미지로 새 ImageIcon 생성

		    // 배경 JLabel 설정
		    contentPane = new JLabel(resizedIcon);
		    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		    contentPane.setLayout(null);
		    
		    // 프레임의 ContentPane 설정
		    setContentPane(contentPane);
		    
		    // 추가 UI 구성
		    setVisible(true);
	    contentPane.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                // 마우스 이동 시 좌표 출력
                int x = e.getX();  // 마우스 X 좌표
                int y = e.getY();  // 마우스 Y 좌표
                System.out.println("마우스 이동 위치: X = " + x + ", Y = " + y);
            }
        });
	    
	   
	
//		select1.addMouseListener(new MouseListener() {
//
//			@Override
//			public void mouseReleased(MouseEvent e) {
//				select1.setBackground(Color.BLUE);
//			}
//
//			@Override
//			public void mousePressed(MouseEvent e) {
//				select1.setBackground(Color.RED);
//			}
//
//			@Override
//			public void mouseExited(MouseEvent e) {
//				setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
//				select1.setBackground(new Color(0, 191, 255));
//			}
//
//			@Override
//			public void mouseEntered(MouseEvent e) {
//				setCursor(new Cursor(Cursor.HAND_CURSOR));
//				select1.setBackground(Color.BLUE);
//			}
//
//			@Override
//			public void mouseClicked(MouseEvent e) {
//				new CookieMap(username);
//			}
//		});


//		select2.addMouseListener(new MouseListener() {
//
//			@Override
//			public void mouseReleased(MouseEvent e) {
//				select2.setBackground(Color.BLUE);
//			}
//
//			@Override
//			public void mousePressed(MouseEvent e) {
//				select2.setBackground(Color.RED);
//			}
//
//			@Override
//			public void mouseExited(MouseEvent e) {
//				setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
//				select2.setBackground(new Color(0, 191, 255));
//			}
//
//			@Override
//			public void mouseEntered(MouseEvent e) {
//				setCursor(new Cursor(Cursor.HAND_CURSOR));
//				select2.setBackground(Color.BLUE);
//			}
//
//			@Override
//			public void mouseClicked(MouseEvent e) {
//				new PirateMap(username);
//			}
//		});
//        
	    
	    
		ImageIcon icon = new ImageIcon("images/mapButton.jpg");
		Image img1 = icon.getImage(); // ImageIcon에서 Image 객체 얻기
	    Image scaledImg = img1.getScaledInstance(120, 50, Image.SCALE_SMOOTH); // 크기 조정
	    
        Map=new JButton(icon);
        Map.setBounds(643, 442, 120,50);
        contentPane.add(Map);
	    
        JPanel Mappanel = new JPanel();
        Mappanel.setBackground(Color.blue); // 패널 배경색 설정
        Mappanel.setBounds(478, 15, 294, 250); // 패널 위치와 크기 설정
        
        Map.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 패널을 프레임에 추가하고 리프레시
                getContentPane().add(Mappanel);
                revalidate(); // 레이아웃을 다시 계산하여 변경사항을 반영
                repaint(); // 화면을 다시 그려서 패널이 나타나게 함
            }
        });
        
		chatTf = new JTextField();
		chatTf.setBackground(new Color(173, 216, 230));
	    chatTf.setBounds(181, 541, 223, 22);
	    contentPane.add(chatTf);
	    chatTf.setColumns(10);
        
		ta = new JTextArea();
		ta.setBackground(new Color(30, 144, 255));  // 배경 색 설정 (연한 파란색)
		
		ta.setBounds(19, 430, 400, 90);  // JTextArea 크기와 위치 설정
		ta.setOpaque(true);  // 배경을 불투명하게 설정 (기본값)
		contentPane.add(ta); 
		ta.setEditable(false);  // 텍스트 영역을 수정할 수 없게 설정
	    
		Bazzi = new JLabel(new ImageIcon("Images/bazzi_front.png"));
	    Bazzi.setBounds(38, 111, 60, 72);
	    contentPane.add(Bazzi);
	    
	 
	    
	
		ChatThread ct = new ChatThread();
		ct.send(username);
		ct.start();

		chatTf.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == 10) {
					
					String msg = chatTf.getText();
					chatTf.setText("");
					
					ct.send(msg);
				}
			}
		});

	    Ready=new JButton();
	    Ready.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ct.send("Ready");
			}
		});
		  setVisible(true);
		  
	}
	
    
	class ChatThread extends Thread {

		private Socket socket;
		private BufferedReader reader;
		private PrintWriter writer;

		public ChatThread() {
			
			try {
				socket = new Socket("localhost", 5000);//채팅서버 접속
				writer = new PrintWriter(socket.getOutputStream(), true);
				reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}

		@Override
		public void run() {
			try {
				String line;
				while ((line = reader.readLine()) != null) {
					ta.append(line + "\n");
					repaint();
					 if (line.contains("님 환영합니다.")) {
						 if(!line.contains(username))
	                        addUsernameLabel(); // "username님 환영합니다"에서 username만 추출
	                    }
					 if (line.contains("님 입장했습니다.")) {
	                        addUsernameLabel(); // "username님 환영합니다"에서 username만 추출
	                    }
					System.out.println(line);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public void send(String msg) {
			writer.println(msg);
		}
		public void addUsernameLabel() {
			    Woonie= new JLabel(new ImageIcon("Images/woonie_front.png"));
			    Woonie.setBounds(145, 111, 60, 72);
			    contentPane.add(Woonie);
			    repaint();
		}
	}

}