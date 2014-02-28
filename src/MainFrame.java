import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Represents the program as a whole, derived from JFrame to implement a GUI and implements a KeyListener to get basic user input.
 * @author Henri
 */
public class MainFrame extends JFrame implements KeyListener {

	// Enumeration to represent program State
	private enum State {
		MENU,
		SIMULATION
	}
	
	// Panel identification strings
	final static String MENU_PANEL = State.MENU.toString();
	final static String SIMULATION_PANEL = State.SIMULATION.toString();
	
	// Menu GUI fields - need a reference to these to retrieve text
	JTextField widthField, heightField;
	
	// The current settings in the menu
	int widthSetting, heightSetting;
	
	// Simulation GUI - each panel represents a crossroad with 5 text fields, one for each direction.
	JPanel[][] panels;
	
	// Column width of text fields
	int columnWidth = 2;
	
	// Container panel to contain the two interfaces
	JPanel container;
	
	// Two JPanels - act as cards, i.e separate interfaces for the menu and the simulation itself.
	JPanel menuPanel;
	JPanel gridPanel;
	
	// The card layout
	CardLayout cl;
	
	// The simulation itself
	Simulation simulation;
	
	// Timers for GUI updates
	Timer timer;
	final int MS_BETWEEN_GUI_UPDATES = 1000;
	
	// Task the updates the GUI within the EDT
	TimerTask timerTask = new TimerTask() {
		@Override
		public void run() {
			EventQueue.invokeLater(new Runnable() {
				@Override
				public void run() {
					updateGUI();
				}
			});
		}
	};
	
	public MainFrame() {
		// Initialize the JFrame
		this.setVisible(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Traffic Light System");
		
		// Initialize simulation variables
		widthSetting = 0;
		heightSetting = 0;
		
		// Initialize Cards
		menuPanel = new JPanel();
		gridPanel = new JPanel();
		
		// Initialize layout and container panel
		container = new JPanel();
		cl = new CardLayout();
		container.setLayout(cl);
		
		// Add the cards to the container
		container.add(menuPanel, MENU_PANEL);
		container.add(gridPanel, SIMULATION_PANEL);
		
		// Ensure the menu shows first
		initGUI(State.MENU);
	
		this.getContentPane().add(container);
	}
	
	/**
	 * Creates and shows the GUI. Should be run on the EDT for thread safety.
	 */
	public void createAndShowGUI() {
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	/**
	 * Create a grid of panels based on the current grid.
	 * @return A panel containing a panel for each crossroad.
	 */
	private void createGrid() {
		
		// Get reference to simulation grid
		Grid grid = simulation.getGrid();
		
		// Initialize the panels
		panels = new JPanel[grid.getWidth()][grid.getHeight()];
		
		// Create a grid layout on a new panel
		GridLayout layout = new GridLayout(grid.getWidth(), grid.getHeight());
		gridPanel.setLayout(layout);
		
		// Loop through all panels and create text boxes
		for(int x = 0; x < grid.getWidth(); x++) {
			for(int y = 0; y < grid.getHeight(); y++) {
				
				// Create panel and layout
				panels[x][y] = new JPanel();
				GridLayout playout = new GridLayout(3, 3);
				panels[x][y].setLayout(playout);
				
				// Get a reference to the grid
				Grid gr = simulation.getGrid();
				
				panels[x][y].add(new JPanel());
				panels[x][y].add(gr.Get(x, y).getTextField(Dir.NORTH));
				panels[x][y].add(new JPanel());
				panels[x][y].add(gr.Get(x, y).getTextField(Dir.WEST));
				panels[x][y].add(gr.Get(x, y).getTextField(Dir.CENTER));
				panels[x][y].add(gr.Get(x, y).getTextField(Dir.EAST));
				panels[x][y].add(new JPanel());
				panels[x][y].add(gr.Get(x, y).getTextField(Dir.SOUTH));
				panels[x][y].add(new JPanel());
				
				// Set some initial text as an example
				gr.Get(x, y).getTextField(Dir.NORTH).setText("Cars: \nLight: ");
				gr.Get(x, y).getTextField(Dir.SOUTH).setText("Cars: \nLight: ");
				gr.Get(x, y).getTextField(Dir.EAST).setText("Cars: \nLight: ");
				gr.Get(x, y).getTextField(Dir.WEST).setText("Cars: \nLight: ");
				gr.Get(x, y).getTextField(Dir.CENTER).setText("Cars: \nLight: ");
				
				// Add the panel to the main panel
				gridPanel.add(panels[x][y]);
			}
		}
	}
	
	/**
	 * Creates the menu content and adds it to the cards.
	 */
	private void createMenu() {
		// Create Labels
		JLabel welcomeLabel = new JLabel("<html>Welcome to the Traffic Light System Simulator.<br>Enter height and width for the simulation below.<br><br><html>", JLabel.CENTER);
		JLabel heightLabel = new JLabel("Height:", JLabel.CENTER);
		JLabel widthLabel = new JLabel("Width:", JLabel.CENTER);
		
		// Create TextFields
		widthField = new JTextField(20);
		widthField.setColumns(2);
		widthField.setText(Integer.toString(widthSetting));
		heightField = new JTextField(20);
		heightField.setColumns(2);
		heightField.setText(Integer.toString(heightSetting));
		
		// Create Button and action listener
		JButton button = new JButton("Start Simulation");
		button.addActionListener(new MenuButtonListener());
		
		// Create Panels
		JPanel textPanel = new JPanel();
		JPanel fieldPanel = new JPanel();
		
		// Add Content to panels
		textPanel.add(welcomeLabel, BorderLayout.CENTER);
		fieldPanel.add(heightLabel, BorderLayout.LINE_START);
		fieldPanel.add(heightField, BorderLayout.CENTER);
		fieldPanel.add(widthLabel,  BorderLayout.LINE_START);
		fieldPanel.add(widthField,  BorderLayout.LINE_END);
		
		// Create menu layout
		menuPanel.setLayout(new BorderLayout());
		
		// Add panels to to the menu panel
		menuPanel.add(textPanel,  BorderLayout.NORTH);
		menuPanel.add(fieldPanel, BorderLayout.CENTER);
		menuPanel.add(button,	  BorderLayout.SOUTH);
	}
	
	/**
	 * Menu listener class for the menu button. Used to update the simulation grid during initialization.
	 */
	public class MenuButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// Retrieve the input from the menu
			widthSetting = Integer.parseInt(widthField.getText());
			heightSetting = Integer.parseInt(heightField.getText());
			
			initSimulation(); // Initialize the simulation.
			initGUI(State.SIMULATION); // Update the GUI ready for the simulation
			simulation.Start(); // Start the simulation
			startGUITimer(); // Start the GUI update timer
		}
	}
	
	/**
	 * Initializes the GUI based on the state passed in.
	 */
	private void initGUI(State state) {
		if(state == State.SIMULATION) {
			createGrid(); // Creates a new grid panel based upon the current grid.
			gridPanel.validate(); // Validate the new panel
			this.setLocationRelativeTo(null); // Set location
			cl.show(container, SIMULATION_PANEL);
		}
		else if(state == State.MENU) {
			createMenu();
			cl.show(container, MENU_PANEL);
		}
	}
	
	/**
	 * Initializes the simulation GUI's update timer.
	 */
	private void startGUITimer() {
		timer = new Timer();
		timer.scheduleAtFixedRate(timerTask, MS_BETWEEN_GUI_UPDATES, MS_BETWEEN_GUI_UPDATES);
	}
	
	/**
	 * Stops the GUI timer updating.
	 */
	private void stopGUITimer() {
		timer.cancel();
	}
	
	/**
	 * Updates the GUI based on the current grid. Called on a timestep, must be called on event dispatch thread for safety.
	 */
	private void updateGUI() {
		// Will likely loop through all grid text boxes and update the display accordingly.
		System.out.println("GUI Update called.");
	}
	
	/**
	 * Initializes the simulation. Does not handle any visualization.
	 */
	private void initSimulation() {
		simulation = new Simulation();
		simulation.Init(widthSetting, heightSetting);
	}
	
	// Key listener functions - the window can respond to specific key presses if neccesary
	
	@Override
	public void keyPressed(KeyEvent arg0) {
		if(arg0.getKeyChar() == KeyEvent.VK_ESCAPE)
			this.setVisible(false);
			this.dispose();
	}

	@Override
	public void keyReleased(KeyEvent arg0) {

	}

	@Override
	public void keyTyped(KeyEvent arg0) {

	}

}
