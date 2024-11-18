package map;

import java.awt.EventQueue;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

import javax.management.RuntimeErrorException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

public class CookieMap extends JFrame {
   private JLabel contentPane;
   private Vector<JLabel> item = new Vector<JLabel>();
   private ArrayList<JLabel> itemlist = new ArrayList<JLabel>();
   private String username;

   ImageIcon item3;
   JLabel itemLabel;
   int myX = 500;
   int myY = 500;

   int enemyX = 100;
   int enemyY = 100;
   String move;
   String shape;
   int x;
   int y;
   int enemyBx,enemyBy;
   int bx, by;
   boolean keyU = false;
   boolean keyD = false;
   boolean keyL = false;
   boolean keyR = false;
   boolean die = false;
   boolean check=true;
   private int speed = 80;
   private ImageIcon[] item2 = { new ImageIcon("images/speed.png"),null,null, null };
   Random random = new Random();

   JLabel bazzi = new JLabel(new ImageIcon("images/bazzi_front.png"));
   JLabel woonie = new JLabel(new ImageIcon("images/woonie_front.png"));
   GameThread gt;

   public void DropBomb() {//플레이어 폭탄
	     this.x = myX;  // 플레이어의 x 좌표
	    this.y = myY;  // 플레이어의 y 좌표

	    Runnable runnable = new Runnable() {
	        @Override
	        public void run() {
	            ImageIcon bubble = new ImageIcon("images/bomb.png");
	            JLabel bu = new JLabel(bubble);

	            // x, y 좌표를 40으로 나누고 다시 40을 곱해 40단위로 위치 맞추기
	            x /= 40;
	            y /= 40;
	            x *= 40;
	            y *= 40;

	            // 폭탄 위치 설정
	            bu.setSize(40, 40);
	            bu.setLocation(x + 16, y + 45);  // 폭탄 위치를 (x, y) 기준으로 설정
	            contentPane.add(bu);
	            bu.setVisible(true);

	            // 폭탄의 실제 폭발 위치 (폭탄의 중심)
	            bx = x + 16;
	            by = y + 45;

	            try {
	                Thread.sleep(2000);  // 폭탄이 2초 후에 폭발하도록 대기

	                // 폭발 이펙트 (상, 우, 하, 좌 방향)
	                ImageIcon bup = new ImageIcon("images/bup.png");         
	                ImageIcon bright = new ImageIcon("images/bright.png");
	                ImageIcon bdown = new ImageIcon("images/bdown.png");
	                ImageIcon bleft = new ImageIcon("images/bleft.png");
	                
	                Image bupImg = bup.getImage().getScaledInstance(45, 60, Image.SCALE_SMOOTH);
	                Image bdownImg = bdown.getImage().getScaledInstance(45, 60, Image.SCALE_SMOOTH);
	                
	                // 좌우 이미지를 가로로 확대
	                Image brightImg = bright.getImage().getScaledInstance(60, 45, Image.SCALE_SMOOTH);
	                Image bleftImg = bleft.getImage().getScaledInstance(60, 45, Image.SCALE_SMOOTH);
	                
	                JLabel bupp = new JLabel(new ImageIcon(bupImg));
	                JLabel br = new JLabel(new ImageIcon(brightImg));
	                JLabel bd = new JLabel(new ImageIcon(bdownImg));
	                JLabel bl = new JLabel(new ImageIcon(bleftImg));
 
	                // 폭발 이펙트 라벨 크기 설정 및 위치 지정
	                bupp.setSize(45, 60);
	                bupp.setLocation(bu.getLocation().x, bu.getLocation().y - 60);  // 상
	                br.setSize(60, 45);
	                br.setLocation(bu.getLocation().x + 40, bu.getLocation().y);  // 우
	                bd.setSize(45, 60);
	                bd.setLocation(bu.getLocation().x, bu.getLocation().y + 40);  // 하
	                bl.setSize(60, 45);
	                bl.setLocation(bu.getLocation().x - 60, bu.getLocation().y);  // 좌

	                // 각 이펙트 라벨 추가
	                contentPane.add(bupp);
	                contentPane.add(br);
	                contentPane.add(bd);
	                contentPane.add(bl);

	                // 화면 새로 고침
	                SwingUtilities.invokeLater(() -> {
	                    contentPane.repaint();
	                });

	                // 폭발 이펙트 후 1초 대기
	                Thread.sleep(1000);

	                // 이펙트 제거
	                bupp.setVisible(false);
	                br.setVisible(false);
	                bd.setVisible(false);
	                bl.setVisible(false);
	                bu.setVisible(false);

	                // 폭탄 위치 체크 (폭탄이 떨어진 후 위치에 대해 체크)
	                checkLocation();
	                bx = bu.getLocation().x;
	                by = bu.getLocation().y;

	                // 화면 새로 고침
	                SwingUtilities.invokeLater(() -> {
	                    contentPane.repaint();
	                });

	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	        }
	    };

	    // 새로운 스레드로 폭탄을 떨어뜨림
	    new Thread(runnable).start();
	}
   
   public void Die() {
      if(die==true) {
         
         JOptionPane.showMessageDialog(null, "패배했습니다.!");
         die=false;
         dispose();
         
      }
   }
   
   Runnable gameover = new Runnable() {

      @Override
      public void run() {
         while (true) {
            Die();
            try {
               Thread.sleep(20);
            } catch (InterruptedException e) {
               // TODO Auto-generated catch block
               e.printStackTrace();
            }
         }
      }
   };
   public void DropBomb(int imgX, int imgY) {//적폭탄
      this.x = imgX;
      this.y = imgY;

      Runnable runnable = new Runnable() {

         @Override
         public void run() {
            ImageIcon bubble = new ImageIcon("images/bomb.png");
            JLabel bu = new JLabel(bubble);
            x /= 40;
            y /= 40;
            x *= 40;
            y *= 40;
            //40px 격자로 배치
            bu.setSize(40, 40);
            bu.setLocation(x + 16, y + 5);
            bu.setVisible(true);
            contentPane.add(bu);
            enemyBx = x + 16;
            enemyBy = y + 5;
            try {
               Thread.sleep(2000);
               bu.setVisible(false);

               ImageIcon bcenter = new ImageIcon("images/bcenter.png");
               JLabel bc = new JLabel(bcenter);
               ImageIcon bup = new ImageIcon("images/bup.png");
               JLabel bupp = new JLabel(bup);
               ImageIcon bright = new ImageIcon("images/bright.png");
               JLabel br = new JLabel(bright);
               ImageIcon bdown = new ImageIcon("images/bdown.png");
               JLabel bd = new JLabel(bdown);
               ImageIcon bleft = new ImageIcon("images/bleft.png");
               JLabel bl = new JLabel(bleft);

               bc.setSize(40, 40);
               bc.setLocation(enemyBx, enemyBy);
               bc.setVisible(true);
               contentPane.add(bc);
               bupp.setSize(40, 40);
               bupp.setLocation(enemyBx, enemyBy - 40);
               bupp.setVisible(true);
               contentPane.add(bupp);
               br.setSize(40, 40);
               br.setLocation(enemyBx + 40, enemyBy);
               br.setVisible(true);
               contentPane.add(br);
               bd.setSize(40, 40);
               bd.setLocation(enemyBx, enemyBy + 40);
               bd.setVisible(true);
               contentPane.add(bd);
               bl.setSize(40, 40);
               bl.setLocation(enemyBx - 40, enemyBy);
               bl.setVisible(true);
               contentPane.add(bl);
               Thread.sleep(1000);
               bc.setVisible(false);
               bupp.setVisible(false);
               br.setVisible(false);
               bd.setVisible(false);
               bl.setVisible(false);
               
               checkLocation();
            } catch (InterruptedException e) {
               e.printStackTrace();
            }
         }
      };
      new Thread(runnable).start();
   }

   public void MyLocation() {//keyR .. keyD 는 특정 위치에 도달했을때 그방향으로 이동할수없도록 제어

      for (int i = 0; i < item.size(); i++) {
         if ((myX >= item.get(i).getX() - 40 && myX <= item.get(i).getX())
               && (myY < item.get(i).getY()+5 && myY > item.get(i).getY() - 35)) {
            keyR = false;
            continue;

         } else if ((myX >= item.get(i).getX() && myX <= item.get(i).getX() + 40)
               && (myY < item.get(i).getY()+5 && myY > item.get(i).getY() - 35)) {
            keyL = false;
            continue;
         } else if ((myX > item.get(i).getX()-10 && myX < item.get(i).getX() + 30)
               && (myY > item.get(i).getY() && myY < item.get(i).getY() + 40)) {
            keyU = false;
            continue;
         } else if ((myX >= item.get(i).getX()-10 && myX <= item.get(i).getX() + 30)
               && (myY+46 > item.get(i).getY()-10&& myY+46 < item.get(i).getY()+40)) {
            keyD = false;
            continue;
         }

      }
   }

   Runnable runable = new Runnable() {

      @Override
      public void run() {
         while (true) {
            MyLocation();
         }
      }
   };

   public void checkLocation() {//아이템과 폭발 충동처리,케릭터 폭탄 위치 확인

	// 폭발 이펙트 상단
	   
	      if((myX>bx-50 && myX<bx+45) &&(myY>by-35 &&myY<by+15)) {
	         die = true;
	      }
	      else if((myX>bx-5 && myX<bx+35) &&(myY>by-65 &&myY<by+35)) {
	         die = true;
	      }
	
 
      for (int i = 0; i < item.size(); i++) {

        
         // 496.525
         y -= 40;
         if ((bx + 40 >= item.get(i).getX() && bx + 40 <= item.get(i).getX() + 16)
               && (by >= item.get(i).getY() && by <= item.get(i).getY() + 5)) {
            item.get(i).setIcon(null);

            item3 = item2[random.nextInt(3)];
            itemLabel = new JLabel(item3);
            itemLabel.setLocation(item.get(i).getX(), item.get(i).getY());
            itemLabel.setSize(40, 40);
            contentPane.add(itemLabel);
            itemlist.add(itemLabel);
            item.remove(i);

         } else if ((bx >= item.get(i).getX() && bx <= item.get(i).getX() + 16)
               && (by + 40 >= item.get(i).getY() && by + 40 <= item.get(i).getY() + 5)) {
            item.get(i).setIcon(null);

            item3 = item2[random.nextInt(3)];
            itemLabel = new JLabel(item3);
            itemLabel.setLocation(item.get(i).getX(), item.get(i).getY());
            itemLabel.setSize(40, 40);
            contentPane.add(itemLabel);
            itemlist.add(itemLabel);
            item.remove(i);

         } else if ((bx - 40 >= item.get(i).getX() && bx - 40 <= item.get(i).getX() + 16)
               && (by >= item.get(i).getY() && by <= item.get(i).getY() + 5)) {

            item.get(i).setIcon(null);
            item3 = item2[random.nextInt(3)];
            itemLabel = new JLabel(item3);
            itemLabel.setLocation(item.get(i).getX(), item.get(i).getY());
            itemLabel.setSize(40, 40);
            contentPane.add(itemLabel);
            itemlist.add(itemLabel);
            item.remove(i);

         } else if ((bx >= item.get(i).getX() && bx <= item.get(i).getX() + 16)
               && (by - 40 >= item.get(i).getY() && by - 40 <= item.get(i).getY() + 5)) {
            item.get(i).setIcon(null);

            item3 = item2[random.nextInt(3)];
            itemLabel = new JLabel(item3);
            itemLabel.setLocation(item.get(i).getX(), item.get(i).getY());
            itemLabel.setSize(40, 40);
            contentPane.add(itemLabel);
            itemlist.add(itemLabel);
            item.remove(i);

         }

         // item.get(i).setIcon(null); //23
      }

      
   }

   Runnable enemyBomb = new Runnable() {
	
	@Override
	public void run() {
		try {
			Thread.sleep(2000);
			enemyCheckLocation(enemyBx,enemyBy);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
	}
};
   
   public void enemyCheckLocation(int enemyBx,int enemyBy) {
	      // ǳ����ġ


	   if((myX>enemyBx-50 && myX<enemyBx+45) &&(myY>enemyBy-35 &&myY<enemyBy+15)) {
	         die = true;
	      }
	      else if((myX>enemyBx-5 && myX<enemyBx+35) &&(myY>enemyBy-65 &&myY<enemyBy+35)) {
	         die = true;
	      }
	      
	      for (int i = 0; i < item.size(); i++) {

	        
	        
	         y -= 40;
	         if ((enemyBx + 40 >= item.get(i).getX() && enemyBx + 40 <= item.get(i).getX() + 16)
	               && (enemyBy >= item.get(i).getY() && enemyBy <= item.get(i).getY() + 5)) {
	            item.get(i).setIcon(null);

	            item3 = item2[random.nextInt(3)];
	            itemLabel = new JLabel(item3);
	            itemLabel.setLocation(item.get(i).getX(), item.get(i).getY());
	            itemLabel.setSize(40, 40);
	            contentPane.add(itemLabel);
	            itemlist.add(itemLabel);
	            item.remove(i);

	         } else if ((enemyBx >= item.get(i).getX() && enemyBx <= item.get(i).getX() + 16)
	               && (enemyBy + 40 >= item.get(i).getY() && enemyBy + 40 <= item.get(i).getY() + 5)) {
	            item.get(i).setIcon(null);

	            item3 = item2[random.nextInt(3)];
	            itemLabel = new JLabel(item3);
	            itemLabel.setLocation(item.get(i).getX(), item.get(i).getY());
	            itemLabel.setSize(40, 40);
	            contentPane.add(itemLabel);
	            itemlist.add(itemLabel);
	            item.remove(i);

	         } else if ((enemyBx - 40 >= item.get(i).getX() && enemyBx - 40 <= item.get(i).getX() + 16)
	               && (enemyBy >= item.get(i).getY() && enemyBy <= item.get(i).getY() + 5)) {

	            item.get(i).setIcon(null);
	            item3 = item2[random.nextInt(3)];
	            itemLabel = new JLabel(item3);
	            itemLabel.setLocation(item.get(i).getX(), item.get(i).getY());
	            itemLabel.setSize(40, 40);
	            contentPane.add(itemLabel);
	            itemlist.add(itemLabel);
	            item.remove(i);

	         } else if ((enemyBx >= item.get(i).getX() && enemyBx <= item.get(i).getX() + 16)
	               && (enemyBy - 40 >= item.get(i).getY() && enemyBy - 40 <= item.get(i).getY() + 5)) {
	            item.get(i).setIcon(null);

	            item3 = item2[random.nextInt(3)];
	            itemLabel = new JLabel(item3);
	            itemLabel.setLocation(item.get(i).getX(), item.get(i).getY());
	            itemLabel.setSize(40, 40);
	            contentPane.add(itemLabel);
	            itemlist.add(itemLabel);
	            item.remove(i);

	         }

	         // item.get(i).setIcon(null); //23
	      }

	      
	   }
   
   public void ItemSpeed() {
      for (int i = 0; i < itemlist.size(); i++) {
         if ((myX >= itemlist.get(i).getX() -20 && myX <= itemlist.get(i).getX() + 10)
               && (myY >= itemlist.get(i).getY() - 10 && myY <= itemlist.get(i).getY() + 30)) {
            itemlist.get(i).setIcon(null);
            speed -= 5;
            if (speed < 40) {
               speed = 40;
            }
         }
      }
   }

   public void bazziCurrent(String imageLocation) {
      bazzi.setIcon(new ImageIcon(imageLocation));
   }

   public void woonieCurrent(String imageLocation) {
      woonie.setIcon(new ImageIcon(imageLocation));
   }

   private void firstLocation() {//플레이어,적의 초기 위치
      // ����
      contentPane.add(bazzi);
      bazzi.setSize(44, 56);
      bazzi.setLocation(myX, myY);
      // ���
      contentPane.add(woonie);
      woonie.setSize(44, 56);
      woonie.setLocation(enemyX, enemyY);
   }

   public void keyProcess() {
      if (keyU == true) {
         bazziCurrent("images/bazzi_back.png");
         myY -= 10;
         bazzi.setLocation(myX, myY);
         move = "U";
         gt.send(username + ":" + "MOVE:" + move);
         if (myY < 0) {
            myY = 0;
         }
      }
      if (keyD == true) {
         bazziCurrent("images/bazzi_front.png");
         myY += 10;
         bazzi.setLocation(myX, myY);
         move = "D";
         gt.send(username + ":" + "MOVE:" + move);
         if (myY > 550) {
            myY = 550;
         }
      }
      if (keyL == true) {
         bazziCurrent("images/bazzi_left.png");
         myX -= 10;
         bazzi.setLocation(myX, myY);
         move = "L";
         gt.send(username + ":" + "MOVE:" + move);
         if (myX < 16) {
            myX = 16;
         }
      }
      if (keyR == true) {
         bazziCurrent("images/bazzi_right.png");
         myX += 10;
         bazzi.setLocation(myX, myY);
         move = "R";
         gt.send(username + ":" + "MOVE:" + move);
         if (myX > 580) {
            myX = 580;
         }
      }
   }

   Runnable runnable = new Runnable() {

      @Override
      public void run() {
         while (true) {
            keyProcess();
            ItemSpeed();
            
            repaint();
            try {
               Thread.sleep(speed);
            } catch (InterruptedException e) {
               e.printStackTrace();
            }
         }
      }
   };

   public CookieMap(String username) {

	   this.username = username;
       setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       setBounds(100, 100, 650, 650);
       setLocationRelativeTo(null);
       contentPane = new JLabel(new ImageIcon("Images/mapbg1.png"));
       contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
       contentPane.setLayout(null);
       setContentPane(contentPane);

       firstLocation();

       gt = new GameThread();
       new Thread(runnable).start();
       new Thread(runable).start();
       new Thread(gameover).start();
       gt.start();

       gt.send(username + ":" + "LOCATIONX:" + myX);
       gt.send(username + ":" + "LOCATIONY:" + myY);

       addKeyListener(new KeyAdapter() {
           @Override
           public void keyPressed(KeyEvent e) {
               switch (e.getKeyCode()) {
                   case KeyEvent.VK_RIGHT:
                       keyR = true;
                       break;
                   case KeyEvent.VK_LEFT:
                       keyL = true;
                       break;
                   case KeyEvent.VK_UP:
                       keyU = true;
                       break;
                   case KeyEvent.VK_DOWN:
                       keyD = true;
                       break;
                   case KeyEvent.VK_SPACE:
                       if (check) {
                           new Thread(one).start();  // 스페이스바를 눌렀을 때만 실행
                       }
                       break;
               }
           }

           Runnable one = new Runnable() {
               @Override
               public void run() {
                   synchronized (this) {
                       if (check) {
                           check = false;  // DropBomb을 실행 중일 때 check 값을 false로 설정
                           DropBomb();
                           gt.send(username + ":DROP:o");
                           try {
                               Thread.sleep(3000);  // 3초 동안 대기
                           } catch (InterruptedException e) {
                               e.printStackTrace();
                           }
                           check = true;  // 3초 후 check 값을 true로 설정하여 다음 bomb이 가능하게 함
                       }
                   }
               }
           };

           @Override
           public void keyReleased(KeyEvent e) {
               switch (e.getKeyCode()) {
                   case KeyEvent.VK_RIGHT:
                       keyR = false;
                       break;
                   case KeyEvent.VK_LEFT:
                       keyL = false;
                       break;
                   case KeyEvent.VK_UP:
                       keyU = false;
                       break;
                   case KeyEvent.VK_DOWN:
                       keyD = false;
                       break;
               }
           }
       });
   

      
      Cookie cookie = new Cookie(15);
      for (int i = 0; i < cookie.size; i++) {
         for (int j = 0; j < cookie.size; j++) {
            cookie.map[i][j] = "1";
            String block = cookie.map[i][j];
            if (i == 0 || j == 14 || i == 14 || j == 0) {
               JLabel cookie2 = new JLabel(new ImageIcon("images/cookie.png"));
               item.add(cookie2);
               this.add(cookie2);
               cookie2.setBounds(i * 40 + 15, j * 40, 45, 45);
            } else if (i + j == 8 || i + j == 20 || i == j + 6 || i == j - 6) {
               JLabel cookie3 = new JLabel(new ImageIcon("images/cookie2.png"));
               item.add(cookie3);
               this.add(cookie3);
               cookie3.setBounds(i * 40 + 15, j * 40, 45, 45);
            }

         }
      }
      EventQueue.invokeLater(new Runnable() {
         public void run() {
            try {
               setVisible(true);
            } catch (Exception e) {
               e.printStackTrace();
            }
         }
      });

   }

   class GameThread extends Thread {

      private Socket socket;
      private BufferedReader reader;
      private PrintWriter writer;

      public GameThread() {
         
         try {
            socket = new Socket("172.30.1.59", 6000);//서버 접속
            writer = new PrintWriter(socket.getOutputStream(), true);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

         } catch (Exception e) {
            e.printStackTrace();
         }

      }

      @Override
      public void run() {//우니의 위치 받아 옮기기
         try {
            String line;
            String firstline, secondline, thirdline;

            while ((line = reader.readLine()) != null) {

             

               String[] firstindex = line.split(":");

               firstline = firstindex[0];
               secondline = firstindex[1];
               thirdline = firstindex[2];

               // System.out.println(firstline);
               // System.out.println(secondline);
               // System.out.println(thirdline);
               if (!(firstline.equals(username))) {
                  if (secondline.equals("LOCATIONX")) {
                     enemyX = Integer.parseInt(thirdline);
                     // System.out.println(Integer.parseInt(thirdline));

                  }
                  if (secondline.equals("LOCATIONY")) {
                     enemyY = Integer.parseInt(thirdline);
                     // System.out.println(Integer.parseInt(thirdline));

                  }

                  if (secondline.equals("MOVE")) {
                     if (thirdline.equals("R")) {
                        enemyX -= 10;
                        woonieCurrent("images/woonie_right.png");
                        woonie.setLocation(enemyX, enemyY);
                        if (enemyX > 580) {
                           enemyX = 580;
                        }
                     } else if (thirdline.equals("L")) {
                        enemyX += 10;
                        woonieCurrent("images/woonie_left.png");
                        woonie.setLocation(enemyX, enemyY);
                        if (enemyX < 16) {
                           enemyX = 16;
                        }
                     } else if (thirdline.equals("U")) {
                        enemyY += 10;
                        woonieCurrent("images/woonie_back.png");
                        woonie.setLocation(enemyX, enemyY);
                        if (enemyY < 0) {
                           enemyY = 0;
                        }
                     } else if (thirdline.equals("D")) {
                        enemyY -= 10;
                        woonieCurrent("images/woonie_front.png");
                        woonie.setLocation(enemyX, enemyY);
                        if (enemyY > 550) {
                           enemyY = 550;
                        }
                     }

                  }
                  if (secondline.equals("DROP")) {
                     DropBomb(enemyX, enemyY);
                     new Thread(enemyBomb).start();
					
                  }

               }

               repaint();
               // System.out.println(line);
            }
         } catch (IOException e) {
            e.printStackTrace();
         }
      }

     
      public void send(String msg) {
         writer.println(msg);
      }
   }
}