package net;

import org.json.JSONObject;

import application.GameInstance;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class GameServer extends Thread{

	private DatagramSocket socket;
	
	public GameServer() {
		try {
			this.socket = new DatagramSocket(1331);
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		System.out.println(GameInstance.getIsServer());
	    while (true) {
	        byte[] data = new byte[1024];
	        DatagramPacket packet = new DatagramPacket(data, data.length);
	        try {
	            socket.receive(packet);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }

	        String receivedData = new String(packet.getData(), 0, packet.getLength());

	        try {
	        	
	            JSONObject json = new JSONObject(receivedData);
	            String message = json.getString("message");
	            int extraData = json.getInt("extraData");

//	            System.out.println("CLIENT [" + packet.getAddress().getHostAddress() + ":" + packet.getPort() + "] " + message);
//	            System.out.println("Extra Data: " + extraData);

	            if (message.trim().equalsIgnoreCase("ping")) {
//	                System.out.println("Returning pong");
	                sendData("pong", 42, packet.getAddress(), packet.getPort());
	            }
	            
	            if (message.trim().equalsIgnoreCase("join")) {
	            	GameInstance.setSecondePlayerConnected(true);
	            }
	        } catch (Exception e) {
	            System.out.println("Erreur de parsing JSON : " + e.getMessage());
	        }
	    }
	}
	
	public void sendData(String message, int extraData, InetAddress ipAddress, int port) {

	    JSONObject json = new JSONObject();
	    json.put("message", message);
	    json.put("extraData", extraData);

	    byte[] data = json.toString().getBytes();

	    DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, 1331);
	    try {
	        socket.send(packet);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	public void stopServer() {
	    if (socket != null && !socket.isClosed()) {
	        socket.close();
	    }
	}

}
