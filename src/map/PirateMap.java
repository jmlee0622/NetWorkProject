package map;

import java.awt.EventQueue;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
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

public class PirateMap extends JFrame {
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
   int apple=0;
   int grape=0;
   private boolean bombReady = false;
   private boolean bombReadys = false;
   private boolean bombReadyss = false;
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
   boolean dies = false;
   boolean check=true;
   private int speed = 80;
   private ImageIcon[] item2 = { new ImageIcon("images/speed.png"),new ImageIcon("images/morewater.png"),new ImageIcon("images/powers.png"),null};
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
   
   
   public void Die() {// 첫번째 클라이언트 체력 소진시 호출. 메시지를 띄우고 창을 닫음
	   if(die==true) {
	          JOptionPane.showMessageDialog(null, "1플레이어 패배했습니다.!");
	          die=false;
	          gt.send(username + ":Dead:o");
	          dispose();
	      }
      
   }
   public void Dies() {// 두번째 클라이언트 체력 소진시 호출. 메시지를 띄우고 창을 닫음
	   if(dies==true) {
	          JOptionPane.showMessageDialog(null, "2플레이어 패배했습니다.!");
	          dies=false;
	          gt.send(username + ":Dead:o");
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
	            Dies();
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
		            if(die==true) {// 캐릭터 체력소진시 bazzi 이미지 순차적으로 변경
		            	Thread.sleep(100);
		            	bazziCurrent("images/b11.png");
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
	    	            Thread.sleep(100000);
	    	            
		            }else if(dies==true) {// 캐릭터 체력소진시 woonie 이미지 순차적으로 변경
		            	Thread.sleep(100);
	    	            woonieCurrent("images/w11.png");
	    	            Thread.sleep(100);
	    	            woonieCurrent("images/w22.png");
	    	            Thread.sleep(100);
	    	            woonieCurrent("images/w33.png");
	    	            Thread.sleep(100);
	    	            woonieCurrent("images/w44.png");
	    	            Thread.sleep(100);
	    	            woonieCurrent("images/w55.png");
	    	            Thread.sleep(100);
	    	            woonieCurrent("images/w66.png");
	    	            Thread.sleep(100);
	    	            woonieCurrent("images/w77.png");
	    	            Thread.sleep(100);
	    	            woonieCurrent("images/w88.png");
	    	            Thread.sleep(100);
	    	            woonieCurrent("images/w99.png");
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
	
   
   public void DropBomb(int imgX, int imgY) {//두번째 클라이언트 캐릭터 폭탄
      this.x = imgX;
      this.y = imgY;
      ImageIcon bubble = new ImageIcon("images/bomb.png");
      
      JLabel bu = new JLabel(bubble);

      x /= 40;
      y /= 40;
      x *= 40;
      y *= 40;

      bu.setSize(40, 40);
      bu.setLocation(x + 16, y + 45); 
      contentPane.add(bu);
      bu.setVisible(true);

      bx = x + 16;
      by = y + 45;

      Runnable runnable = new Runnable() {
	        @Override
	        public void run() {	           
	            try {
	                Thread.sleep(2000); 
	                bu.setVisible(false);

	                ImageIcon bup = new ImageIcon("images/bup.png");         
	                ImageIcon bright = new ImageIcon("images/bright.png");
	                ImageIcon bdown = new ImageIcon("images/bdown.png");
	                ImageIcon bleft = new ImageIcon("images/bleft.png");
	                ImageIcon bcenter = new ImageIcon("images/bcenter.png");
	                
	                Image bupImg = bup.getImage().getScaledInstance(45, 60, Image.SCALE_SMOOTH);
	                Image bdownImg = bdown.getImage().getScaledInstance(45, 60, Image.SCALE_SMOOTH);
	                Image bcenterImg = bcenter.getImage().getScaledInstance(60, 45, Image.SCALE_SMOOTH);

	                Image brightImg = bright.getImage().getScaledInstance(60, 45, Image.SCALE_SMOOTH);
	                Image bleftImg = bleft.getImage().getScaledInstance(60, 45, Image.SCALE_SMOOTH);
	                
	                JLabel bupp = new JLabel(new ImageIcon(bupImg));
	                JLabel br = new JLabel(new ImageIcon(brightImg));
	                JLabel bd = new JLabel(new ImageIcon(bdownImg));
	                JLabel bl = new JLabel(new ImageIcon(bleftImg));
	                JLabel bc = new JLabel(new ImageIcon(bcenterImg));
	                
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
	                contentPane.add(bupp);
	                contentPane.add(br);
	                contentPane.add(bd);
	                contentPane.add(bl);
	                contentPane.add(bc);

	                SwingUtilities.invokeLater(() -> {
	                    contentPane.repaint();
	                });

	                Thread.sleep(1000);

	                bupp.setVisible(false);
	                br.setVisible(false);
	                bd.setVisible(false);
	                bl.setVisible(false);
	                bc.setVisible(false);

	                checkLocation();
	                bx = bu.getLocation().x;
	                by = bu.getLocation().y;

	                SwingUtilities.invokeLater(() -> {
	                    contentPane.repaint();
	                });

	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	        }
	    };
	    new Thread(runnable).start();
	}
   public void MoreBomb(int imgX, int imgY) {//물줄기 증가시 두번째 클라이언트 캐릭터 폭탄
	   
	   this.x = imgX; 
	    this.y = imgY; 
	   
	     ImageIcon bubble = new ImageIcon("images/bomb.png");
	     JLabel bu = new JLabel(bubble);

       x /= 40;
       y /= 40;
       x *= 40;
       y *= 40;

       bu.setSize(40, 40);
       bu.setLocation(x + 16, y + 45);  
       contentPane.add(bu);
       bu.setVisible(true);

       bx = x + 16;
       by = y + 45;
	    Runnable runnable = new Runnable() {
	        @Override
	        public void run() {	           
	            try {
	                Thread.sleep(2000); 
	                bu.setVisible(false);
	                ImageIcon bup = new ImageIcon("images/bup.png");         
	                ImageIcon bright = new ImageIcon("images/bright.png");
	                ImageIcon bdown = new ImageIcon("images/bdown.png");
	                ImageIcon bleft = new ImageIcon("images/bleft.png");
	                ImageIcon bcenter = new ImageIcon("images/bcenter.png");
	                
	                Image bupImg = bup.getImage().getScaledInstance(45, 60, Image.SCALE_SMOOTH);
	                Image bdownImg = bdown.getImage().getScaledInstance(45, 60, Image.SCALE_SMOOTH);
	                
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
	                              
	                bupps.setLocation(bu.getLocation().x, bu.getLocation().y - 60); 
 	                brs.setLocation(bu.getLocation().x + 45, bu.getLocation().y); 
 	                bds.setLocation(bu.getLocation().x, bu.getLocation().y + 45);
 	                bls.setLocation(bu.getLocation().x - 60, bu.getLocation().y);
		            bupp.setLocation(bu.getLocation().x, bu.getLocation().y - 100);  
		            br.setLocation(bu.getLocation().x + 90, bu.getLocation().y);  
		            bd.setLocation(bu.getLocation().x, bu.getLocation().y + 100);  
		            bl.setLocation(bu.getLocation().x - 100, bu.getLocation().y);  
		            bc.setLocation(bu.getLocation().x, bu.getLocation().y); 
	                
	                contentPane.add(bupp);
	                contentPane.add(br);
	                contentPane.add(bd);
	                contentPane.add(bl);
	                contentPane.add(bc);
	                contentPane.add(bupps);
	                contentPane.add(brs);
	                contentPane.add(bds);
	                contentPane.add(bls);
	                
	                SwingUtilities.invokeLater(() -> {
	                    contentPane.repaint();
	                });

	                Thread.sleep(1000);
	                
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
	                
	                checkLocation();
	                bx = bu.getLocation().x;
	                by = bu.getLocation().y;

	                SwingUtilities.invokeLater(() -> {
	                    contentPane.repaint();
	                });

	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	        }
	    };

	    new Thread(runnable).start();     
   }
 public void MoreBomb() {//물줄기 증가 시 첫번째 클라이언트 캐릭터 폭탄
	   
	   this.x = myX; 
	    this.y = myY; 
	   
	     ImageIcon bubble = new ImageIcon("images/bomb.png");
	     JLabel bu = new JLabel(bubble);

       x /= 40;
       y /= 40;
       x *= 40;
       y *= 40;

       bu.setSize(40, 40);
       bu.setLocation(x + 16, y + 45); 
       contentPane.add(bu);
       bu.setVisible(true);

       bx = x + 16;
       by = y + 45;
	    Runnable runnable = new Runnable() {
	        @Override
	        public void run() {
	            try { 
	                Thread.sleep(2000);  
	                bu.setVisible(false);
	           
	                ImageIcon bup = new ImageIcon("images/bup.png");         
	                ImageIcon bright = new ImageIcon("images/bright.png");
	                ImageIcon bdown = new ImageIcon("images/bdown.png");
	                ImageIcon bleft = new ImageIcon("images/bleft.png");
	                ImageIcon bcenter = new ImageIcon("images/bcenter.png");
	                
	                Image bupImg = bup.getImage().getScaledInstance(45, 60, Image.SCALE_SMOOTH);
	                Image bdownImg = bdown.getImage().getScaledInstance(45, 60, Image.SCALE_SMOOTH);
	                
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
	                
	                contentPane.add(bupp);
	                contentPane.add(br);
	                contentPane.add(bd);
	                contentPane.add(bl);
	                contentPane.add(bc);
	                contentPane.add(bupps);
	                contentPane.add(brs);
	                contentPane.add(bds);
	                contentPane.add(bls);
	                
	                SwingUtilities.invokeLater(() -> {
	                    contentPane.repaint();
	                });

	                Thread.sleep(1000);
	                
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
	                
	                checkLocation();
	                bx = bu.getLocation().x;
	                by = bu.getLocation().y;

	                SwingUtilities.invokeLater(() -> {
	                    contentPane.repaint();
	                });

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
   public void MyLocations() {//keyR .. keyD 는 2번째 클라이언트 캐릭터가 특정 위치에 도달했을때 그방향으로 이동할수없도록 제어
	   for (int i = 0; i < item.size(); i++) {
	   if ((enemyX >= item.get(i).getX() - 40 && enemyX <= item.get(i).getX())
               && (enemyY < item.get(i).getY()+5 && enemyY > item.get(i).getY() - 35)) {
            keyR = false;
            continue;

         } else if ((enemyX >= item.get(i).getX() && enemyX <= item.get(i).getX() + 40)
               && (enemyY < item.get(i).getY()+5 && enemyY > item.get(i).getY() - 35)) {
            keyL = false;
            continue;
         } else if ((enemyX > item.get(i).getX()-10 && enemyX < item.get(i).getX() + 30)
               && (enemyY > item.get(i).getY() && enemyY < item.get(i).getY() + 40)) {
            keyU = false;
            continue;
         } else if ((enemyX >= item.get(i).getX()-10 && enemyX <= item.get(i).getX() + 30)
               && (enemyY+46 > item.get(i).getY()-10&& enemyY+46 < item.get(i).getY()+40)) {
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
            MyLocations();
         }
      }
   };

   public void checkLocation() {//아이템과 폭발 충동처리,케릭터 폭탄 위치 확인
	   if(!bombReady) {//물줄기가 늘어나지 않은 상태의 물풍선과 캐릭터 폭발 충돌처리
	      if((myX>bx-50 && myX<bx+45) &&(myY>by-35 &&myY<by+15)) {
	         die = true;
	      }
	      else if((myX>bx-5 && myX<bx+35) &&(myY>by-65 &&myY<by+35)) {
	         die = true;
	      }
	      if((enemyX>bx-50 && enemyX<bx+45) &&(enemyY>by-35 &&enemyY<by+15)) {//2번째 클라이언트 캐릭터 폭발 충동처리
		         dies = true;
		      }
		      else if((enemyX>bx-5 && enemyX<bx+35) &&(enemyY>by-65 &&enemyY<by+35)) {
		         dies = true;
		      }
	   }else {
		   if((myX>bx-100 && myX<bx+100) &&(myY>by-35 &&myY<by+15)) {//물약 아이템 호출시 캐릭터와 폭발 충동처리
		         die = true;
		      }
		      else if((myX>bx-5 && myX<bx+35) &&(myY>by-100 &&myY<by+100)) {
		         die = true;
		      }
		   if((enemyX>bx-100 && enemyX<bx+100) &&(enemyY>by-35 &&enemyY<by+15)) {//물약 아이템 호출시 2번째 클라이언트 폭발 충동처리
		         dies = true;
		      }
		      else if((enemyX>bx-5 && enemyX<bx+35) &&(enemyY>by-100 &&enemyY<by+100)) {
		         dies = true;
		      }
	   }
	   if(!bombReady) {//물줄기 증가하지 않았을시에 이벤트
       for (int i = 0; i < item.size(); i++) {
         y -= 40;
         if ((bx + 40 >= item.get(i).getX() && bx + 40 <= item.get(i).getX() + 16)
               && (by >= item.get(i).getY() && by <= item.get(i).getY() + 5)) {// 물줄기 특정 위치 충돌시 이벤트 처리
            item.get(i).setIcon(null);
            item3 = item2[0];
            if(item3==null) {//아이템 이미지 경로 list에 추가
	        list.add("a");
            }else {
            list.add(item3.toString());
            }     
            itemLabel = new JLabel(item3);
            itemLabel.setLocation(item.get(i).getX(), item.get(i).getY());
            itemLabel.setSize(40, 40);
            contentPane.add(itemLabel);
            itemlist.add(itemLabel);
            item.remove(i);// 벽 제거

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

         }
      }
	   }else {
		   for (int i = 0; i < item.size(); i++) {//물줄기 길이 증가시 이벤트 처리
		         y -= 40;
		         if ((bx+80 >= item.get(i).getX() && bx - 80 <= item.get(i).getX() + 16)
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
		               && (by + 80 >= item.get(i).getY() && by-80 <= item.get(i).getY() + 5)) {
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
		         } 
      }
	   }
      
   }
   public void enemyCheckLocation(int enemyBx,int enemyBy) {//상대 클라이언트 물줄기 벽 충돌 이벤트 처리
	   if(!bombReady) {
	      for (int i = 0; i < item.size(); i++) {
	         y -= 40;
	         if ((enemyBx + 40 >= item.get(i).getX() && enemyBx + 40 <= item.get(i).getX() + 16)
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
	               && (enemyBy + 40 >= item.get(i).getY() && enemyBy + 40 <= item.get(i).getY() + 5)) {
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
	         } else if ((enemyBx - 40 >= item.get(i).getX() && enemyBx - 40 <= item.get(i).getX() + 16)
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
	         } else if ((enemyBx >= item.get(i).getX() && enemyBx<= item.get(i).getX() + 16)
	               && (enemyBy - 40 >= item.get(i).getY() && enemyBy - 40 <= item.get(i).getY() + 5)) {
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
	         }
	      }
	   }else {
		   for (int i = 0; i < item.size(); i++) {
		         y -= 40;
		         if ((bx+80 >= item.get(i).getX() && bx - 80 <= item.get(i).getX() + 16)
		               && (by >= item.get(i).getY() && by <= item.get(i).getY() + 5)) {
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
		         } else if ((bx >= item.get(i).getX() && bx <= item.get(i).getX() + 16)
		               && (by + 80 >= item.get(i).getY() && by-80 <= item.get(i).getY() + 5)) {
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
		   }
	   }      
	   }

   Runnable enemyBomb = new Runnable() {
	
	@Override
	public void run() {
		try {
			Thread.sleep(2000);
			enemyCheckLocation(bx,by);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	
	}
   };
   Runnable myBomb = new Runnable() {
		
		@Override
		public void run() {
			try {
				Thread.sleep(2000);
				checkLocation();
				
			} catch (Exception e) {
				// TODO: handle exception
			}
			
		
		}
	   };
   
   
  
   
	   public void Item() {//itemlist의 i번째 아이템과 충돌시 아이콘 제거하고, 경로에 맞는 아이템 호출
		      for (int i = 0; i < itemlist.size(); i++) {
		         if ((myX >= itemlist.get(i).getX() -20 && myX <= itemlist.get(i).getX() + 10)
		               && (myY >= itemlist.get(i).getY() - 10 && myY <= itemlist.get(i).getY() + 30)) {//첫번째 클라이언트 캐릭터 이벤트 호출
		        	itemlist.get(i).setIcon(null);
		            if(list.get(i).toString().equals(item2[0].toString())) { //list에 추가했던 경로 일치하는지 확인 
		            	if(apple==1) {//첫 클라이언트 이속증가
		                speed -= 5;
		                if (speed < 40) {
		                   speed = 40;
		                }
		            	}
		            }
		            if(list.get(i).toString().equals(item2[2].toString())) {
		            	
		            	if(apple==1) {//첫 클라이언트 물줄기 길이 증가후 서버로 MORE 전달
		            	bombReady = true;
		            	gt.send(username + ":MORE:o");
		            	}
		            }
		            if(list.get(i).toString().equals(item2[1].toString())) {  	
		            	if(apple==1) {//첫 클라이언트 물풍선 개수 증가
		            	more=true;
		            	}
		            }
		            
		         }else if ((enemyX >= itemlist.get(i).getX() -20 && enemyX <= itemlist.get(i).getX() + 10)
		                 && (enemyY >= itemlist.get(i).getY() - 10 && enemyY <= itemlist.get(i).getY() + 30)) {//두번째 클라이언트 아이템 흭득 이벤트 호출
		         	
		          	itemlist.get(i).setIcon(null);	 
		          	if(list.get(i).toString().equals(item2[0].toString())) {
		          		if(grape==1 && apple==0) {//두번째 클라이언트 이속 증가
		                speed -= 5;
		                if (speed < 40) {
		                   speed = 40;
		                }
		          		}
		            }
		            if(list.get(i).toString().equals(item2[2].toString())) {
		            	
		            	if(grape==1) {//두번째 클라이언트 물줄기 길이 증가후 서버로 MORES 전달
		            		bombReady = true;
		            		gt.send(username + ":MORES:o");	
		            	}
		            }
		            if(list.get(i).toString().equals(item2[1].toString())) {  
		            	if(grape==1 && apple==0) {//두번째 클라이언트 물풍선 개수 증가
		            	more=true;
		            	}
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

   public void keyProcess() {//키보드 눌림에 따라 움직임 인식
	  
      if (keyU == true) {
         bazziCurrent("images/bazzi_back.png");
         myY -= 10;
         sendY+=10;
         bazzi.setLocation(myX, myY);
         move = "U";
         gt.send(username + ":" + "MOVE:" + move);// 움직임 방향과 MOVE 메시지 서버로 전달
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
   public void keyProcesses() {//두번째 클라이언트 키보드 눌림에 따라 움직임 인식
	   
	      if (keyU == true) {
	         woonieCurrent("images/woonie_back.png");
	         enemyY -= 10;
	         sendY+=10;
	         woonie.setLocation(enemyX, enemyY);
	         move = "U";
	         gt.send(username + ":" + "MOVES:" + move);// 움직임 방향과 MOVES 메시지 서버로 전달
	         if (myY < 0) {
	            myY = 0;
	         }
	      }
	      if (keyD == true) {
	         woonieCurrent("images/woonie_front.png");
	         enemyY += 10;
	         sendY-=10;
	         woonie.setLocation(enemyX, enemyY);
	         move = "D";
	         gt.send(username + ":" + "MOVES:" + move);
	         if (myY > 550) {
	            myY = 550;
	         }
	      }
	      if (keyL == true) {
	         woonieCurrent("images/woonie_left.png");
	         enemyX -= 10;
	         sendX+=10;
	         woonie.setLocation(enemyX, enemyY);
	         move = "L";
	         gt.send(username + ":" + "MOVES:" + move);
	         if (myX < 16) {
	            myX = 16;
	         }
	      }
	      if (keyR == true) {
	         woonieCurrent("images/woonie_right.png");
	         enemyX += 10;
	         sendX-=10;
	         woonie.setLocation(enemyX, enemyY);
	         move = "R";
	         gt.send(username + ":" + "MOVES:" + move);
	         if (myX > 580) {
	            myX = 580;
	         }
	      }
	   
   }
  
   
   Runnable runnable = new Runnable() {
	  
      @Override
      public void run() {
    	
    	  while (true) {
    		  if(apple==1) {// 첫번째 클라이언트 움직임 인식
    			  SwingUtilities.invokeLater(() -> keyProcess());     	
            }else if(grape==1){//두번째 클라이언트 움직임 인식  	
            	SwingUtilities.invokeLater(() -> keyProcesses());
            	
            }
            Item();
            
            repaint();
            try {
               Thread.sleep(speed);
            } catch (InterruptedException e) {
               e.printStackTrace();
            }
         }
      }
   };
  
   
   public PirateMap(String username) {

	   this.username = username;
       setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       setBounds(100, 100, 650, 650);
       setLocationRelativeTo(null);
       contentPane = new JLabel(new ImageIcon("Images/mapbg2.png"));//배경 이미지
       contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
       contentPane.setLayout(null);
       setContentPane(contentPane);

       firstLocation();

       gt = new GameThread();
       new Thread(runnable).start();
       new Thread(runable).start();
       new Thread(gameover).start();
     
      
       gt.start();
    

       gt.send(username + ":" + "LOCATIONX:" + sendX);// 캐릭터 첫 주소 위치 전달
       gt.send(username + ":" + "LOCATIONY:" + sendY);
       
       
       addKeyListener(new KeyAdapter() {
           @Override
           public void keyPressed(KeyEvent e) {//키보드 눌림시 이벤트 호출
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
                        	   if(apple==1) {
                        	   DropBomb();
                        	  
                        	   gt.send(username + ":DROP:o");//상대 클라이언트에 내 캐릭터 폭탄 위치전송
                        	   }else if(grape==1) {
                          		   DropBomb(enemyX,enemyY);//상대편 폭탄 위치
                        		 
                        		   gt.send(username + ":DROPS:o");
                        	   }
                           }
                           else {
                        	   if(apple==1) {//물약 아이템을 흭득한 클라이언트만 이벤트 호출되게 나눔
                        		   
                        		   MoreBomb();// 물줄기 증가 이벤트 후 호출
                        		
                        		   gt.send(username + ":DROP:o");
                            	   }else if(grape==1) {
                            		   MoreBomb(enemyX,enemyY);
                            		 
                            		   gt.send(username + ":DROPS:o");
                            	   }   
                    	   }
                           
                           try {
                        	   if(grape==1) {//물풍선 아이템을 흭득한 클라이언트만 호출할수있게 나눔
                        	   if(more) {//물풍선 대기시간 없음
                        		   Thread.sleep(0);
                        	   }else {
                        		   Thread.sleep(3000);
                        	   }
                        	   }else if(apple==1){
                        		   if(more) {
                            		   Thread.sleep(0);
                            	   }
                        	   }else {
                        		   Thread.sleep(3000);
                        	   }
                               // 3초 동안 대기
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

               // 중앙에 십자가 모양 그리기
               if (i == cookie.size / 2 || j == cookie.size / 2) {
                   // 십자가가 가로로 중앙(한 줄), 세로로 중앙(한 줄)에 그려짐
                   JLabel crossCookie = new JLabel(new ImageIcon("images/box3.png"));
                   item.add(crossCookie);
                   this.add(crossCookie);
                   crossCookie.setBounds(i * 40 + 15, j * 40, 45, 45);
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
      private BufferedWriter writer;

      public GameThread() {
         
         try {
            socket = new Socket("localhost",6000);//서버 접속 
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())); 
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

         } catch (Exception e) {
            e.printStackTrace();
         }

      }

      @Override
      public void run() {//각각의 캐릭터의 위치를 서버로부터 읽어들임
         try {
            String line;
            String firstline, secondline, thirdline;
            int zeroline;
            boolean myBombReady = true;
            boolean enemyBombReady = true; 
            while ((line = reader.readLine()) != null) {
            	String[] firstindex = line.split(":");
               zeroline=Integer.parseInt(firstindex[0]);
               firstline = firstindex[1];
               secondline = firstindex[2];
               thirdline = firstindex[3];
               if (secondline.equals("LOCATIONX")) {//적 위치 서버로부터 받아옴
                   enemyX = Integer.parseInt(thirdline);
               }
                if (secondline.equals("LOCATIONY")) {
                   enemyY = Integer.parseInt(thirdline);
                }
               if(zeroline==1) {//서버로부터 몇번째 클라이언트인지 받아옴
            	   apple=1;//서버로부터 받아온 숫자는 지역변수이므로 전역변수인 apple과 grape에 나눠서 저장
               }
               if(zeroline==2) {
            	   grape=1;
               }
               if (secondline.equals("MOVE")) {//첫번째 클라이언트 움직일때 서버로부터 MORE 메시지 흭득후 움직임 인식
            	   if(!firstline.equals(username))
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
                      bazziCurrent("images/bazzi_back.png");
                      bazzi.setLocation(myX, myY);
                      if (myY < 0) {
                         myY = 0;
                      }
                   } else if (thirdline.equals("D")) {
                      myY += 10;
                      bazziCurrent("images/bazzi_front.png");
                      bazzi.setLocation(myX, myY);
                      if (myY > 550) {
                         myY = 550;
                      }
                   }
            	   
            	   
               }
                   else if(secondline.equals("MOVES")) {//두번째 클라이언트 움직일때 서버로부터 MORE 메시지 흭득후 움직임 인식
                	   if(!firstline.equals(username))	
                                 if (thirdline.equals("L")) {
                                    enemyX -= 10;
                                    woonieCurrent("images/woonie_left.png");
                                    woonie.setLocation(enemyX, enemyY);
                                    if (enemyX > 580) {
                                         enemyX = 580;
                                    }
                                 } else if (thirdline.equals("R")) {
                                    enemyX += 10;
                                    woonieCurrent("images/woonie_right.png");
                                    woonie.setLocation(enemyX, enemyY);
                                    if (enemyX < 16) {
                                       enemyX = 16;
                                    }
                                 } else if (thirdline.equals("U")) {
                                    enemyY -= 10;
                                    woonieCurrent("images/woonie_back.png");
                                    woonie.setLocation(enemyX, enemyY);
                                    if (enemyY < 0) {
                                       enemyY = 0;
                                    }
                                 } else if (thirdline.equals("D")) {
                                    enemyY += 10;
                                    woonieCurrent("images/woonie_front.png");
                                    woonie.setLocation(enemyX, enemyY);
                                    if (enemyY > 550) {
                                       enemyY = 550;
                                    }
                                 

                                 }
                	   
                	   }
               if (secondline.equals("MORE")) {//서버로부터 물줄기 길이에 관한 정보를 받아옴
             	  bombReadys = true;  
               }
               if (secondline.equals("MORES")) {
              	  bombReadyss = true;  
                }
               if (secondline.equals("DROP")) {//첫번째 클라이언트에서 전송해주는 캐릭터의 물풍선의 위치 반환
            	   if(!bombReadys) {
            	   SwingUtilities.invokeLater(() -> DropBomb());
                   new Thread(myBomb).start();
            	   }else {
            		   SwingUtilities.invokeLater(() -> MoreBomb());
                       new Thread(myBomb).start();   
            	   }
               }else if(secondline.equals("DROPS")) {//두번째 클라이언트에서 전송해주는 캐릭터의 물풍선의 위치 반환
            	   if(!bombReadyss) {
            	   SwingUtilities.invokeLater(() -> DropBomb(enemyX,enemyY));
                   new Thread(enemyBomb).start();
            	   }else {
            		   SwingUtilities.invokeLater(() -> MoreBomb(enemyX,enemyY));
                       new Thread(enemyBomb).start();   
            	   }
            	  }
               repaint();
            }
         
         }catch (IOException e) {
            e.printStackTrace();
         }
      }

     
      public void send(String msg) {
    	    try {
    	        writer.write(msg);
    	        writer.newLine();  
    	        writer.flush();   
    	    } catch (IOException e) {
    	        e.printStackTrace();
    	    }
    	}
   }
}