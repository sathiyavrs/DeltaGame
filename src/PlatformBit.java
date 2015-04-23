import java.awt.Color;
import java.awt.Graphics;

public class PlatformBit {

	private StartingPoint startingPoint;
	Player player;

	int x;
	int y;
	double dy;
	double dx;
	int iniY;
	static final int height = 10;
	static final int width = 8;
	int gravity;
	double dt;

	public PlatformBit(StartingPoint startingPoint, int x, int y, Player player) {
		this.startingPoint = startingPoint;
		this.x = x;
		this.y = y;
		this.player = player;

		iniY = y;
		dy = 0;
		gravity = 4;
		dt = 0.3;
		// TODO Auto-generated constructor stub
	}

	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		g.setColor(Color.GRAY);
		g.fillRect(x, y + startingPoint.globalDy, width, height);

		if (dy > 0 && y > iniY + height) {
			g.setColor(Color.BLACK);
			g.fillRect(x, y + startingPoint.globalDy, width, height);
		}
	}

	public void update(Graphics g) {
		// TODO Auto-generated method stub
		// y += startingPoint.globalDy;
		if (dy > 0) {
			dy = dy + gravity * dt;
			y += dy * dt + 0.5 * gravity * dt * dt;
		}

		if (dx != 0) {
			x += dx;
		}

		if (y - height > startingPoint.getHeight()) {
			// y = startingPoint.getHeight() + height + 1;
		}

		if (y < 100) {
			y = 0;
		}
	}

}
