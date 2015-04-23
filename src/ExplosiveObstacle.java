import java.awt.Color;
import java.awt.Graphics;

public class ExplosiveObstacle {

	double x;
	double y;
	double dx;
	double dy;
	static double width = 20;
	static double height = 25;

	StartingPoint startingPoint;
	Player player;

	boolean visible = true;
	boolean destroy = false;
	boolean hasFallen = false;
	Platform current;
	int position;

	int gravity;
	double dt;

	double xShrink = 5;
	double yShrink = 5;
	double x1;
	double y1;
	double width1;
	double height1;

	public ExplosiveObstacle(int x, StartingPoint startingPoint, Player player,
			Platform current, int position) {
		// TODO Auto-generated constructor stub
		this.x = x;
		this.y = current.y - height;
		this.startingPoint = startingPoint;
		this.player = player;
		this.current = current;
		this.position = position;

		dx = 0;
		dy = 0;
		gravity = 4;
		dt = 0.3;

	}

	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		if (visible) {
			g.setColor(Color.RED);

			g.fillRect((int) x, (int) y + startingPoint.globalDy, (int) width,
					(int) height);
			g.setColor(Color.MAGENTA);
			g.drawRect((int) x, (int) y + startingPoint.globalDy, (int) width,
					(int) height);
			x1 = x;
			y1 = y;
			width1 = width;
			height1 = height;
		} else {
			x1 += xShrink;
			y1 += yShrink;
			width1 -= xShrink;
			height1 -= yShrink;
			if (width1 > 0 && height1 > 0) {
				g.setColor(Color.RED);
				g.fillRect((int) x1, (int) y1 + startingPoint.globalDy,
						(int) width1, (int) height1);
				g.setColor(Color.MAGENTA);
				g.drawRect((int) x1, (int) y1 + startingPoint.globalDy,
						(int) width1, (int) height1);
			}
		}
	}

	public void update(Graphics g) {
		// TODO Auto-generated method stub
		if (visible) {
			x += dx;
			// y += startingPoint.globalDy;
			// dx += 0.1;
			checkForPlayerCollision();
			canFallThrough();
		}

		if (destroy) {
			current.gradualDestruction((int) (x + width / 2), 10, false);
			// System.out.println("in");
			// System.out.println("Destruction");
		}

		if (hasFallen) {
			dy = dy + gravity * dt;
			y += dy * dt + 0.5 * gravity * dt * dt;

			if (y > current.y) {
				destroy = true;
				if (!destroy)
					current.gradualDestructioncount = 0;
				visible = false;
			}
		}
	}

	private void canFallThrough() {
		// TODO Auto-generated method stub
		if (!current.isPlatformUnderneath((int) (x + width / 2))) {
			// System.out.println("Falling THrough");

			boolean a = current.isPlatformUnderneath((int) (x));
			boolean b = current.isPlatformUnderneath((int) (x + width));

			if (a && !b) {

				dx = 1;
			}

			if (!a && b) {
				dx = -1;
			}

			if (!a && !b) {
				hasFallen = true;
				// System.out.println("HEKP");
			}

			if (hasFallen) {
				if (startingPoint.currentPosition + 1 != startingPoint.values
						.size()) {
					current = startingPoint.platforms.get(position + 1);
					position += 1;
					// startingPoint.currentPosition += 1;
				}

			}

		}
	}

	private void checkForPlayerCollision() {
		// TODO Auto-generated method stub
		int playerX = player.x;
		int playerY = player.y;
		int playerRad = player.radius;
		double playerDx = player.dx;

		if (playerX - playerRad < x && playerX + playerRad > x + width) {
			if (playerY + playerRad > y && playerY + playerRad < y + height) {
				// System.out.println("Explode");
				// player.currentPlatform.gradualDestruction((int) (x +
				// width/2), 7, false);
				destroy = true;
				current.gradualDestructioncount = 0;
				visible = false;
			}
		}

		if (visible) {
			// For reppelling the ball
			if ((playerX + playerRad > x && playerX - playerRad < x + width)
					&& ((playerY + playerRad > y + 5) && (playerY + playerRad < y
							+ height))) {
				player.dx *= -1;

				playerX -= playerDx;
				if (playerX < x + width / 2) {
					player.x = (int) (x - playerRad - 1);
				} else {
					player.x = (int) (x + width + playerRad + 1);
				}
			}
		}

		if (visible) {
			if (playerY + playerRad > y && playerY + playerRad < y + height / 2) {
				if (playerX + playerRad > x && playerX - playerRad < x) {
					// System.out.println("Explode1");
					destroy = true;
					current.gradualDestructioncount = 0;
					visible = false;
				}

				if (playerX - playerRad < x + width
						&& playerX + playerRad > x + width) {
					// System.out.println("Explode2");
					destroy = true;
					current.gradualDestructioncount = 0;
					visible = false;
					// System.out.println(destroy);
				}

			}

		}

		// if (playerX - playerRad < x + width) {
		// player.dx *= -1;
		// }

	}
}
