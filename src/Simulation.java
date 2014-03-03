import java.util.Timer;
import java.util.TimerTask;


public class Simulation {
	
	// The main grid of crossroads.
	private Grid grid;
	
	// Swing worker - thread worker used to update the simulation in a background thread
	private SimulationStep simStepWorker;
	
	// The simulation's timers
	private Timer timer;
	private final int MS_BETWEEN_STEPS = 1000; // Number of milliseconds between each simulation step
	
	// The timer task, calls the simulation step worker thread to execute
	private TimerTask simStepTask = new TimerTask(){

		@Override
		public void run() {
			simStepWorker = new SimulationStep(grid);
			simStepWorker.execute();
		}
		
	};
	
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
		
		// Attemp server connection
		Server.Connect();
		
		// Introduce cars here? e.g. grid.Get(0, 0).addCars(new Car[])?
	}
	
	/**
	 * Starts the simulation timer
	 */
	public void Start() {
		// Initialize simulation timer
		timer = new Timer();
		timer.scheduleAtFixedRate(simStepTask, MS_BETWEEN_STEPS, MS_BETWEEN_STEPS);
	}
	
	/**
	 * Stops the simulation timer and disconnects from the server.
	 */
	public void End() {
		timer.cancel();
		Server.Disconnect();
	}
}
