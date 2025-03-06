package net;

import org.json.JSONObject;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class GameClient extends Thread{

	private InetAddress ipAddress;
	private DatagramSocket socket;
	
	public GameClient(String ipAddress) {
		try {
			this.socket = new DatagramSocket();
			this.ipAddress = InetAddress.getByName(ipAddress);
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		while (true) {
			byte[] data = new byte[1024];
			DatagramPacket packet = new DatagramPacket(data, data.length);
			try {
				socket.receive(packet);
			} catch (IOException e) {
				e.printStackTrace();
			}
			String message = new String(packet.getData());
			if(message.trim().equalsIgnoreCase("pong")) {
				System.out.println("coucou");
			}
			
			if(message.trim().equalsIgnoreCase("")) {
				
			}
		}
	}
	
	public void sendData(String message, int extraData) {

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
}
