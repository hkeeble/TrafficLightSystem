import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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
	 // The socket
	private Socket socket;
	
	// Input/Output streams of the server
	private PrintWriter out;
	private BufferedReader in;
	
	/**
	 * Default constructor.
	 */
	public Server() {
		socket = null;
		out = null;
		in = null;
	}
	
	/**
	 * Construct and connect the server.
	 */
	public Server(String name, int port, int timeout) {
		Connect(name, port, timeout);
	}
	
	/**
	 * Can be used to connect a new server to the same IP and port as the given server.
	 */
	public Server(Server server, int timeout) {
		Connect(server.getIP(), server.getPort(), timeout);
	}
	
	/**
	 * Attempts to connect to the server.
	 * @param name The name of the server. This will usually be the IP address of the server in the form of a string. "localhost"
	 * 			   will connect to a server on the local machine.
	 * @param port The port to connect to.
	 * @param timout Number of milliseconds to wait before connection timeout.
	 * @return Whether or not the connection was successful.
	 */
	public boolean Connect(String name, int port, int timeout) {

		System.out.println("Attempting to contact server...");
		
		Socket socket = new Socket();

		try {
			socket.connect(new InetSocketAddress(name, port), timeout);
			if(socket.isConnected()) {
				System.out.println("Connection successful!");
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
		
		try {
			System.out.println("Retrieving server input and output streams...");
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		}
		catch(IOException e) {
			System.out.println("Failed retrieving input and output streams!");
			System.out.println("IOException: " + e.getMessage());
		}
		
		System.out.println("Obtained input/output streams successfully.");
		
		if(socket.isConnected() && out != null && in != null) {
			System.out.println("Server ready for use.");
			return true;
		}
		
		return false;
	}
	
	/**
	 * Close the connection to the server.
	 */
	public void Disconnect() {
		if(socket != null) {
			if(isConnected()) {
				try {
					socket.close();
				}
				catch (IOException e) {
					System.out.println("Failed to disconneted from server: " + e.getMessage());
				}
			}
		}
	}
	
	/**
	 * Sends the given string data to the server.
	 * @param data Data to send to the server.
	 */
	public void SendData(String data) {
		out.println(data);
	}
	
	/**
	 * Returns whether or not the socket is currently connected to the server.
	 */
	public boolean isConnected() {
		if(socket != null)
			return socket.isConnected();
		else
			return false;
	}
	
	/**
	 * Retrieve string data from the server.
	 */
	public String getData() {
		try {
			return in.readLine();
		} catch (IOException e) {
			System.out.println("Failed to retrieve data from server: " + e.getMessage());
			return null;
		}
	}
	
	/**
	 * Get the IP currently connected to.
	 */
	public String getIP() {
		return socket.getInetAddress().getAddress().toString();
	}
	
	/**
	 * Get the port currently connected to.
	 */
	public int getPort() {
		return socket.getPort();
	}
}
