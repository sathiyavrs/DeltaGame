import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Coin {

	double x;
	double y;
	double dx;
	double dy;
	static int radius = 10;

	Random rand = new Random();

	boolean visible = true;
	double timing = 0;
	final int fullTime = 1;
	Color color = Color.WHITE;

	boolean followPath = false;

	private StartingPoint startingPoint;
	private Player player;

	public Coin(StartingPoint startingPoint, Player player, int x, int y) {
		// TODO Auto-generated constructor stub
		this.startingPoint = startingPoint;
		this.player = player;
		this.x = x;
		this.y = y;

		dx = 0;
		dy = 0;

	}

	public void paint(Graphics g) {
		// TODO Auto-generated method stub

		// Have to add some coin shimmerring thingy here.
		timing += 0.5;
		if (timing >= fullTime) {
			timing = 0;
			if (color == Color.ORANGE)
				color = Color.YELLOW;
			else
				color = Color.ORANGE;
		}

		if (visible) {
			g.setColor(color);
			g.fillOval((int) x - radius, (int) y - radius
					+ startingPoint.globalDy, radius * 2, radius * 2);
			g.setColor(Color.YELLOW);
			g.drawOval((int) x - radius, (int) y - radius
					+ startingPoint.globalDy, radius * 2, radius * 2);

		}
	}

	public void update(Graphics g) {
		// TODO Auto-generated method stub

		if (visible) {
			x += dx;
			y += dy;
			// y += startingPoint.globalDy;
			if (Math.abs(dx) < 1) {
				if (rand.nextInt(7) == 2) {
					if (dx > 0) {
						x += 1;

					}
					if (dx < 0) {
						x -= 1;
					}
				}
			}

			if (Math.abs(dy) < 1) {
				if (rand.nextInt(2) == 1) {

					if (dy > 0) {
						y += 1;

					}
					if (dy < 0) {
						y -= 1;
					}
				}
			}

			int ballX = player.x;
			int ballY = player.y;
			int ballRad = player.radius;

			double square = (x - ballX) * (x - ballX) + (y - ballY)
					* (y - ballY);
			final double square2 = (radius + ballRad) * (radius + ballRad);

			if (square - square2 < 0) {
				visible = false;
				startingPoint.score++;
			} else if (Math.abs(square - square2) < 1299) {
				// Follow path
				followPath = true;
			}

			if (followPath) {
				try {
					if (visible) {

						double m1 = (ballY - y);
						double m2 = (ballX - x);
						double m = m1 / m2;

						// System.out.println("FollowPath, " + Math.atan(m));
						dx = Math
								.abs(5 * Math.cos(Math.toRadians(Math.atan(m))));
						dy = Math.abs(250 * Math.sin(Math.toRadians(Math
								.atan(m))));

						if (x > ballX) {
							dx *= -1;
						}

						if (y > ballY) {
							dy *= -1;
						}
					}
				} catch (ArithmeticException e) {

				}
			}

		}
	}
}
