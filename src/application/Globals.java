package application;

public class Globals {
	
	private static boolean isMulti = false;
	private static boolean isServer = false;
	
	private static boolean secondePlayerConnected = false;
	
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
}
