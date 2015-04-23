import java.awt.Color;
import java.awt.Graphics;

public class Player {

	StartingPoint startingPoint;

	int x;
	int y;
	int dx;
	int moveDx;
	int gameDx;
	double dy;
	double gameDy;
	int gravity;
	int radius = 17;
	double dt;
	double actualY;

	Platform currentPlatform;

	double maxHeight = 0;

	double platformPosition;

	boolean hasJumped;

	public Player(StartingPoint startingPoint) {
		// TODO Auto-generated constructor stub
		this.startingPoint = startingPoint;
		radius = 17;
		x = radius;
		platformPosition = 120;
		y = (int) platformPosition - radius - 1;
		gravity = 4;
		gameDx = 4;
		dx = gameDx;
		moveDx = gameDx - 2;
		dy = -5;
		gameDy = dy;
		dt = 0.3;
		actualY = y;
	}

	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		g.setColor(Color.BLACK);
		g.fillOval(x - radius, y - radius + startingPoint.globalDy, 2 * radius,
				2 * radius);
	}

	public void update(Graphics g) {
		// TODO Auto-generated method stub

		// System.out.println(startingPoint.currentPosition);
		currentPlatform = startingPoint.platforms
				.get(startingPoint.currentPosition);

		// System.out.println("PlatformPos: " + platformPosition);
		// System.out.println("Y + rad: " + (y + radius));

		// System.out.println(currentPlatform.isPlatformUnderneath(x));
		if (!currentPlatform.isPlatformUnderneath(x) && !currentPlatform.broken
				&& y + radius > platformPosition) {

			hasJumped = true;
			if (startingPoint.currentPosition + 1 != startingPoint.values
					.size()) {
				this.setCurrentPlatformLevel(startingPoint.values
						.get(startingPoint.currentPosition + 1));
				startingPoint.currentPosition += 1;
				currentPlatform.broken = true;
			}
		}
		
		x += dx;
		if (x + radius > startingPoint.getWidth()) {
			dx = -dx;
			x = startingPoint.getWidth() - radius;
		}
		if (x - radius < 0) {
			dx = -dx;
			x = radius;
		}

		if (y + radius < platformPosition) {
			dy = dy + gravity * dt;
			y += dy * dt + 0.5 * gravity * dt * dt;
		} else {
			maxHeight = 0;
			dy = gameDy;
			if (dx > 0)
				dx = gameDx;
			else
				dx = -gameDx;
			y = (int) platformPosition - radius - 1;
			hasJumped = false;
		}

		if (dy < 0 && dy + gravity * dt > 0) {
			if (y > maxHeight)
				maxHeight = platformPosition - y;
			// System.out.println("MaxHeight: " + maxHeight);
		}

		// System.out.println(maxHeight);

		// MaxHeight seems to be 80 at the topmost point

	}

	public void setJump(double speed) {
		if (y + radius + dy * dt + 0.5 * gravity * dt * dt > platformPosition) {
			y = (int) platformPosition - radius - 1;
			dy = -1 * speed;
		} else {
			dy = -1 * speed;
		}
		if (dx > 0)
			dx = moveDx;
		else
			dx = -moveDx;
		hasJumped = true;
	}

	public void setCurrentPlatformLevel(double position) {
		platformPosition = position;
	}

	public boolean hasJumped() {
		return hasJumped;
	}
}
