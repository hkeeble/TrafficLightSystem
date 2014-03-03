import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;

/**
 * Represents the connection to the server during the runtime of the application. Contains static methods and variables, to keep the
 * socket open from program execution until closure.
 * @author Henri
 *
 */
public class Server {
	private static Socket socket; // The socket
	
	/**
	 * Attempts to connect to the server. Returns false if the connection was not made successfully.
	 * @return
	 */
	public static boolean Connect() {
		System.out.println("Attempting to contact server...");
		
		Socket socket = new Socket();

		try {
			socket.connect(new InetSocketAddress("192.168.0.196", 5000), 1000);
			if(socket.isConnected()) {
				return true;
			}
		}
		catch(IOException e) {
			System.out.print("Server connection failed: ");
			System.out.print(e.getMessage());
			System.out.println();
			return false;
		}
		catch(Exception e) {
			System.out.print("Server connection failed: ");
			System.out.print(e.getMessage());
			System.out.println();
			return false;
		}
		
		return false;
	}
	
	/**
	 * Close the connection to the server.
	 */
	public static void Disconnect() {
		if(isConnected()) {
			try {
				socket.close();
			}
			catch (IOException e) {
				System.out.println("Failed to disconneted from server: " + e.getMessage());
			}
		}
	}
	
	/**
	 * Sends the given string data to the server.
	 * @param data Data to send to the server.
	 */
	public static void SendData(String data) {
		try {
		DataOutputStream output = new DataOutputStream(socket.getOutputStream());
		output.writeBytes("HELO");
		}
		catch (IOException e) {
			System.out.println("Could not send data to server: " + e.getMessage());
		}
	}
	
	/**
	 * Returns whether or not the socket is currently connected to the server.
	 */
	public static boolean isConnected() {
		return socket.isConnected();
	}
	
	/**
	 * Retrieve string data from the server.
	 */
	public static String getData() {
		try {
		BufferedReader read = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		return read.readLine();
		}
		catch (IOException e) {
			System.out.println("Could not retrieve data from server: " + e.getMessage());
			return null;
		}
	}
}
