package stratego;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.SwingConstants;


@SuppressWarnings("serial")
public class Stratego extends JFrame {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		if(args.length != 1) {
			System.err.println("Pass the IP of the Server as the sole command-line argument");
			return;
		}
		final String serverIP = args[0];
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Stratego frame = new Stratego(serverIP);
					frame.setVisible(true);
					frame.play();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	protected void play() {
		while(in.hasNextLine() ) {
			String response = in.nextLine();
			if(response.equals("CHOOSE_TEAM")) {
				messageLabel.setText("Choose Team:");
				JButton orc = new JButton("ORC");
				orc.addMouseListener(new MouseAdapter() {
					public void mouseClicked() {
						out.println("PICK_TEAM orc");
					}
				});
				JButton human = new JButton("HUMAN");
				human.addMouseListener(new MouseAdapter() {
					public void mouseClicked() {
						out.println("PICK_TEAM human");
					}
				});
				messageLabel.add(orc);
				messageLabel.add(human);
			}
		}
		
	}

	private Socket socket;
	private Scanner in;
	private PrintWriter out;
	
	private JLabel messageLabel;
	private Board board;
	
	/**
	 * Create the frame.
	 * @param serverIP 
	 */
	public Stratego(String serverIP) throws IOException {
		socket = new Socket(serverIP, 58901);
		in = new Scanner(socket.getInputStream());
		out = new PrintWriter(socket.getOutputStream(), true);
		initUI();
	}
	
	private void initUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Stratego");
		setBounds(100, 100, 768, 768);
		
		board = new Board();
		messageLabel = new JLabel("...");
		messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		messageLabel.setFont(new Font("Arial", Font.PLAIN, 16));
		
		Container contentPane = getContentPane();
		contentPane.add(messageLabel, BorderLayout.SOUTH);
		contentPane.add(board, BorderLayout.CENTER);	
	}

}
