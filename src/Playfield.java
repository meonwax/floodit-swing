import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Playfield extends JPanel {

	private static final long serialVersionUID = 1L;

	private final Square[][] grid = new Square[ FloodIt.GRID_SIZE ][ FloodIt.GRID_SIZE ];

	int turnCount = 0;

	public void init() {

		// Populate the grid with random colored squares
		for( int row = 0; row < grid.length; row++ ) {

			for( int col = 0; col < FloodIt.GRID_SIZE; col++ ) {
				grid[ row ][ col ] = new Square( FloodIt.getRandomColor() );
			}
		}
	}

	/**
	 * Process one game turn
	 */
	public void process( Color newColor ) {

		Color referenceColor = grid[ 0 ][ 0 ].color;

		if( referenceColor != newColor ) {

			fill( 0, 0, referenceColor, newColor );

			turnCount++;
		}

		// Check if full grid is filled...
		boolean completed = true;
		TEST: for( int row = 0; row < grid.length; row++ ) {

			for( int col = 0; col < FloodIt.GRID_SIZE; col++ ) {

				if( grid[ row ][ col ].color != grid[ 0 ][ 0 ].color ) {

					completed = false;
					break TEST;
				}
			}
		}

		// ...and display a message if game was completed
		if( completed ) {

			repaint();

			JOptionPane.showMessageDialog( this, "Congratulations. You needed " + turnCount + " turns.", "Completed", JOptionPane.PLAIN_MESSAGE );

			// Restart the game
			init();
		}
	}

	/**
	 * Recursively fill all adjacent squares of the same color
	 */
	private void fill( int row, int col, Color referenceColor, Color newColor ) {

		if( grid[ row ][ col ].color == referenceColor ) {

			grid[ row ][ col ].color = newColor;

			if( row < FloodIt.GRID_SIZE - 1 ) {
				fill( row + 1, col, referenceColor, newColor );
			}
			if( col < FloodIt.GRID_SIZE - 1 ) {
				fill( row, col + 1, referenceColor, newColor );
			}
			if( row > 0 ) {
				fill( row - 1, col, referenceColor, newColor );
			}
			if( col > 0 ) {
				fill( row, col - 1, referenceColor, newColor );
			}
		}
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension( FloodIt.SQUARE_SIZE * FloodIt.GRID_SIZE, FloodIt.SQUARE_SIZE * FloodIt.GRID_SIZE );
	}

	@Override
	/**
	 * The main paint method called by the system for drawing the
	 * playfield grid and its squares
	 */
	protected void paintComponent( final Graphics g ) {

		super.paintComponent( g );

		// The coordinates of each square
		int x = 0;
		int y = 0;

		for( Square[] row : grid ) {

			for( int col = 0; col < FloodIt.GRID_SIZE; col++ ) {

				row[ col ].draw( (Graphics2D)g, x, y );

				x += FloodIt.SQUARE_SIZE;
			}

			x = 0;

			y += FloodIt.SQUARE_SIZE;
		}
	}
}
