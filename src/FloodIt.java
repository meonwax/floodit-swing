import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class FloodIt extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	/*
	 * Game config constants
	 */
	public final static int SQUARE_SIZE = 30;

	public final static int GRID_SIZE = 14;

	public final static Color[] COLORS = new Color[] { Color.BLUE, Color.RED, Color.GREEN, Color.YELLOW, Color.MAGENTA,	Color.MAGENTA.darker().darker() };

	/* ***************************** */

	private final Playfield playfield;
	private final JButton newGameButton;
	private final JButton[] colorButtons = new JButton[ COLORS.length ];

	public FloodIt() {

		super( "Flood It!" );
		setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

		Container content = getContentPane();
		content.setBackground( Color.WHITE );
		BorderLayout layout = new BorderLayout();
		layout.setVgap( 10 );
		content.setLayout( layout );

		// The playfield grid
		playfield = new Playfield();
		playfield.init();
		content.add( playfield, BorderLayout.CENTER );

		// The 'New game' button
		newGameButton = new JButton( "New game" );
		content.add( newGameButton, BorderLayout.PAGE_END );
		newGameButton.addActionListener( this );

		// The color buttons within a box
		JPanel buttonBox = new JPanel( new GridLayout( 1, COLORS.length) );

		for( int i = 0; i < COLORS.length; i++ ) {

			JButton button = new JButton();
			button.setBackground( COLORS[ i ] );
			button.setPreferredSize( new Dimension( 10, 20 ) );
			button.addActionListener( this );

			buttonBox.add( button );

			colorButtons[ i ] = button;
		}

		content.add( buttonBox, BorderLayout.PAGE_START );

		// Pack and show the main window
		pack();
		setResizable( false );
		setVisible( true );
	}

	public static void main( String[] args ) {
		new FloodIt();
	}

	/**
	 * Retrieve a random color out of the available colors
	 */
	public static Color getRandomColor() {
		return COLORS[ new Random().nextInt( COLORS.length ) ];
	}

	/**
	 * The button click handler
	 */
	@Override
	public void actionPerformed( ActionEvent e ) {

		Object source = e.getSource();

		if( source == newGameButton ) {
			playfield.init();
		}
		else {

			for( int i = 0; i < colorButtons.length; i++ ) {

				// Determine the clicked color and start to process with it
				if( source == colorButtons[ i ] ) {
					playfield.process( COLORS[ i ] );
					break;
				}
			}
		}

		// Just paint the grid again after setting new square colors
		playfield.repaint();
	}
}
