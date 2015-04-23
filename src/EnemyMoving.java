import java.awt.Color;
import java.awt.Graphics;

public class EnemyMoving {

	double x;
	double y;
	double dx;
	double dy;
	double gameDy = -8;
	double gameDx = 4;
	double width = 30;
	double height = 50;
	double gravity;
	double dt;

	double xShrink = 7;
	double yShrink = 7;

	StartingPoint startingPoint;
	Platform current;
	Player player;

	boolean visible = true;
	boolean destroy = false;
	boolean once = true;
	int params;

	// Destroy means game OVer.

	public EnemyMoving(StartingPoint startingPoint, Platform current,
			Player player, int x) {
		// TODO Auto-generated constructor stub
		this.startingPoint = startingPoint;
		this.current = current;
		this.player = player;
		this.x = x;

		y = current.y - height;
		gameDx = 2.5;
		dx = gameDx;
		dy = gameDy;
		gravity = 4;
		dt = 0.3;
	}

	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		if (visible) {
			g.setColor(Color.BLUE);
			g.fillRect((int) x, (int) y + startingPoint.globalDy, (int) width,
					(int) height);
			g.setColor(Color.BLACK);
			g.drawRect((int) x, (int) y + startingPoint.globalDy, (int) width,
					(int) height);
		} else {
			x += xShrink;
			y += yShrink;
			width -= xShrink;
			height -= yShrink;
			if (width > 0 && height > 0) {
				g.setColor(Color.BLUE);
				g.fillRect((int) x, (int) y + startingPoint.globalDy,
						(int) width, (int) height);
				g.setColor(Color.BLACK);
				g.drawRect((int) x, (int) y + startingPoint.globalDy,
						(int) width, (int) height);
			}

			if (once) {
				startingPoint.score++;
				once = false;
			}
		}
	}

	public void update(Graphics g) {
		// TODO Auto-generated method stub
		if (visible) {

			if (!destroy) {
				checkForCollision();
				x += dx;
				// y += startingPoint.globalDy;
				if (x + width > startingPoint.getWidth()) {
					dx = -dx;
					x = startingPoint.getWidth() - width;
				}
				if (x - width < 0) {
					dx = -dx;
					x = width;
				}
				if (y + height < current.y) {
					dy = dy + gravity * dt;
					y += dy * dt + 0.5 * gravity * dt * dt;
				} else {
					dy = gameDy;
					if (dx > 0)
						dx = gameDx;
					else
						dx = -gameDx;
					y = (int) current.y - height - 1;
					// hasJumped = false;
				}
			} else {
				dx = 0;
				player.dx = 0;
				startingPoint.gameOver = true;
				gravity = 0;
				// player.gravity = 0;
			}

		} else {
			current.gradualDestruction((int) params, 3, true);
			// System.out.println("XXX"+x+" "+params);
			// System.out.println(current.isPlatformUnderneath(params + 3 *
			// 11));
			// System.out.println(params + "s");
			// System.out.println(i);
		}
	}

	private void checkForCollision() {
		// TODO Auto-generated method stub
		int playerX = player.x;
		int playerY = player.y;
		int playerRad = player.radius;
		// double playerDx = player.dx;

		if (playerX - playerRad < x && playerX + playerRad > x + width) {
			if (playerY + playerRad > y && playerY + playerRad < y + height) {
				// destroy = true;
				visible = false;
				params = (int) (x + width / 2);

			}
		}

		if (visible) {
			if (playerY + playerRad > y && playerY + playerRad < y + height / 2) {
				if (playerX + playerRad > x && playerX - playerRad < x) {
					// System.out.println("Explode1");
					// destroy = true;
					// current.gradualDestructioncount = 0;
					visible = false;
					params = (int) (x + width / 2);
				}

				if (playerX - playerRad < x + width
						&& playerX + playerRad > x + width) {
					// System.out.println("Explode2");
					// destroy = true;
					// current.gradualDestructioncount = 0;
					visible = false;
					params = (int) (x + width / 2);
					// System.out.println(destroy);
				}

			}

		}

		if (playerY > y && playerY < y + height && playerX + playerRad > x
				&& playerX - playerRad < x) {
			destroy = true;

		}

		if (playerY > y && playerY < y + height
				&& playerX + playerRad > x + width
				&& playerX - playerRad < x + width) {
			destroy = true;

		}

		if (playerX - playerRad < x && playerX + playerRad > x + width) {
			if (playerY + playerRad > y && playerY + playerRad < y + height) {
				// destroy = true;
				visible = false;
				params = (int) (x + width / 2);
			}
		}

	}
}
