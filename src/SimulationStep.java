import java.util.TimerTask;

/**
 * Represents a single simulation step.
 * @author Henri
 */
public class SimulationStep extends TimerTask {

	Grid grid;
	Server server;
	boolean serverConnected;
	
	/*
	 * Constructor is used to obtain a reference to the grid, and the server
	 */
	public SimulationStep(Grid currentGrid, Server currentServer) {
		grid = currentGrid;
		server = currentServer;
	}
	
	/**
	 * Run will update both the server and simulation.
	 */
	@Override
	public void run() {
		updateSimulation();
		if(server.isConnected())
			updateServer();
	}

	/**
	 * Update simulation.
	 */
	private void updateSimulation() {
		System.out.println("Simulation step called.");
		grid.Get(0, 0).cars++;
	}
	
	/**
	 * Update the server.
	 */
	private void updateServer() {
		System.out.println("Updating the server.");
		
		// PLACEHOLDER
		server.SendData("HELO");
		System.out.println(server.getData());
	}
	
}
