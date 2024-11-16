package frame;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import dao.MemberDao;

public class LoginFrame extends JFrame {
	
	
	private JLabel contentPane, lblNewLabel;
	private JTextField tfUsername;
	private JButton btnStart;
    private Font f1;
    private JLabel LabelName;
	public LoginFrame() {
		
		
		renderUI();

	
		eventInit();
	}
	
	public void renderUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 400, 300);

		setSize(1072, 751);
		setLocationRelativeTo(null);

		contentPane = new JLabel(new ImageIcon("images/login.png"));
		// contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		
		
		btnStart=new JButton(new ImageIcon("images/start.png"));
		btnStart.setBounds(430, 550, 182, 54);
		contentPane.add(btnStart);
        

	
		setVisible(true);
	}
	
	public void eventInit() {
		btnStart.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				String username = tfUsername.getText();
			
					new WaitingRoom(username);

			}
		});
	}
				


	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginFrame frame = new LoginFrame();

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
	
	

