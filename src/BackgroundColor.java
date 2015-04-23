import java.awt.Color;
import java.awt.Graphics;

public class BackgroundColor {

	Platform current;
	StartingPoint startingPoint;
	Color color;
	int y;

	public BackgroundColor(Platform current, StartingPoint startingPoint,
			Color color) {
		this.current = current;
		this.startingPoint = startingPoint;
		this.color = color;
		
		y = current.y;
	}

	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		g.setColor(color);
		g.fillRect(0, y + startingPoint.globalDy, startingPoint.getWidth(), 150);
	}
}
