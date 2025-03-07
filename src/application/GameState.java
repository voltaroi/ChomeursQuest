package application;

import net.GameClient;
import net.GameServer;

public class GameState {
	
	private static boolean isMulti = false;
	private static boolean isServer = false;
	
	private static GameClient socketClient;
	private static GameServer socketServer;
	
	private static boolean secondePlayerConnected = false;
	
	private static boolean round = true;
	
	public static void setRound(boolean newRound) {
		round = newRound;
	}
	
	public static boolean getRound() {
		return round;
	}
	
	public static void setIsMulti(boolean newIsMulti) {
		isMulti = newIsMulti;
	}
	
	public static boolean getIsMulti() {
		return isMulti;
	}
	
	public static void setIsServer(boolean newIsServer) {
		isServer = newIsServer;
	}
	
	public static boolean getIsServer() {
		return isServer;
	}
	
	public static void setSecondePlayerConnected(boolean newSecondePlayerConnected) {
		secondePlayerConnected = newSecondePlayerConnected;
	}
	
	public static boolean getSecondePlayerConnected() {
		return secondePlayerConnected;
	}

	public static void startServer() {
		socketServer = new GameServer();
	}
	
	public static GameServer getServer() {
		return socketServer;
	}
	
	public static void startClient() {
		socketClient = new GameClient("localhost");
	}
	
	public static GameClient getClient() {
		return socketClient;
	}
}
