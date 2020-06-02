package server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.Instant;
import java.util.Date;
import java.util.Scanner;
import javax.swing.Timer;

public class Game implements ActionListener{
	Player player1, player2;
	boolean firstPlayerJoined;
	final int SETUP_TIME_IN_MINUTES;
	PrintWriter logger;
	Timer setupTimer;
		
	public Game(int setupTime) {
		firstPlayerJoined = false;
		SETUP_TIME_IN_MINUTES = setupTime;
		setupTimer = new Timer(SETUP_TIME_IN_MINUTES * 1000 * 60, this);
		setupTimer.setRepeats(false);
		
		String thisInstant = Date.from(Instant.now()).toString();
		thisInstant = thisInstant.replace(':', '_');
		thisInstant = thisInstant.replace(' ', '_');
		String replaysDirname = System.getProperty("user.dir") + File.separator + "replays";
		String logfilename = replaysDirname + File.separator + thisInstant + ".txt";
		File replaysDir = new File(replaysDirname);
		try {					
			if(!replaysDir.isDirectory())
				replaysDir.mkdir();
			logger = new PrintWriter(new FileOutputStream(logfilename), true);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		player1.output.println("SETUP_TIME_OVER");
		player2.output.println("SETUP_TIME_OVER");
		try {
			logger.println("SETUP_TIME_OVER");
		} catch(NullPointerException e) {
			e.printStackTrace();
		}
	}
	
	public class Player implements Runnable {
		
		boolean team;
		boolean ready;
		Socket socket;
		Scanner input;
		PrintWriter output;
		Player opponent;
		
		int calls = 0;
		
		public Player(Socket socket) {
			this.socket = socket;
			ready = false;
		}

		@Override
		public void run() {
			try {
				setup();
				processCommands();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if(opponent != null && opponent.output != null)
					opponent.output.println("OTHER_PLAYER_LEFT");
				try {
					System.out.println("Closing socket!");
					socket.close();
				} catch(IOException e) {}
			}
		}
		
		private void setup() throws Exception {
			input = new Scanner(socket.getInputStream());
			output = new PrintWriter(socket.getOutputStream(), true);
			
			if(!firstPlayerJoined) {
				firstPlayerJoined = true;
				output.println("CHOOSE_TEAM");
				team = getTeamSelectionFromClient();
				player1 = this;
				output.println("MESSAGE Waiting for other player to connect");
				output.println("YOUR_TEAM " + team);
			} else {
				try {
					calls++;
					team = !(player1.team);
				} catch (NullPointerException e) {
					output.println("MESSAGE Waiting for other player to choose team");
					Thread.sleep(5000);
					if(calls == 36) {
						output.println("MESSAGE Waited too long for other player - terminating connection to server");
						throw new Exception("Waited too long for other player to choose team");
					}
					setup();
					return;
				}
				player2 = this;
				opponent = player1;
				opponent.opponent = this;
				output.println("YOUR_TEAM " + team);
				output.println("SETUP " + SETUP_TIME_IN_MINUTES);
				opponent.output.println("SETUP " + SETUP_TIME_IN_MINUTES);
				setupTimer.start();
			}
		}
		
		private void processCommands() {
			while(input.hasNextLine()) {
				String command = input.nextLine();
				System.out.println(team + ":" + command);
				try {
					logger.println(team + ":" + command);
				} catch(NullPointerException e) {
					e.printStackTrace();
				}
				if(command.startsWith("MOVE")) {			
					output.println("MOVE_OK");
					opponent.output.println("OPPONENT_MOVED " + command.substring(5, 9));
				} else if(command.equals("READY")) {
					ready = true;
					if(ready && opponent.ready) {
						setupTimer.stop();
						player1.output.println("SETUP_TIME_OVER");
						player2.output.println("SETUP_TIME_OVER");
						try {
							logger.println("SETUP_TIME_OVER");
						} catch(NullPointerException e) {
							e.printStackTrace();
						}
					}
				} else if(command.startsWith("REPEAT_ATTACK")) {
					output.println("OK");
					opponent.output.println("OPPONENT_REPEAT_ATTACK " + command.substring(14, 18));
				} else if(command.equals("STOP_REPEAT_ATTACK")) {
					output.println("MOVE_OK");
					opponent.output.println("OPPONENT_STOP_REPEAT_ATTACK");
				} else if(command.startsWith("DWARVEN_AXE")) {					
					output.println("MOVE_OK");
					opponent.output.println("OPPONENT_DWARVEN_AXE " + command.substring(12));
				} else if(command.startsWith("RAMPAGE")) {				
					output.println("MOVE_OK");
					opponent.output.println("OPPONENT_RAMPAGE " + command.substring(8, 12));
				} else if(command.startsWith("FLIGHT")) {					
					output.println("MOVE_OK");
					opponent.output.println("OPPONENT_FLIGHT " + command.substring(7, 11));
				} else if(command.startsWith("SWIFT_STEED")) {					
					output.println("MOVE_OK");
					opponent.output.println("OPPONENT_SWIFT_STEED " + command.substring(12, 16));
				} else if(command.startsWith("DETECT")) {					
					output.println("MOVE_OK");
					opponent.output.println("OPPONENT_DETECT " + command.substring(7, 11));
				} else if(command.startsWith("LONGBOW")) {					
					output.println("MOVE_OK");
					opponent.output.println("OPPONENT_LONGBOW " + command.substring(8, 12));
				} else if(command.equals("FLAG")) {
					output.println("VICTORY");
					opponent.output.println("DEFEAT");
				}
			}
		}
		
		private boolean getTeamSelectionFromClient() throws Exception {
			while(input.hasNextLine()) {
				String command = input.nextLine();
				if(command.startsWith("PICK_TEAM")) {
					//System.out.println(command);
					return command.substring(10).equalsIgnoreCase("orc") ? ORC : HUMAN;
				} 
			}
			throw new Exception("Failed to get team selection");
		}
				
		static final boolean ORC = false;
		static final boolean HUMAN = true;
	}
}