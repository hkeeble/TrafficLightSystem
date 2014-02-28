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
		
		return null;
	}

}
