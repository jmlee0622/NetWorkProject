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
   int i=0;
   int enemyX = 100;
   int enemyY = 30;
   private boolean bombReady = false;
   private boolean bombReadys = false;
   private boolean walking = false;
   private boolean more = false;
   int sendX=100;
   int sendY=30;
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
   private ImageIcon[] item2 = { new ImageIcon("images/speed.png"),new ImageIcon("images/morewater.png"),new ImageIcon("images/powers.png"),null,null,null,null};
   ArrayList<String> list = new ArrayList<>();
   
   Random random = new Random();

   JLabel bazzi = new JLabel(new ImageIcon("images/bazzi_front.png"));
   JLabel woonie = new JLabel(new ImageIcon("images/woonie_front.png"));
   GameThread gt;
 
   
   public void DropBomb() {//플레이어 폭탄
	     this.x = myX;  // 플레이어의 x 좌표
	     this.y = myY;  // 플레이어의 y 좌표
	     
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
	    Runnable runnable = new Runnable() {
	        @Override
	        public void run() {
	            

	            try { 
	                Thread.sleep(2000);  // 폭탄이 2초 후에 폭발하도록 대기
	                bu.setVisible(false);
	                // 폭발 이펙트 (상, 우, 하, 좌 방향)
	                ImageIcon bup = new ImageIcon("images/bup.png");         
	                ImageIcon bright = new ImageIcon("images/bright.png");
	                ImageIcon bdown = new ImageIcon("images/bdown.png");
	                ImageIcon bleft = new ImageIcon("images/bleft.png");
	                ImageIcon bcenter = new ImageIcon("images/bcenter.png");
	                
	                Image bupImg = bup.getImage().getScaledInstance(45, 60, Image.SCALE_SMOOTH);
	                Image bdownImg = bdown.getImage().getScaledInstance(45, 60, Image.SCALE_SMOOTH);
	                
	                // 좌우 이미지를 가로로 확대
	                Image brightImg = bright.getImage().getScaledInstance(60, 45, Image.SCALE_SMOOTH);
	                Image bleftImg = bleft.getImage().getScaledInstance(60, 45, Image.SCALE_SMOOTH);
	                Image bcenterImg = bcenter.getImage().getScaledInstance(60, 45, Image.SCALE_SMOOTH);
	                
	                JLabel bupp = new JLabel(new ImageIcon(bupImg));
	                JLabel br = new JLabel(new ImageIcon(brightImg));
	                JLabel bd = new JLabel(new ImageIcon(bdownImg));
	                JLabel bl = new JLabel(new ImageIcon(bleftImg));
	                JLabel bc = new JLabel(new ImageIcon(bcenterImg));
	                
	                bupp.setSize(45, 60);
	                br.setSize(60, 45);
	                bd.setSize(45, 60);
	                bl.setSize(60, 45);
	                bc.setSize(60, 45);
	                
	                	 
	 	            bupp.setLocation(bu.getLocation().x, bu.getLocation().y - 60);  // 상	 	                
	 	            br.setLocation(bu.getLocation().x + 45, bu.getLocation().y);  // 우	 	               
	 	            bd.setLocation(bu.getLocation().x, bu.getLocation().y + 45);  // 하	 	                
	 	            bl.setLocation(bu.getLocation().x - 60, bu.getLocation().y);  // 좌
	 	            bc.setLocation(bu.getLocation().x, bu.getLocation().y); 
	                // 폭발 이펙트 라벨 크기 설정 및 위치 지정
	               
	                
	                
	                // 각 이펙트 라벨 추가
	                contentPane.add(bupp);
	                contentPane.add(br);
	                contentPane.add(bd);
	                contentPane.add(bl);
	                contentPane.add(bc);
	                
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
	                bc.setVisible(false);
	                
	                
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
	    	  
	         while(true) {
	            try {
	            new Thread(gameovers).start();
	            Thread.sleep(1000);
	            Die();
	            
	            } catch (InterruptedException e) {
	               // TODO Auto-generated catch block
	               e.printStackTrace();
	            }
	            
	         }
	         
	      }
	   };
	   Runnable gameovers = new Runnable() {

		      @Override
		      public void run() {
		    	 
		         while(true) {
		            try {
		            if(die==true) {
		            	Thread.sleep(100);
	    	            bazziCurrent("images/b22.png");
	    	            Thread.sleep(100);
	    	            bazziCurrent("images/b33.png");
	    	            Thread.sleep(100);
	    	            bazziCurrent("images/b44.png");
	    	            Thread.sleep(100);
	    	            bazziCurrent("images/b55.png");
	    	            Thread.sleep(100);
	    	            bazziCurrent("images/b66.png");
	    	            Thread.sleep(100);
	    	            bazziCurrent("images/b77.png");
	    	            Thread.sleep(100);
	    	            bazziCurrent("images/b88.png");
	    	            Thread.sleep(100000);
		            }
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
      enemyX = x + 16;
      enemyBy = y + 45;

      Runnable runnable = new Runnable() {
	        @Override
	        public void run() {
	            

	            try {
	            	
		            
	                Thread.sleep(2000);  // 폭탄이 2초 후에 폭발하도록 대기
	                bu.setVisible(false);
	                // 폭발 이펙트 (상, 우, 하, 좌 방향)
	                ImageIcon bup = new ImageIcon("images/bup.png");         
	                ImageIcon bright = new ImageIcon("images/bright.png");
	                ImageIcon bdown = new ImageIcon("images/bdown.png");
	                ImageIcon bleft = new ImageIcon("images/bleft.png");
	                ImageIcon bcenter = new ImageIcon("images/bcenter.png");
	                
	                Image bupImg = bup.getImage().getScaledInstance(45, 60, Image.SCALE_SMOOTH);
	                Image bdownImg = bdown.getImage().getScaledInstance(45, 60, Image.SCALE_SMOOTH);
	                Image bcenterImg = bcenter.getImage().getScaledInstance(60, 45, Image.SCALE_SMOOTH);
	                // 좌우 이미지를 가로로 확대
	                Image brightImg = bright.getImage().getScaledInstance(60, 45, Image.SCALE_SMOOTH);
	                Image bleftImg = bleft.getImage().getScaledInstance(60, 45, Image.SCALE_SMOOTH);
	                
	                JLabel bupp = new JLabel(new ImageIcon(bupImg));
	                JLabel br = new JLabel(new ImageIcon(brightImg));
	                JLabel bd = new JLabel(new ImageIcon(bdownImg));
	                JLabel bl = new JLabel(new ImageIcon(bleftImg));
	                JLabel bc = new JLabel(new ImageIcon(bcenterImg));
	                
	                // 폭발 이펙트 라벨 크기 설정 및 위치 지정
	                bupp.setSize(45, 60);
	                bupp.setLocation(bu.getLocation().x, bu.getLocation().y - 60);  // 상
	                br.setSize(60, 45);
	                br.setLocation(bu.getLocation().x + 40, bu.getLocation().y);  // 우
	                bd.setSize(45, 60);
	                bd.setLocation(bu.getLocation().x, bu.getLocation().y + 40);  // 하
	                bl.setSize(60, 45);
	                bl.setLocation(bu.getLocation().x - 60, bu.getLocation().y);  // 좌
	                bc.setSize(60, 45);
	                bc.setLocation(bu.getLocation().x, bu.getLocation().y); 
	                // 각 이펙트 라벨 추가
	                contentPane.add(bupp);
	                contentPane.add(br);
	                contentPane.add(bd);
	                contentPane.add(bl);
	                contentPane.add(bc);

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
	                bc.setVisible(false);

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
   public void MoreBomb(int imgX, int imgY) {
	   
	   this.x = imgX;  // 플레이어의 x 좌표
	    this.y = imgY;  // 플레이어의 y 좌표
	   
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
       enemyBx = x + 16;
       enemyBy = y + 45;
	    Runnable runnable = new Runnable() {
	        @Override
	        public void run() {
	            

	            try {
	            	
		            
		            
	                Thread.sleep(2000);  // 폭탄이 2초 후에 폭발하도록 대기
	                bu.setVisible(false);
	                // 폭발 이펙트 (상, 우, 하, 좌 방향)
	                ImageIcon bup = new ImageIcon("images/bup.png");         
	                ImageIcon bright = new ImageIcon("images/bright.png");
	                ImageIcon bdown = new ImageIcon("images/bdown.png");
	                ImageIcon bleft = new ImageIcon("images/bleft.png");
	                ImageIcon bcenter = new ImageIcon("images/bcenter.png");
	                
	                Image bupImg = bup.getImage().getScaledInstance(45, 60, Image.SCALE_SMOOTH);
	                Image bdownImg = bdown.getImage().getScaledInstance(45, 60, Image.SCALE_SMOOTH);
	                
	                // 좌우 이미지를 가로로 확대
	                Image brightImg = bright.getImage().getScaledInstance(60, 45, Image.SCALE_SMOOTH);
	                Image bleftImg = bleft.getImage().getScaledInstance(60, 45, Image.SCALE_SMOOTH);
	                Image bcenterImg = bcenter.getImage().getScaledInstance(60, 45, Image.SCALE_SMOOTH);
	                
	                JLabel bupp = new JLabel(new ImageIcon(bupImg));
	                JLabel br = new JLabel(new ImageIcon(brightImg));
	                JLabel bd = new JLabel(new ImageIcon(bdownImg));
	                JLabel bl = new JLabel(new ImageIcon(bleftImg));
	                JLabel bc = new JLabel(new ImageIcon(bcenterImg));
	                
	                ImageIcon bdowns = new ImageIcon("images/bdowns.png");         
	                ImageIcon brights = new ImageIcon("images/brights.png");
	                ImageIcon bups = new ImageIcon("images/bups.png");         
	                ImageIcon blefts = new ImageIcon("images/blefts.png");
	                
	                Image bdownImgs = bdowns.getImage().getScaledInstance(45, 60, Image.SCALE_SMOOTH);
	                Image brightss = brights.getImage().getScaledInstance(45, 45, Image.SCALE_SMOOTH);
	                Image bupImgs = bups.getImage().getScaledInstance(45, 60, Image.SCALE_SMOOTH);
	                Image bleftss = blefts.getImage().getScaledInstance(60, 45, Image.SCALE_SMOOTH);
	                
	                JLabel bupps = new JLabel(new ImageIcon(bupImgs));
	                JLabel brs = new JLabel(new ImageIcon(brightss));
	                JLabel bds = new JLabel(new ImageIcon(bdownImgs));
	                JLabel bls = new JLabel(new ImageIcon(bleftss));
	                
	                bupp.setSize(45, 60);
	                br.setSize(60, 45);
	                bd.setSize(45, 60);
	                bl.setSize(60, 45);
	                bc.setSize(60, 45);
	                bupps.setSize(45, 60);
	                brs.setSize(60, 45);
	                bds.setSize(45, 60);
	                bls.setSize(60, 45);
	                
	               
	                bupps.setLocation(bu.getLocation().x, bu.getLocation().y - 60);  // 상 
 	                brs.setLocation(bu.getLocation().x + 45, bu.getLocation().y);  // 우
 	                bds.setLocation(bu.getLocation().x, bu.getLocation().y + 45);  // 하
 	                bls.setLocation(bu.getLocation().x - 60, bu.getLocation().y);
		            bupp.setLocation(bu.getLocation().x, bu.getLocation().y - 100);  // 상  
		            br.setLocation(bu.getLocation().x + 90, bu.getLocation().y);  // 우   
		            bd.setLocation(bu.getLocation().x, bu.getLocation().y + 100);  // 하   
		            bl.setLocation(bu.getLocation().x - 100, bu.getLocation().y);  // 좌
		            bc.setLocation(bu.getLocation().x, bu.getLocation().y); 
	                
	                
	                // 각 이펙트 라벨 추가
	                contentPane.add(bupp);
	                contentPane.add(br);
	                contentPane.add(bd);
	                contentPane.add(bl);
	                contentPane.add(bc);
	                contentPane.add(bupps);
	                contentPane.add(brs);
	                contentPane.add(bds);
	                contentPane.add(bls);
	                
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
	                bupps.setVisible(false);
	                brs.setVisible(false);
	                bds.setVisible(false);
	                bls.setVisible(false);
	                bu.setVisible(false);
	                bc.setVisible(false);
	                
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
 public void MoreBomb() {
	   
	   this.x = myX;  // 플레이어의 x 좌표
	    this.y = myY;  // 플레이어의 y 좌표
	   
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
	    Runnable runnable = new Runnable() {
	        @Override
	        public void run() {
	            

	            try {
	            	
		            
		            
	                Thread.sleep(2000);  // 폭탄이 2초 후에 폭발하도록 대기
	                bu.setVisible(false);
	                // 폭발 이펙트 (상, 우, 하, 좌 방향)
	                ImageIcon bup = new ImageIcon("images/bup.png");         
	                ImageIcon bright = new ImageIcon("images/bright.png");
	                ImageIcon bdown = new ImageIcon("images/bdown.png");
	                ImageIcon bleft = new ImageIcon("images/bleft.png");
	                ImageIcon bcenter = new ImageIcon("images/bcenter.png");
	                
	                Image bupImg = bup.getImage().getScaledInstance(45, 60, Image.SCALE_SMOOTH);
	                Image bdownImg = bdown.getImage().getScaledInstance(45, 60, Image.SCALE_SMOOTH);
	                
	                // 좌우 이미지를 가로로 확대
	                Image brightImg = bright.getImage().getScaledInstance(60, 45, Image.SCALE_SMOOTH);
	                Image bleftImg = bleft.getImage().getScaledInstance(60, 45, Image.SCALE_SMOOTH);
	                Image bcenterImg = bcenter.getImage().getScaledInstance(60, 45, Image.SCALE_SMOOTH);
	                
	                JLabel bupp = new JLabel(new ImageIcon(bupImg));
	                JLabel br = new JLabel(new ImageIcon(brightImg));
	                JLabel bd = new JLabel(new ImageIcon(bdownImg));
	                JLabel bl = new JLabel(new ImageIcon(bleftImg));
	                JLabel bc = new JLabel(new ImageIcon(bcenterImg));
	                
	                ImageIcon bdowns = new ImageIcon("images/bdowns.png");         
	                ImageIcon brights = new ImageIcon("images/brights.png");
	                ImageIcon bups = new ImageIcon("images/bups.png");         
	                ImageIcon blefts = new ImageIcon("images/blefts.png");
	                
	                Image bdownImgs = bdowns.getImage().getScaledInstance(45, 60, Image.SCALE_SMOOTH);
	                Image brightss = brights.getImage().getScaledInstance(45, 45, Image.SCALE_SMOOTH);
	                Image bupImgs = bups.getImage().getScaledInstance(45, 60, Image.SCALE_SMOOTH);
	                Image bleftss = blefts.getImage().getScaledInstance(60, 45, Image.SCALE_SMOOTH);
	                
	                JLabel bupps = new JLabel(new ImageIcon(bupImgs));
	                JLabel brs = new JLabel(new ImageIcon(brightss));
	                JLabel bds = new JLabel(new ImageIcon(bdownImgs));
	                JLabel bls = new JLabel(new ImageIcon(bleftss));
	                
	                bupp.setSize(45, 60);
	                br.setSize(60, 45);
	                bd.setSize(45, 60);
	                bl.setSize(60, 45);
	                bc.setSize(60, 45);
	                bupps.setSize(45, 60);
	                brs.setSize(60, 45);
	                bds.setSize(45, 60);
	                bls.setSize(60, 45);
	                
	               
	                bupps.setLocation(bu.getLocation().x, bu.getLocation().y - 60);  // 상 
 	                brs.setLocation(bu.getLocation().x + 45, bu.getLocation().y);  // 우
 	                bds.setLocation(bu.getLocation().x, bu.getLocation().y + 45);  // 하
 	                bls.setLocation(bu.getLocation().x - 60, bu.getLocation().y);
		            bupp.setLocation(bu.getLocation().x, bu.getLocation().y - 100);  // 상  
		            br.setLocation(bu.getLocation().x + 90, bu.getLocation().y);  // 우   
		            bd.setLocation(bu.getLocation().x, bu.getLocation().y + 100);  // 하   
		            bl.setLocation(bu.getLocation().x - 100, bu.getLocation().y);  // 좌
		            bc.setLocation(bu.getLocation().x, bu.getLocation().y); 
	                
	                
	                // 각 이펙트 라벨 추가
	                contentPane.add(bupp);
	                contentPane.add(br);
	                contentPane.add(bd);
	                contentPane.add(bl);
	                contentPane.add(bc);
	                contentPane.add(bupps);
	                contentPane.add(brs);
	                contentPane.add(bds);
	                contentPane.add(bls);
	                
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
	                bupps.setVisible(false);
	                brs.setVisible(false);
	                bds.setVisible(false);
	                bls.setVisible(false);
	                bu.setVisible(false);
	                bc.setVisible(false);
	                
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
	   if(!bombReady) {
	      if((myX>bx-50 && myX<bx+45) &&(myY>by-35 &&myY<by+15)) {
	         die = true;
	      }
	      else if((myX>bx-5 && myX<bx+35) &&(myY>by-65 &&myY<by+35)) {
	         die = true;
	      }
	   }else {
		   if((myX>bx-100 && myX<bx+100) &&(myY>by-35 &&myY<by+15)) {
		         die = true;
		      }
		      else if((myX>bx-5 && myX<bx+35) &&(myY>by-100 &&myY<by+100)) {
		         die = true;
		      }
	   }
	   if(!bombReady) {
       for (int i = 0; i < item.size(); i++) {

        
         // 496.525
         y -= 40;
         if ((bx + 40 >= item.get(i).getX() && bx + 40 <= item.get(i).getX() + 16)
               && (by >= item.get(i).getY() && by <= item.get(i).getY() + 5)) {
            item.get(i).setIcon(null);

            item3 = item2[0];
            
           
    
            if(item3==null) {
	        list.add("a");
            }else {
            list.add(item3.toString());
            }
	           
            itemLabel = new JLabel(item3);
            itemLabel.setLocation(item.get(i).getX(), item.get(i).getY());
            itemLabel.setSize(40, 40);
            contentPane.add(itemLabel);
            itemlist.add(itemLabel);
            item.remove(i);

         } else if ((bx >= item.get(i).getX() && bx <= item.get(i).getX() + 16)
               && (by + 40 >= item.get(i).getY() && by + 40 <= item.get(i).getY() + 5)) {
            item.get(i).setIcon(null);

            item3 = item2[1];
            
          
            if(item3==null) {
	        list.add("a");
            }else {
            list.add(item3.toString());
            }
            
	        
            itemLabel = new JLabel(item3);
            itemLabel.setLocation(item.get(i).getX(), item.get(i).getY());
            itemLabel.setSize(40, 40);
            contentPane.add(itemLabel);
            itemlist.add(itemLabel);
            item.remove(i);

         } else if ((bx - 40 >= item.get(i).getX() && bx - 40 <= item.get(i).getX() + 16)
               && (by >= item.get(i).getY() && by <= item.get(i).getY() + 5)) {

            item.get(i).setIcon(null);
            item3 = item2[2];
            
            
            if(item3==null) {
	        list.add("a");
            }else {
            list.add(item3.toString());
            }
	            
            itemLabel = new JLabel(item3);
            itemLabel.setLocation(item.get(i).getX(), item.get(i).getY());
            itemLabel.setSize(40, 40);
            contentPane.add(itemLabel);
            itemlist.add(itemLabel);
            item.remove(i);

         } else if ((bx >= item.get(i).getX() && bx <= item.get(i).getX() + 16)
               && (by - 40 >= item.get(i).getY() && by - 40 <= item.get(i).getY() + 5)) {
            item.get(i).setIcon(null);

            item3 = item2[random.nextInt(6)];
            
            
            if(item3==null) {
	        list.add("a");
            }else {
            list.add(item3.toString());
            }
	            
            itemLabel = new JLabel(item3);
            itemLabel.setLocation(item.get(i).getX(), item.get(i).getY());
            itemLabel.setSize(40, 40);
            contentPane.add(itemLabel);
            itemlist.add(itemLabel);
            item.remove(i);

         }
      }
	   }else {
		   for (int i = 0; i < item.size(); i++) {

		        
		         // 496.525
		         y -= 40;
		         if ((bx+80 >= item.get(i).getX() && bx - 80 <= item.get(i).getX() + 16)
		               && (by >= item.get(i).getY() && by <= item.get(i).getY() + 5)) {
		            item.get(i).setIcon(null);

		            item3 = item2[3];
		            if(item3==null) {
		    	        list.add("a");
		                }else {
		                list.add(item3.toString());
		                }
		    	            
			            
		            itemLabel = new JLabel(item3);
		            itemLabel.setLocation(item.get(i).getX(), item.get(i).getY());
		            itemLabel.setSize(40, 40);
		            contentPane.add(itemLabel);
		            itemlist.add(itemLabel);
		            item.remove(i);

		         } else if ((bx >= item.get(i).getX() && bx <= item.get(i).getX() + 16)
		               && (by + 80 >= item.get(i).getY() && by-80 <= item.get(i).getY() + 5)) {
		            item.get(i).setIcon(null);

		            item3 = item2[random.nextInt(6)];
		            
		            item3 = item2[random.nextInt(6)];
		            if(item3==null) {
		    	        list.add("a");
		                }else {
		                list.add(item3.toString());
		                }
		    	            
			            
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
   public void lineone() {
	   walking=false;
   }
   public void linetwo() {
	   walking=true;
   }
   
   public void enemyCheckLocation(int enemyBx,int enemyBy) {
	 


	   if(!bombReady) {
		      if((enemyX>bx-50 && enemyX<bx+45) &&(enemyY>by-35 && enemyY<by+15)) {
		         die = true;
		      }
		      else if((enemyX>bx-5 && enemyX<bx+35) &&(enemyY>by-65 &&enemyY<by+35)) {
		         die = true;
		      }
		   }else {
			   if((enemyX>bx-100 && enemyX<bx+100) &&(enemyY>by-35 &&enemyY<by+15)) {
			         die = true;
			      }
			      else if((enemyX>bx-5 && enemyX<bx+35) &&(enemyY>by-100 &&enemyY<by+100)) {
			         die = true;
			      }
		   }
	   if(!bombReady) {
	      for (int i = 0; i < item.size(); i++) {

	        
	        
	         y -= 40;
	         if ((enemyBx + 40 >= item.get(i).getX() && enemyBx + 40 <= item.get(i).getX() + 16)
	               && (enemyBy >= item.get(i).getY() && enemyBy <= item.get(i).getY() + 5)) {
	            item.get(i).setIcon(null);

	            item3 = item2[2]; 
	            itemLabel = new JLabel(item3);
	            itemLabel.setLocation(item.get(i).getX(), item.get(i).getY());
	            itemLabel.setSize(40, 40);
	            contentPane.add(itemLabel);
	            itemlist.add(itemLabel);
	            item.remove(i);
	            if(item3==null) {
	    	        list.add("a");
	                }else {
	                list.add(item3.toString());
	            }

	         } else if ((enemyBx >= item.get(i).getX() && enemyBx <= item.get(i).getX() + 16)
	               && (enemyBy + 40 >= item.get(i).getY() && enemyBy + 40 <= item.get(i).getY() + 5)) {
	            item.get(i).setIcon(null);

	            item3 = item2[3];   
	            itemLabel = new JLabel(item3);
	            itemLabel.setLocation(item.get(i).getX(), item.get(i).getY());
	            itemLabel.setSize(40, 40);
	            contentPane.add(itemLabel);
	            itemlist.add(itemLabel);
	            item.remove(i);
	            if(item3==null) {
	    	        list.add("a");
	                }else {
	                list.add(item3.toString());
	            }

	         } else if ((enemyBx - 40 >= item.get(i).getX() && enemyBx - 40 <= item.get(i).getX() + 16)
	               && (enemyBy >= item.get(i).getY() && enemyBy <= item.get(i).getY() + 5)) {

	            item.get(i).setIcon(null);
	            item3 = item2[0];    
	            itemLabel = new JLabel(item3);
	            itemLabel.setLocation(item.get(i).getX(), item.get(i).getY());
	            itemLabel.setSize(40, 40);
	            contentPane.add(itemLabel);
	            itemlist.add(itemLabel);
	            item.remove(i);
	            if(item3==null) {
	    	        list.add("a");
	                }else {
	                list.add(item3.toString());
	            }
	    	           

	         } else if ((enemyBx >= item.get(i).getX() && enemyBx <= item.get(i).getX() + 16)
	               && (enemyBy - 40 >= item.get(i).getY() && enemyBy - 40 <= item.get(i).getY() + 5)) {
	            item.get(i).setIcon(null);

	            item3 = item2[1];    
	            itemLabel = new JLabel(item3);
	            itemLabel.setLocation(item.get(i).getX(), item.get(i).getY());
	            itemLabel.setSize(40, 40);
	            contentPane.add(itemLabel);
	            itemlist.add(itemLabel);
	            item.remove(i);
	            if(item3==null) {
	    	        list.add("a");
	                }else {
	                list.add(item3.toString());
	            }

	         }

	         // item.get(i).setIcon(null); //23
	      }
	   }else {
		   for (int i = 0; i < item.size(); i++) {

		        
		         // 496.525
		         y -= 40;
		         if ((bx+80 >= item.get(i).getX() && bx - 80 <= item.get(i).getX() + 16)
		               && (by >= item.get(i).getY() && by <= item.get(i).getY() + 5)) {
		            item.get(i).setIcon(null);

		            item3 = item2[random.nextInt(6)];  
		            itemLabel = new JLabel(item3);
		            itemLabel.setLocation(item.get(i).getX(), item.get(i).getY());
		            itemLabel.setSize(40, 40);
		            contentPane.add(itemLabel);
		            itemlist.add(itemLabel);
		            item.remove(i);
		            if(item3==null) {
		    	        list.add("a");
		                }else {
		                list.add(item3.toString());
		            }
		    	            

		         } else if ((bx >= item.get(i).getX() && bx <= item.get(i).getX() + 16)
		               && (by + 80 >= item.get(i).getY() && by-80 <= item.get(i).getY() + 5)) {
		            item.get(i).setIcon(null);

		            item3 = item2[random.nextInt(6)];    
		            itemLabel = new JLabel(item3);
		            itemLabel.setLocation(item.get(i).getX(), item.get(i).getY());
		            itemLabel.setSize(40, 40);
		            contentPane.add(itemLabel);
		            itemlist.add(itemLabel);
		            item.remove(i);
		            if(item3==null) {
		    	        list.add("a");
		                }else {
		                list.add(item3.toString());
		            }
		    	            

		         } 
       // item.get(i).setIcon(null); //23
    }
	   }

	      
	   }
   
   public void ItemSpeed() {
      for (int i = 0; i < itemlist.size(); i++) {
         if ((myX >= itemlist.get(i).getX() -20 && myX <= itemlist.get(i).getX() + 10)
               && (myY >= itemlist.get(i).getY() - 10 && myY <= itemlist.get(i).getY() + 30)) {
        	
        	itemlist.get(i).setIcon(null);
        	
            JLabel item = itemlist.get(i);
          
            
            if(list.get(i).toString().equals(item2[0].toString())) {                
                speed -= 5;
                if (speed < 40) {
                   speed = 40;
                }
            }
            if(list.get(i).toString().equals(item2[2].toString())) {
            	
            	bombReady = true;  
            	gt.send(username + ":MORE:o");
            }
            if(list.get(i).toString().equals(item2[1].toString())) {
            	
            	more=true;
            	
            	
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
      
      contentPane.add(bazzi);
      bazzi.setSize(44, 56);
      bazzi.setLocation(myX, myY);
    
      contentPane.add(woonie);
      woonie.setSize(44, 56);
      woonie.setLocation(enemyX, enemyY);
      
   }

   public void keyProcess() {
      if (keyU == true) {
         bazziCurrent("images/bazzi_back.png");
         myY -= 10;
         sendY+=10;
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
         sendY-=10;
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
         sendX+=10;
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
         sendX-=10;
         bazzi.setLocation(myX, myY);
         move = "R";
         gt.send(username + ":" + "MOVE:" + move);
         if (myX > 580) {
            myX = 580;
         }
      }
   }
   public void keyProcesses() {
	      if (keyU == true) {
	         woonieCurrent("images/woonie_back.png");
	         myY -= 10;
	         sendY+=10;
	         woonie.setLocation(enemyX, enemyY);
	         move = "U";
	         gt.send(username + ":" + "MOVE:" + move);
	         if (myY < 0) {
	            myY = 0;
	         }
	      }
	      if (keyD == true) {
	         woonieCurrent("images/woonie_front.png");
	         myY += 10;
	         sendY-=10;
	         woonie.setLocation(enemyX, enemyY);
	         move = "D";
	         gt.send(username + ":" + "MOVE:" + move);
	         if (myY > 550) {
	            myY = 550;
	         }
	      }
	      if (keyL == true) {
	         woonieCurrent("images/woonie_left.png");
	         myX -= 10;
	         sendX+=10;
	         woonie.setLocation(enemyX, enemyY);
	         move = "L";
	         gt.send(username + ":" + "MOVE:" + move);
	         if (myX < 16) {
	            myX = 16;
	         }
	      }
	      if (keyR == true) {
	         woonieCurrent("images/woonie_right.png");
	         myX += 10;
	         sendX-=10;
	         woonie.setLocation(enemyX, enemyY);
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
            if(!walking) {
            	keyProcess();
            }else {
            	gt.send(username + ":MORE:o");
            	keyProcesses();
            }
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
    

       gt.send(username + ":" + "LOCATIONX:" + sendX);
       gt.send(username + ":" + "LOCATIONY:" + sendY);
       
       
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
                           if(!bombReady) {
                        	   DropBomb();
                           }
                           else {
                    		   MoreBomb();
                    	   }
                           gt.send(username + ":DROP:o");
                           try { 	   
                        	   if(more) {
                        		   Thread.sleep(0);
                        	   }else {
                               Thread.sleep(3000);
                               // 3초 동안 대기
                        	   }
                        	 
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
            socket = new Socket("localhost",6000);//서버 접속
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
                     if (thirdline.equals("L")) {
                        myX -= 10;
                        bazziCurrent("images/bazzi_left.png");
                        bazzi.setLocation(myX, myY);
                        if (myX > 580) {
                           myX = 580;
                        }
                     } else if (thirdline.equals("R")) {
                        myX += 10;
                        bazziCurrent("images/bazzi_right.png");
                        bazzi.setLocation(myX, myY);
                        if (myX < 16) {
                           myX = 16;
                        }
                     } else if (thirdline.equals("U")) {
                        myY -= 10;
                        bazziCurrent("images/bazzi_down.png");
                        bazzi.setLocation(myX, myY);
                        if (myY < 0) {
                           myY = 0;
                        }
                     } else if (thirdline.equals("D")) {
                        myY += 10;
                        bazziCurrent("images/bazzi_front.png");
                        bazzi.setLocation(myX, myY);
                        if (myY > 550) {
                           myY = 550;//문제발생
                        }
                     }

                  }
                  if (secondline.equals("MORE")) {
                	  bombReadys = true;  
                  }
                  if (secondline.equals("DROP")) {
                	  System.out.println(bombReady);                	  
                	  if(!bombReadys) {
                		  DropBomb(enemyX,enemyY);   
                	  }else {
                	  MoreBomb(enemyX,enemyY);  	  
                	  }
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