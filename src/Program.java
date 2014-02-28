import java.awt.EventQueue;
import java.lang.reflect.InvocationTargetException;

import javax.swing.SwingUtilities;


/**
 * The main program, handles both the Menu and Simulation JFrames
 * @author Henri
 */
public class Program {
	MainFrame frame;
	
	public Program() {
		frame = new MainFrame();
	}
	
	/**
	 * Runs the main program, calling the main frame show function. This is created on the EDT (Event Dispatch Thread).
	 */
	public void Run() {		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				frame.createAndShowGUI();
			}
		});
	}
}