import java.awt.Color;
import java.awt.Graphics;

public class Bullet {

	double x;
	double y;
	double dx = 3;
	double height = 8;
	double width = 20;

	StartingPoint startingPoint;
	Player player;
	EnemyShooter shooter;

	boolean destroy = false;
	boolean visible = true;

	public Bullet(EnemyShooter shooter, StartingPoint startingPoint,
			Player player) {
		// TODO Auto-generated constructor stub
		this.shooter = shooter;
		this.startingPoint = startingPoint;
		this.player = player;

		y = shooter.shotY;
		x = shooter.shotX;
		dx = EnemyShooter.bulletSpeed;
	}

	public Bullet(StartingPoint startingPoint, Player player) {
		this.startingPoint = startingPoint;
		this.player = player;

		y = 280;
		x = -2000;
		dx = 5;
	}

	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		if (x <= startingPoint.getWidth()) {
			g.setColor(Color.GREEN);
			g.fillRoundRect((int) x, (int) y + startingPoint.globalDy,
					(int) width, (int) height, 10, 10);
		}
	}

	public void update(Graphics g) {
		// TODO Auto-generated method stub
		if (visible) {
			checkforCollision();
			x += dx;
			// y += startingPoint.globalDy;
			if (x > startingPoint.getWidth()) {
				if (shooter != null)
					x = shooter.shotX;
				else
					x = -20;
			}
		} else {

		}

		if (destroy) {
			visible = false;
			dx = 0;
			player.dx = 0;
			startingPoint.gameOver = true;
		}
	}

	private void checkforCollision() {
		// TODO Auto-generated method stub
		int playerX = player.x;
		int playerY = player.y;
		int playerRad = player.radius;

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
				destroy = true;
				// visible = false;
			}
		}
	}
}
