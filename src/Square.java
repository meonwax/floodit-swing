import java.awt.Color;
import java.awt.Graphics2D;

public class Square {

	protected Color color;

	public Square( Color color ) {
		this.color = color;
	}

	public void draw( Graphics2D g2d, int x, int y ) {

		g2d.setColor( color );
		g2d.fillRect( x, y, FloodIt.SQUARE_SIZE, FloodIt.SQUARE_SIZE );
		g2d.drawRect( x, y, FloodIt.SQUARE_SIZE, FloodIt.SQUARE_SIZE );
	}
}
