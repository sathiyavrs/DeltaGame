import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class EnemyShooter {

	double shotY;
	double shotX;
	ArrayList<Bullet> bullets;
	static double bulletSpeed = 5;

	StartingPoint startingPoint;
	Platform current;
	Player player;

	double x;
	double y;
	double width = 30;
	double height = 50;
	double gravity;
	double dt;
	double gameDy = -8;
	double dy;

	boolean destroy = false;
	boolean visible = true;
	boolean once = true;

	double xShrink = 7;
	double yShrink = 7;
	
	int params;

	public EnemyShooter(StartingPoint startingPoint, Platform current,
			Player player) {
		// TODO Auto-generated constructor stub
		this.startingPoint = startingPoint;
		this.current = current;
		this.player = player;

		shotX = x + width;
		x = 15;
		y = current.y - height;
		shotY = y - 20 + height;
		gravity = 4;
		dt = 0.3;
		dy = gameDy;

		bullets = new ArrayList<Bullet>();
		Bullet bullet = new Bullet(this, startingPoint, player);
		bullets.add(bullet);
	}

	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		if (visible) {
			for (int i = 0; i < bullets.size(); i++) {
				bullets.get(i).paint(g);
			}
			g.setColor(new Color(165, 42, 42));
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
				g.setColor(new Color(165, 42, 42));
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
				// y += startingPoint.globalDy;
				if (y + height < current.y) {
					dy = dy + gravity * dt;
					y += dy * dt + 0.5 * gravity * dt * dt;
				} else {
					dy = gameDy;
					y = (int) current.y - height - 1;
					// hasJumped = false;
				}
				for (int i = 0; i < bullets.size(); i++) {
					bullets.get(i).update(g);
				}
			} else {
				for (int i = 0; i < bullets.size(); i++) {
					bullets.get(i).dx = 0;
				}
				player.dx = 0;
				startingPoint.gameOver = true;
				gravity = 0;
			}
		} else {
			current.gradualDestruction((int) params, 4, true);
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
