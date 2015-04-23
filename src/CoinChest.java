import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class CoinChest {

	double x;
	double y;
	double dx;
	double dy;
	static double width = 40;
	static double height = 40;

	StartingPoint startingPoint;
	Player player;

	boolean visible = true;
	boolean destroy = false;
	boolean hasFallen = false;
	boolean once = true;
	Platform current;
	int position;

	int gravity;
	int yIncrease = 0;
	double dt;
	ArrayList<Coin> coins;

	public CoinChest(int x, StartingPoint startingPoint, Player player,
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
		coins = new ArrayList<Coin>();
	}

	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		if (visible) {
			g.setColor(Color.YELLOW);
			g.fillRect((int) x, (int) y + startingPoint.globalDy, (int) width,
					(int) height);
			g.setColor(Color.ORANGE);
			g.drawLine((int) x, (int) y + startingPoint.globalDy,
					(int) (x + width), (int) y + startingPoint.globalDy);
			g.drawLine((int) x, (int) y + 1 + startingPoint.globalDy,
					(int) (x + width), (int) y + 1 + startingPoint.globalDy);

		}
		Coin coin;
		for (int i = 0; i < coins.size(); i++) {
			coin = coins.get(i);
			coin.paint(g);
		}
	}

	public void update(Graphics g) {
		// TODO Auto-generated method stub
		Coin coin;
		yIncrease += startingPoint.globalDy;
		if (visible) {
			x += dx;
			// y += startingPoint.globalDy;
			// dx += 0.1;
			checkForPlayerCollision();
			canFallThrough();

		}

		for (int i = 0; i < coins.size(); i++) {
			coin = coins.get(i);
			coin.update(g);
		}
		if (destroy) {
			// current.gradualDestruction((int) (x + width / 2), 15, false);
			if (once) {
				coin = new Coin(
						startingPoint,
						player,
						Coin.radius,
						(int) (Coin.radius
								+ startingPoint.values
										.get(startingPoint.currentPosition) - 150));
				coin.followPath = true;
				coins.add(coin);

				coin = new Coin(
						startingPoint,
						player,
						startingPoint.getWidth() - Coin.radius,
						(int) (Coin.radius
								+ startingPoint.values
										.get(startingPoint.currentPosition) - 150));
				coin.followPath = true;
				coins.add(coin);

				coin = new Coin(startingPoint, player, Coin.radius,
						(int) (Coin.radius
								+ startingPoint.values
										.get(startingPoint.currentPosition)
								- 150 + startingPoint.getHeight()));
				coin.followPath = true;
				coins.add(coin);

				coin = new Coin(startingPoint, player, startingPoint.getWidth()
						- Coin.radius,
						(int) (Coin.radius
								+ startingPoint.values
										.get(startingPoint.currentPosition)
								- 150 + startingPoint.getHeight()));
				coin.followPath = true;
				coins.add(coin);

				for (int i = 0; i < coins.size(); i++) {
					startingPoint.coins.add(coins.get(i));
				}
				once = false;
				// System.out.println("Destruction");
			}
		}

		if (hasFallen) {
			dy = dy + gravity * dt;
			y += dy * dt + 0.5 * gravity * dt * dt;

			if (y > current.y) {
				destroy = true;
				// if (!destroy)
				// current.gradualDestructioncount = 0;
				visible = false;

				current.checkForExplosive((int) (x + width / 2));
				current.checkForExplosive((int) (x));
				current.checkForExplosive((int) (x + width));

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
				visible = false;
			}
		}

		if (visible) {
			// For reppelling the ball
			if ((playerX + playerRad > x && playerX - playerRad < x + width)
					&& ((playerY + playerRad > y + height / 2) && (playerY
							+ playerRad < y + height))) {
				player.dx *= -1;

				playerX -= playerDx;
				if (playerX < x + width / 2) {
					if (playerX - playerRad < x - 10)
						player.x = (int) (x - playerRad - 1);
				} else {
					if (playerX + playerRad > x + width + 10)
						player.x = (int) (x + width + playerRad + 1);
				}
			}
		}

		if (visible) {
			if (playerY + playerRad > y && playerY + playerRad < y + height / 2) {
				if (playerX + playerRad > x && playerX - playerRad < x) {
					// System.out.println("Explode1");
					destroy = true;
					visible = false;
				}

				if (playerX - playerRad < x + width
						&& playerX + playerRad > x + width) {
					// System.out.println("Explode2");
					destroy = true;
					visible = false;
					// System.out.println(destroy);
				}

			}

		}
	}
}
