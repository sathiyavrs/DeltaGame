import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

public class Platform {

	private StartingPoint startingPoint;
	int y;
	private Player player;

	double gradualDestructioncount = 0;
	double gradualDestructionIncrement = 0.5;
	int dyValue = 3;
	boolean gradInitiated = false;
	boolean broken = false;

	ArrayList<PlatformBit> level1;
	ArrayList<PlatformBit> level2;

	Random rand = new Random();
	boolean breakable = false;

	public Platform(StartingPoint startingPoint, Player player, int y) {
		// TODO Auto-generated constructor stub
		this.startingPoint = startingPoint;
		this.y = y;
		this.player = player;

		level1 = new ArrayList<PlatformBit>();
		level2 = new ArrayList<PlatformBit>();
		PlatformBit iterator;

		for (int x1 = 0; x1 <= (startingPoint.getWidth() - PlatformBit.width); x1 += PlatformBit.width) {
			iterator = new PlatformBit(startingPoint, x1, y, player);
			level1.add(iterator);
			iterator = new PlatformBit(startingPoint, x1, y
					+ PlatformBit.height, player);
			level2.add(iterator);

		}

		// System.out.println(level1.size());
	}

	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		for (int i = 0; i < startingPoint.getWidth() / PlatformBit.width; i += 1) {
			level1.get(i).paint(g);
			level2.get(i).paint(g);
		}
	}

	public void update(Graphics g) {
		// TODO Auto-generated method stub

		// y += startingPoint.globalDy;
		for (int i = 0; i < startingPoint.getWidth() / PlatformBit.width; i += 1) {
			level1.get(i).update(g);
			level2.get(i).update(g);
		}

		// gradualDestruction(150, 5, false);

		if (checkForCollision()) {

			if (player.maxHeight > 100 && breakable) {
				// System.out.println("True");

				// Now for Destructon
				int ballX = player.x;

				// System.out.println(ballX);

				int i = ballX / PlatformBit.width;

				try {
					level1.get(i).dy = dyValue;
					level1.get(i - 1).dy = dyValue + rand.nextInt(4);
					level1.get(i + 1).dy = dyValue + rand.nextInt(4);
					level1.get(i - 2).dy = dyValue + rand.nextInt(4);
					level1.get(i + 2).dy = dyValue + rand.nextInt(4);
					level1.get(i - 3).dy = dyValue + rand.nextInt(4);
					level1.get(i + 3).dy = dyValue + rand.nextInt(4);

					level2.get(i).dy = dyValue;
					level2.get(i - 1).dy = dyValue + rand.nextInt(4);
					level2.get(i + 1).dy = dyValue + rand.nextInt(4);
					level2.get(i - 2).dy = dyValue + rand.nextInt(4);
					level2.get(i + 2).dy = dyValue + rand.nextInt(4);

					if (rand.nextInt(6) == 2) {
						level1.get(i - 4).dy = dyValue + rand.nextInt(5);
					}

					if (rand.nextInt(6) == 1) {
						level1.get(i + 4).dy = dyValue + rand.nextInt(5);
					}

					if (rand.nextInt(6) == 1) {
						level2.get(i + 3).dy = dyValue + rand.nextInt(5);
					}

					if (rand.nextInt(6) == 1) {
						level2.get(i - 3).dy = dyValue + rand.nextInt(5);
					}

					if (rand.nextInt(6) == 1) {
						level2.get(i + 3).dy = dyValue + rand.nextInt(5);
						level2.get(i + 4).dy = dyValue + rand.nextInt(5);
					}

					if (rand.nextInt(6) == 1) {
						level2.get(i - 4).dy = dyValue + rand.nextInt(5);
						level2.get(i - 3).dy = dyValue + rand.nextInt(5);
					}

					player.setCurrentPlatformLevel(startingPoint.values
							.get(startingPoint.currentPosition + 1));
					startingPoint.currentPosition += 1;

					if (isPlatformUnderneath(player.x))
						player.dy -= 15;
				} catch (Exception e) {

				}
			}
			breakable = true;
		}

		// if (player.y >= y && player.y - player.radius < y
		// && !isPlatformUnderneath(player.x - player.radius)) {
		// // gradualDestruction(player.x + player.radius, 2);
		// gradualDestruction(player.x - player.radius, 2);
		//
		// }
		//
		// if (player.y >= y && player.y - player.radius < y
		// && !isPlatformUnderneath(player.x + player.radius)) {
		// gradualDestruction(player.x + player.radius, 2);
		// // gradualDestruction(player.x - player.radius, 2);
		//
		// }

		try {
			if (isPlatformUnderneath(player.x - player.radius)
					&& (player.y > y && player.y < y + 2 * PlatformBit.height)) {
				gradualDestruction(player.x - player.radius, 2, true);
				gradualDestruction(player.x - player.radius + 2, 2, true);
				gradualDestruction(player.x - player.radius + 4, 2, true);
				gradualDestruction(player.x - player.radius + 6, 2, true);
				gradualDestruction(player.x - player.radius + 8, 2, true);
				gradualDestruction(player.x - player.radius + 10, 2, true);
				gradualDestruction(player.x - player.radius + 12, 2, true);
				gradualDestruction(player.x - player.radius + 14, 2, true);
				gradualDestruction(player.x - player.radius + 16, 2, true);
				gradualDestruction(player.x - player.radius + 18, 2, true);
				gradualDestruction(player.x - player.radius + 20, 2, true);

				// System.out.println("IN");
			}

			if (isPlatformUnderneath(player.x + player.radius)
					&& (player.y > y && player.y < y + 2 * PlatformBit.height)) {
				gradualDestruction(player.x + player.radius, 2, true);
				gradualDestruction(player.x + player.radius - 2, 2, true);
				gradualDestruction(player.x + player.radius - 4, 2, true);
				gradualDestruction(player.x + player.radius - 6, 2, true);
				gradualDestruction(player.x + player.radius - 8, 2, true);
				gradualDestruction(player.x + player.radius - 10, 2, true);
				gradualDestruction(player.x + player.radius - 12, 2, true);
				gradualDestruction(player.x + player.radius - 14, 2, true);
				gradualDestruction(player.x + player.radius - 16, 2, true);
				gradualDestruction(player.x + player.radius - 18, 2, true);
				gradualDestruction(player.x + player.radius - 20, 2, true);

				// System.out.println("IN2");
			}
		} catch (Exception e) {

		}
		// if ((player.platformPosition == y) && level2.get(0).dy==0) {
		// breakable = true;
		// }

	}

	private boolean checkForCollision() {
		// TODO Auto-generated method stub
		// /player.y + player.radius > y - 100
		if ((player.y + player.radius > y) && (player.platformPosition == y)) {
			// System.out.println("TRUE");
			return true;
		} else {
			return false;
		}
	}

	public void gradualDestruction(int startingX, int length, boolean full) {
		int i = startingX / 8;

		if (length - gradualDestructioncount >= 0) {
			try {
				if (level1.get(i - (int) gradualDestructioncount).dy == 0)
					level1.get(i - (int) gradualDestructioncount).dy = dyValue
							+ rand.nextInt(5);

				if (level1.get(i + (int) gradualDestructioncount).dy == 0)
					level1.get(i + (int) gradualDestructioncount).dy = dyValue
							+ rand.nextInt(5);

				if (level2.get(i - (int) gradualDestructioncount).dy == 0)
					level2.get(i - (int) gradualDestructioncount).dy = dyValue
							+ rand.nextInt(5);

				if (level2.get(i + (int) gradualDestructioncount).dy == 0)
					level2.get(i + (int) gradualDestructioncount).dy = dyValue
							+ rand.nextInt(5);
			} catch (Exception e) {

			}
			gradualDestructioncount += gradualDestructionIncrement;

		} else {
			try {
				if (level1.get(i + length).dy == 0) {
					level1.get(i + length).dy = dyValue + rand.nextInt(5);

				}
				if (level1.get(i - length).dy == 0) {
					level1.get(i - length).dy = dyValue + rand.nextInt(5);

				}
				if (level1.get(i + length + 1).dy == 0) {
					level1.get(i + length + 1).dy = dyValue + rand.nextInt(5);

				}
				if (level1.get(i - length - 1).dy == 0) {
					level1.get(i - length - 1).dy = dyValue + rand.nextInt(5);

				}

				if (full) {
					if (level2.get(i + length).dy == 0) {
						level2.get(i + length).dy = dyValue + rand.nextInt(5);

					}
					if (level2.get(i - length).dy == 0) {
						level2.get(i - length).dy = dyValue + rand.nextInt(5);

					}
					if (level2.get(i + length + 1).dy == 0) {
						level2.get(i + length + 1).dy = dyValue
								+ rand.nextInt(5);

					}
					if (level2.get(i - length - 1).dy == 0) {
						level2.get(i - length - 1).dy = dyValue
								+ rand.nextInt(5);

					}
				}
			} catch (Exception E) {

			}

		}

	}

	public boolean isPlatformUnderneath(int valueX) {
		if (level1.get(valueX / 8).dy != 0) {
			if (level2.get(valueX / 8).dy != 0) {
				return false;
			}
		}
		return true;
	}

	public void checkForExplosive(int xVal) {
		// Has to be implemented
	}

}
