import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import javax.swing.SwingWorker;


public class SimulationStep extends SwingWorker<int[], Void> {

	Grid grid; // The worker's reference to the simulation grid
	
	public SimulationStep(Grid grid) {
		this.grid = grid;
	}
	
	@Override
	protected int[] doInBackground() throws Exception {

		// This is a single step of the simulation!
		System.out.println("Simulation step called.");
		
		grid.Get(0, 0).cars++;
		
		// Ping the server (could be used to send/receieve data on each step?)
		if(Server.isConnected())
			Server.SendData("HELO");
		
		return null;
	}
}
