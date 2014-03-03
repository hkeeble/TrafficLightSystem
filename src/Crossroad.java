import java.awt.Color;

import javax.swing.JTextField;

/**
 * Represents a crossroad. Might have lights, sensors, collection of cars etc.
 */
public class Crossroad {

	private CrossroadTextFields textFields;
	
	public int cars;
	
	public Crossroad() {
		textFields = new CrossroadTextFields();
		cars = 0;
	}
	
	/**
	 * Returns the text field in the given direction.
	 */
	public JTextField getTextField(Dir direction) {
		return textFields.getTextField(direction);
	}
}
