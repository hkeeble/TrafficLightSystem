import java.util.Timer;
import java.util.TimerTask;

/**
 * Represents the simulation itself.
 * @author Henri
 */
public class Simulation {
	
	// The main grid of crossroads.
	private Grid grid;
	
	// Server constants
	final String SERVER_NAME = "localhost";
	final int SERVER_PORT = 5000;
	final int SERVER_TIMEOUT = 1000;
	
	// The server
	Server server;
	
	// The simulation's timers
	private Timer timer;
	private final int MS_BETWEEN_STEPS = 1000; // Number of milliseconds between each simulation step
	
	/* 
	 * The timer task for the simulation - a timer task is actually executed on it's own thread, so we don't need to have
	 * a swing worker thread.
	 */
	private SimulationStep simStep;
	
	public Grid getGrid() {
		return grid;
	}
	
	public Simulation() {
		grid = new Grid(0, 0);
	}
	
	/**
	 * Initializes the simulation with the given width and height.
	 */
	public void Init(int width, int height) {
		grid = new Grid(width, height);
		
		// Attempt server connection
		server = new Server();
		server.Connect(SERVER_NAME, SERVER_PORT, SERVER_TIMEOUT);
		
		// Initialize simulation step task thread
		simStep = new SimulationStep(grid, server);
		
		// Introduce cars here? e.g. grid.Get(0, 0).addCars(new Car[])?
	}
	
	/**
	 * Starts the simulation timer
	 */
	public void Start() {
		// Initialize simulation timer
		timer = new Timer();
		timer.scheduleAtFixedRate(simStep, MS_BETWEEN_STEPS, MS_BETWEEN_STEPS);
	}
	
	/**
	 * Stops the simulation timer and disconnects from the server.
	 */
	public void End() {
		timer.cancel();
		server.Disconnect();
	}
}
