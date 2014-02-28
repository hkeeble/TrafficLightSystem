import java.awt.Color;

import javax.swing.JTextField;


/**
 * @author Henri
 * Represents the group of text fields within a crossroad.
 */
public class CrossroadTextFields {
	JTextField north, south, east, west, center;
	int columnWidth = 2;
	
	public CrossroadTextFields() {
		east = new JTextField();
		west = new JTextField();
		south = new JTextField();
		north = new JTextField();
		center = new JTextField();
		
		// Set columns for width
		east.setColumns(columnWidth);
		west.setColumns(columnWidth);
		south.setColumns(columnWidth);
		north.setColumns(columnWidth);
		center.setColumns(columnWidth);
		
		// Ensure all fields are uneditable
		east.setEditable(false);
		west.setEditable(false);
		north.setEditable(false);
		south.setEditable(false);
		center.setEditable(false);
		
		// Set Colors
		east.setBackground(Color.BLACK);
		west.setBackground(Color.BLACK);
		south.setBackground(Color.BLACK);
		north.setBackground(Color.BLACK);
		center.setBackground(Color.BLACK);
		
		east.setForeground(Color.WHITE);
		west.setForeground(Color.WHITE);
		south.setForeground(Color.WHITE);
		north.setForeground(Color.WHITE);
		center.setForeground(Color.WHITE);
	}
	
	/**
	 * Sets the text in the given direction field.
	 * @param text The text to add.
	 * @param direction The direction to add the text in.
	 */
	public void setText(String text, Dir direction) {
		switch(direction) {
		case NORTH:
			north.setText(text);
			break;
		case SOUTH:
			south.setText(text);
			break;
		case EAST:
			east.setText(text);
			break;
		case WEST:
			west.setText(text);
			break;
		case CENTER:
			center.setText(text);
			break;
		}
	}
	
	public JTextField getTextField(Dir direction) {
		switch(direction) {
		case NORTH:
			return north;
		case SOUTH:
			return south;
		case EAST:
			return east;
		case WEST:
			return west;
		case CENTER:
			return center;
		default:
			System.out.println("Attempted to access unrecognized directional text field. Returning center.");
			return center;
		}
	}
}
