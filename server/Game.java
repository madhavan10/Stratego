package server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import javax.swing.Timer;

public class Game {
	Player player1, player2;
	Player currentPlayer;
	boolean firstPlayerJoined;
	boolean isSetupTime;
	
	final int SETUP_TIME_IN_MINUTES;
	
	public Game(int setupTime) {
		firstPlayerJoined = false;
		SETUP_TIME_IN_MINUTES = setupTime;
		isSetupTime = true;
	}
	
	public synchronized void move(int x1, int y1, int x2, int y2, Player player) {
		if(isSetupTime)
			return;
		if(player != currentPlayer) {
			throw new IllegalStateException("MESSAGE Not your turn");
		}
		if(player.opponent == null) {
			throw new IllegalStateException("MESSAGE You don't have an opponent yet");
		}
		currentPlayer = currentPlayer.opponent;
	}
	
	public class Player implements Runnable, ActionListener {
		
		boolean team;
		Socket socket;
		Scanner input;
		PrintWriter output;
		Player opponent;
		
		int calls = 0;
		
		public Player(Socket socket) {
			this.socket = socket;
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

		private void processCommands() {
			//System.out.println("Process commands");
			while(input.hasNextLine()) {
				//System.out.println("Process commands loop head");
				String command = input.nextLine();
				if(command.startsWith("MOVE")) {
					System.out.println(command);
					String command2 = command.substring(5, 9);
					int x1 = Integer.parseInt(command2.substring(0, 1));
					int y1 = Integer.parseInt(command2.substring(1, 2));
					int x2 = Integer.parseInt(command2.substring(2, 3));
					int y2 = Integer.parseInt(command2.substring(3, 4));
					try {
						move(x1, y1, x2, y2, this);
						output.println("MOVE_OK");
						opponent.output.println("OPPONENT_MOVED " + command2);
					} catch(IllegalStateException e) {
						output.println(e.getMessage());
					}
				} else if(command.equals("FLAG")) {
					output.println("VICTORY");
					opponent.output.println("DEFEAT");
				}
			}
		}

		private void setup() throws Exception {
			input = new Scanner(socket.getInputStream());
			output = new PrintWriter(socket.getOutputStream(), true);
			
			if(!firstPlayerJoined) {
				//System.out.println("Choose team");
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
				Timer timer = new Timer(SETUP_TIME_IN_MINUTES * 1000 * 60, this);
				timer.setRepeats(false);
				timer.start();
			}
			if(team == ORC) currentPlayer = this;
			else currentPlayer = opponent;
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
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			isSetupTime = false;
			player1.output.println("SETUP_TIME_OVER");
			player2.output.println("SETUP_TIME_OVER");
		}
				
		static final boolean ORC = false;
		static final boolean HUMAN = true;
	}
}