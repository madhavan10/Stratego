package server;

import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Client -> Server:
 * PICK_TEAM <orc or human>
 * MOVE <x1><y1><x2><y2>
 * 
 * Server -> Client:
 * CHOOSE_TEAM
 * SETUP <minutes>
 * YOUR_TEAM <team as boolean>
 * SETUP_TIME_OVER
 * MOVE_OK
 * OTHER_PLAYER_MOVED <x1><y1><x2><y2>
 * OTHER_PLAYER_LEFT
 * MESSAGE <text>
 */
public class StrategoServer {
	
	static final int PORT = 58901;
	static final int SETUP_TIME_IN_MINUTES = 1;
	
	public static void main(String[] args) throws Exception {
        try (ServerSocket listener = new ServerSocket(PORT)) {
            System.out.println("Stratego Server is Running...");
            ExecutorService pool = Executors.newFixedThreadPool(200);
            while (true) {
                Game game = new Game(SETUP_TIME_IN_MINUTES);
                pool.execute(game.new Player(listener.accept()));
                pool.execute(game.new Player(listener.accept()));
            }
        }
    }	
}