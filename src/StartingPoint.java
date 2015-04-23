import java.applet.Applet;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

public class StartingPoint extends Applet implements KeyListener, Runnable {

	private static final long serialVersionUID = 1L;

	private Image img;
	private Graphics doubleG;

	Player player;
	Platform platform, platform2;

	public int currentPosition = 0;
	boolean gameOver = false;
	int score = 0;

	int globalDy = 0;
	int globalddy = 0;

	int iterator;
	boolean changing = false;
	// int checker = 300;

	ArrayList<Integer> values = new ArrayList<Integer>();
	ArrayList<Platform> platforms = new ArrayList<Platform>();
	CoinChest coinChest;
	EnemyMoving enemyMoving;
	// Bullet bullet;
	EnemyShooter shooter;

	Random rand = new Random();
	Coin coin;

	ArrayList<Coin> coins = new ArrayList<Coin>();
	ArrayList<ExplosiveObstacle> explosives = new ArrayList<ExplosiveObstacle>();
	ArrayList<CoinChest> coinChests = new ArrayList<CoinChest>();
	ArrayList<EnemyMoving> movers = new ArrayList<EnemyMoving>();
	ArrayList<EnemyShooter> shooters = new ArrayList<EnemyShooter>();
	ArrayList<Color> colors = new ArrayList<Color>();
	ArrayList<BackgroundColor> moreColors = new ArrayList<BackgroundColor>();

	boolean once = true;
	int beforeChanged;

	@Override
	public void init() {
		// TODO Auto-generated method stub
		currentPosition = 0;
		gameOver = false;
		changing = false;
		score = 0;
		globalDy = 0;
		globalddy = 0;
		once = true;

		values = new ArrayList<Integer>();
		platforms = new ArrayList<Platform>();
		coins = new ArrayList<Coin>();
		explosives = new ArrayList<ExplosiveObstacle>();
		coinChests = new ArrayList<CoinChest>();
		movers = new ArrayList<EnemyMoving>();
		shooters = new ArrayList<EnemyShooter>();
		colors = new ArrayList<Color>();
		moreColors = new ArrayList<BackgroundColor>();

		setSize(400, 600);
		player = new Player(this);
		values.add(150);
		values.add(300);
		values.add(450);
		values.add(600);
		values.add(750);
		coin = new Coin(this, player, 300, 60);
		// values.add(120 * 5 + 20);

		colors.add(new Color(255, 232, 232));
		colors.add(new Color(255, 232, 255));
		colors.add(new Color(233, 232, 255));
		colors.add(new Color(232, 255, 255));
		colors.add(new Color(232, 255, 212));
		colors.add(new Color(255, 255, 232));

		for (int i = 0; i < values.size(); i++) {
			platforms.add(new Platform(this, player, values.get(i)));
			moreColors.add(new BackgroundColor(platforms.get(i), this, colors
					.get(rand.nextInt(colors.size()))));
		}

		coinChest = new CoinChest(100, this, player, platforms.get(1), 1);
		enemyMoving = new EnemyMoving(this, platforms.get(2), player, (int) 30);

		player.setCurrentPlatformLevel(150);

		// bullet = new Bullet(this, player);

		iterator = values.get(currentPosition);
		shooter = new EnemyShooter(this, platforms.get(1), player);

		// shooter.visible = false;
		addKeyListener(this);
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
		Thread gameThread = new Thread(this);
		gameThread.start();
	}

	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		for (int i = 0; i < moreColors.size(); i++) {
			moreColors.get(i).paint(g);
			// Platform temp = platforms.get(i);
		}

		player.paint(g);
		coin.paint(g);
		coinChest.paint(g);
		enemyMoving.paint(g);
		// bullet.paint(g);

		shooter.paint(g);

		for (int i = 0; i < values.size(); i++) {
			platforms.get(i).paint(g);

		}
		for (int i = 0; i < coins.size(); i++) {
			coins.get(i).paint(g);
		}
		for (int i = 0; i < explosives.size(); i++) {
			explosives.get(i).paint(g);
		}
		for (int i = 0; i < coinChests.size(); i++) {
			coinChests.get(i).paint(g);
		}
		for (int i = 0; i < shooters.size(); i++) {
			shooters.get(i).paint(g);

		}
		for (int i = 0; i < movers.size(); i++) {
			movers.get(i).paint(g);

		}

		Font font = new Font("Cambria Math", Font.PLAIN, 24);
		g.setFont(font);
		g.setColor(Color.BLACK);
		g.drawString("" + score, this.getWidth() - 45, 50 + 1);
		g.drawString("" + (platforms.size() - 4), getWidth() - 45, 76);

		font = new Font("Times New Roman", Font.BOLD, 32);
		g.setFont(font);
		if (gameOver) {
			g.drawString("GAME OVER", 105, 250);
			font = new Font("Cambria Math", Font.ITALIC, 24);
			g.setFont(font);
			g.drawString("Press Enter to play again", 50 + 22, 275);
		}

	}

	@Override
	public void update(Graphics g) {
		// TODO Auto-generated method stub

		if (iterator < values.get(currentPosition)) {
			changing = true;
			// checker = values.get(currentPosition) + 150;
			if (once)
				beforeChanged = currentPosition - 1;
			once = false;
		}
		if (once) {
			iterator = values.get(currentPosition);
			// once = false;
		}
		if (changing) {
			iterator += 3;
			globalDy -= 3;
			// System.out.println(iterator);
			// System.out.println("Changing");
			if (iterator >= values.get(currentPosition)) {
				changing = false;
				once = true;
				iterator = values.get(currentPosition);
			}
			// platforms.get(platforms.size()-1).y = 600;

			int type = rand.nextInt(12) + 2;
			Coin coin;
			ExplosiveObstacle explosive;
			CoinChest chest;
			EnemyShooter shooter;
			// System.out.println(type);
			EnemyMoving moving;

			// boolean again = iterator <= checker - 150;

			while (beforeChanged < currentPosition) {
				beforeChanged++;
				values.add(values.get(values.size() - 1) + 150);
				Platform platform = new Platform(this, player,
						values.get(values.size() - 1));
				platforms.add(platform);
				BackgroundColor backColor = new BackgroundColor(platform, this,
						colors.get(rand.nextInt(colors.size())));
				moreColors.add(backColor);

				switch (type) {

				case 2:
					coin = new Coin(this, player, this.getWidth() / 2,
							platform.y - 100);
					coins.add(coin);
					coin = new Coin(this, player, this.getWidth() / 2,
							platform.y - 70);
					coins.add(coin);
					coin = new Coin(this, player, this.getWidth() / 2 - 75,
							platform.y - 70);
					coins.add(coin);
					coin = new Coin(this, player, this.getWidth() / 2 + 75,
							platform.y - 70);
					coins.add(coin);
					coin = new Coin(this, player, this.getWidth() / 2,
							platform.y - 40);
					coins.add(coin);
					coin = new Coin(this, player, this.getWidth() / 2 - 100,
							platform.y - 40);
					coins.add(coin);
					coin = new Coin(this, player, this.getWidth() / 2 + 100,
							platform.y - 40);
					coins.add(coin);

					break;

				case 3:
					coin = new Coin(this, player, this.getWidth() / 2 - 100,
							platform.y - 100);
					coins.add(coin);
					coin = new Coin(this, player,
							this.getWidth() / 2 - 100 + 50, platform.y - 100);
					coins.add(coin);
					coin = new Coin(this, player,
							this.getWidth() / 2 - 100 + 50, platform.y - 70);
					coins.add(coin);
					coin = new Coin(this, player, this.getWidth() / 2 - 100
							+ 50 + 50, platform.y - 70);
					coins.add(coin);
					coin = new Coin(this, player, this.getWidth() / 2 - 100
							+ 50 + 50, platform.y - 40);
					coins.add(coin);
					coin = new Coin(this, player, this.getWidth() / 2 - 100
							+ 50 + 50 + 50, platform.y - 40);
					coins.add(coin);

					break;

				case 4:
					coin = new Coin(this, player, this.getWidth() / 2 - 150,
							platform.y - 100);
					coins.add(coin);
					coin = new Coin(this, player,
							this.getWidth() / 2 - 150 + 50, platform.y - 100);
					coins.add(coin);
					coin = new Coin(this, player,
							this.getWidth() / 2 - 150 + 50,
							platform.y - 100 + 50);
					coins.add(coin);
					coin = new Coin(this, player, this.getWidth() / 2 - 150,
							platform.y - 100 + 50);
					coins.add(coin);
					coin = new Coin(this, player, this.getWidth() / 2 + 150,
							platform.y - 100 + 25);
					coins.add(coin);
					break;

				case 5:
					explosive = new ExplosiveObstacle(
							(int) (this.getWidth() / 2 - 75 - ExplosiveObstacle.width / 2),
							this, player, platform, values.size() - 1);
					explosives.add(explosive);
					explosive = new ExplosiveObstacle(
							(int) (this.getWidth() / 2 + 75 - ExplosiveObstacle.width / 2),
							this, player, platform, values.size() - 1);
					explosives.add(explosive);

					chest = new CoinChest(
							(int) (this.getWidth() / 2 - CoinChest.width / 2),
							this, player, platform, values.size() - 1);
					coinChests.add(chest);

					break;

				case 6:
					chest = new CoinChest((int) (this.getWidth() / 2
							- CoinChest.width / 2 - 150), this, player,
							platform, values.size() - 1);
					coinChests.add(chest);
					chest = new CoinChest((int) (this.getWidth() / 2
							- CoinChest.width / 2 + 50), this, player,
							platform, values.size() - 1);
					coinChests.add(chest);
					break;

				case 7:
					shooter = new EnemyShooter(this, platform, player);
					shooters.add(shooter);
					break;

				case 8:
					moving = new EnemyMoving(this, platform, player,
							(int) enemyMoving.width);
					movers.add(moving);
					break;

				case 9:
					explosive = new ExplosiveObstacle((int) (this.getWidth()
							/ 2 - ExplosiveObstacle.width / 2 - 120), this,
							player, platform, values.size() - 1);
					explosives.add(explosive);
					explosive = new ExplosiveObstacle((int) (this.getWidth()
							/ 2 - ExplosiveObstacle.width / 2 - 50), this,
							player, platform, values.size() - 1);
					explosives.add(explosive);

					chest = new CoinChest((int) (this.getWidth() / 2
							- CoinChest.width / 2 + 100), this, player,
							platform, values.size() - 1);
					coinChests.add(chest);
					break;

				case 10:
					shooter = new EnemyShooter(this, platform, player);
					shooters.add(shooter);
					break;

				case 11:
					shooter = new EnemyShooter(this, platform, player);
					shooters.add(shooter);
					break;

				case 12:
					moving = new EnemyMoving(this, platform, player,
							(int) enemyMoving.width);
					movers.add(moving);
					break;

				case 13:
					moving = new EnemyMoving(this, platform, player,
							(int) enemyMoving.width);
					movers.add(moving);
					break;

				default:

					break;
				}
			}
			// This is where we have to add the platforms

		}
		globalDy += globalddy;

		for (int i = 0; i < values.size(); i++) {
			platforms.get(i).update(g);

			// Platform temp = platforms.get(i);

		}

		for (int i = 0; i < coins.size(); i++) {
			coins.get(i).update(g);
			if (coins.get(i).y < 0 || !coins.get(i).visible) {
				coins.remove(coins.get(i));
			}
		}

		for (int i = 0; i < explosives.size(); i++) {
			explosives.get(i).update(g);
			// if (explosives.get(i).y < 0
			// || !explosives.get(i).current
			// .isPlatformUnderneath((int) (explosives.get(i).x
			// + ExplosiveObstacle.width / 2 + 80))) {
			// explosives.remove(explosives.get(i));
			// // System.out.println("Happening");
			// }
		}

		for (int i = 0; i < coinChests.size(); i++) {
			coinChests.get(i).update(g);
			// if (coinChests.get(i).y < 0 || !coinChests.get(i).visible) {
			// coinChests.remove(coinChests.get(i));
			// }

			if (coinChests.get(i).once == false) {
				coinChests.remove(coinChests.get(i));
			}
		}// 9600063014

		for (int i = 0; i < shooters.size(); i++) {
			shooters.get(i).update(g);

		}

		for (int i = 0; i < movers.size(); i++) {
			movers.get(i).update(g);

		}

		// if (!enemyMoving.visible) {
		// if (enemyMoving.current
		// .isPlatformUnderneath(enemyMoving.params + 3 * 11)) {
		// enemyMoving.update(g);
		// }
		// } else {
		// enemyMoving.update(g);
		// }
		enemyMoving.update(g);
		coin.update(g);
		player.update(g);
		coinChest.update(g);

		// bullet.update(g);
		shooter.update(g);

		// platforms.get(2).gradualDestruction(10, 10, false);
		// platforms.get(2).gradualDestruction(200, 10, false);

		if (img == null) {
			img = createImage(this.getWidth(), this.getHeight());
			doubleG = img.getGraphics();
		}

		doubleG.setColor(Color.WHITE);
		doubleG.fillRect(0, 0, this.getWidth(), this.getHeight());

		doubleG.setColor(getForeground());
		paint(doubleG);

		g.drawImage(img, 0, 0, this);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			repaint();
			try {
				Thread.sleep(17);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if (!gameOver) {
			if (e.getKeyCode() == KeyEvent.VK_S && !player.hasJumped()) {
				player.setJump(26);

			}

			if (e.getKeyCode() == KeyEvent.VK_W && !player.hasJumped()) {
				player.setJump(23);

			}
			
			if (e.getKeyCode() == KeyEvent.VK_DOWN && !player.hasJumped()) {
				player.setJump(26);

			}

			if (e.getKeyCode() == KeyEvent.VK_UP && !player.hasJumped()) {
				player.setJump(23);

			}
		}

		if (gameOver) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				destroy();
				init();
				// start();
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}
