package Main;

import java.util.Random;

import Data.BoundingBox;
import Data.Vector2D;
import Data.spriteInfo;
import timer.stopWatchX;

public class Ghost {
	private Random rng;  
	private spriteInfo sprite;
	private spriteInfo slashSprite;
	private BoundingBox defensiveBoundingBox; // The box for hitting the user
	private BoundingBox offensiveBoundingBox; // The box for getting hit by the user
	private final int GAP = 20; // Small gap may be necessary to prevent too large of a hitbox
	private boolean isVisible = true;
	private boolean isHit = false;
	private static Ghost ghostTarget;
	private String slashTag, tag;
	private int speed = 10;
	private final int EXPAND_BOUNDING_BOX = 30;
	private Key key;
	// private stopWatchX sw;
	
	// Trial and Error
	private final int SLASH_X_GAP = 50; 
	private final int SLASH_Y_GAP = 50;
	
	public Ghost(String tag, String slashTag) {
		key = new Key(new spriteInfo(new Vector2D(-100, -100), "key"), new BoundingBox(-100, -100, -100, -100));
		rng = new Random();
		this.slashTag = slashTag;
		this.tag = tag;
		this.sprite = new spriteInfo(new Vector2D(rng.nextInt(1730 - 123) + 123, rng.nextInt(948 - 121) + 121), this.tag);
		this.slashSprite = new spriteInfo(new Vector2D(sprite.getCoords().getX() - SLASH_X_GAP, sprite.getCoords().getY() - SLASH_Y_GAP), this.slashTag);
		this.defensiveBoundingBox = new BoundingBox(this.sprite.getCoords().getX() - EXPAND_BOUNDING_BOX, this.sprite.getCoords().getX() + 100, this.sprite.getCoords().getY(), this.sprite.getCoords().getY() + 100);
		this.offensiveBoundingBox = new BoundingBox(defensiveBoundingBox.getX1() - (GAP * 4), defensiveBoundingBox.getX2() + (GAP * 4), defensiveBoundingBox.getY1() - (GAP * 4), defensiveBoundingBox.getY2() + (GAP * 4)); // Based off of the defensive bounding box
	}
	
	
	// Slash-then-hit is done so that the update() method main() method can last for a while to give a more convincing animation
	// public void slash() {
		// this.isHit = true;
	// }
	
	public void hitAnimation(Player player) {
		if (player.doesPlayerHaveSword()) {
			this.isHit = true;
			this.defensiveBoundingBox.destroy();
			this.offensiveBoundingBox.destroy();
			this.isVisible = false;
			key = new Key(new spriteInfo(new Vector2D(this.sprite.getCoords().getX(), this.sprite.getCoords().getY()), "key"), new BoundingBox(this.sprite.getCoords().getX() - 30, this.sprite.getCoords().getX() + 80, this.sprite.getCoords().getY() - 30, this.sprite.getCoords().getY() + 80));
			key.setVisibility(true); // will get passed to Main() metnod, which will display the key
		}
	}
	
	public void move() { // Ghost's AI
		int randomNum = rng.nextInt(4); 
		System.out.println(randomNum);
		if (randomNum == 0 && !(this.defensiveBoundingBox.getX2() + speed > Main.boundaryBoxes.get(4).getX1())) {
			// Move right
			System.out.println("reached");
			this.sprite.setCoords(this.sprite.getCoords().getX() + speed, this.sprite.getCoords().getY());
			this.slashSprite = new spriteInfo(new Vector2D(sprite.getCoords().getX() - SLASH_X_GAP, sprite.getCoords().getY() - SLASH_Y_GAP), slashTag);
			this.defensiveBoundingBox = new BoundingBox(this.defensiveBoundingBox.getX1() + speed, this.defensiveBoundingBox.getX2() + speed, this.defensiveBoundingBox.getY1(), this.defensiveBoundingBox.getY2());
			this.offensiveBoundingBox = new BoundingBox(this.offensiveBoundingBox.getX1() + speed, this.offensiveBoundingBox.getX2() + speed, this.offensiveBoundingBox.getY1(), this.offensiveBoundingBox.getY2());
		} else if (randomNum == 1 && !(this.defensiveBoundingBox.getX1() - speed < Main.boundaryBoxes.get(2).getX2())) {
			// Move left
			this.sprite.setCoords(this.sprite.getCoords().getX() - speed, this.sprite.getCoords().getY());
			this.slashSprite = new spriteInfo(new Vector2D(sprite.getCoords().getX() - SLASH_X_GAP, sprite.getCoords().getY() - SLASH_Y_GAP), slashTag);
			this.defensiveBoundingBox = new BoundingBox(this.defensiveBoundingBox.getX1() - speed, this.defensiveBoundingBox.getX2() - speed, this.defensiveBoundingBox.getY1(), this.defensiveBoundingBox.getY2());
			this.offensiveBoundingBox = new BoundingBox(this.offensiveBoundingBox.getX1() - speed, this.offensiveBoundingBox.getX2() - speed, this.offensiveBoundingBox.getY1(), this.offensiveBoundingBox.getY2());
		} else if (randomNum == 2 && (!(this.defensiveBoundingBox.getY1() - speed < Main.boundaryBoxes.get(0).getY2()) || !(this.defensiveBoundingBox.getY1() - speed < Main.boundaryBoxes.get(1).getY2() || !(this.defensiveBoundingBox.getY1() - speed < Main.door.getBoundingBox().getY2())))) {
			// Move up
			this.sprite.setCoords(this.sprite.getCoords().getX(), this.sprite.getCoords().getY() - speed);
			this.slashSprite = new spriteInfo(new Vector2D(sprite.getCoords().getX() - SLASH_X_GAP, sprite.getCoords().getY() - SLASH_Y_GAP), slashTag);
			this.defensiveBoundingBox = new BoundingBox(this.defensiveBoundingBox.getX1(), this.defensiveBoundingBox.getX2(), this.defensiveBoundingBox.getY1() - speed, this.defensiveBoundingBox.getY2() - speed);
			this.offensiveBoundingBox = new BoundingBox(this.offensiveBoundingBox.getX1(), this.offensiveBoundingBox.getX2(), this.offensiveBoundingBox.getY1() - speed, this.offensiveBoundingBox.getY2() - speed);
		} else if (randomNum == 3 && !(this.defensiveBoundingBox.getY2() + speed > Main.boundaryBoxes.get(3).getY1())) {
			// Move down
			this.sprite.setCoords(this.sprite.getCoords().getX(), this.sprite.getCoords().getY() + speed);
			this.slashSprite = new spriteInfo(new Vector2D(sprite.getCoords().getX() - SLASH_X_GAP, sprite.getCoords().getY() - SLASH_Y_GAP), slashTag);
			this.defensiveBoundingBox = new BoundingBox(this.defensiveBoundingBox.getX1(), this.defensiveBoundingBox.getX2(), this.defensiveBoundingBox.getY1() + speed, this.defensiveBoundingBox.getY2() + speed);
			this.offensiveBoundingBox = new BoundingBox(this.offensiveBoundingBox.getX1(), this.offensiveBoundingBox.getX2(), this.offensiveBoundingBox.getY1() + speed, this.offensiveBoundingBox.getY2() + speed);
		}
	
	}
	
	public boolean getVisibility() {
		return this.isVisible;
	}
	
	public spriteInfo getSprite() {
		return this.sprite;
	}
	
	public boolean hasBeenHit() {
		return this.isHit;
	}
	
	public static void setGhostTarget(Ghost ghost) {
		ghostTarget = ghost;
	}
	
	public static Ghost getGhostTarget() {
		return ghostTarget; 
	}
	
	public spriteInfo getSlashSprite() {
		return this.slashSprite;
	}
	
	public BoundingBox getDefensiveBoundingBox() {
		return this.defensiveBoundingBox;
	}
	
	public BoundingBox getOffensiveBoundingBox() {
		return this.offensiveBoundingBox;
	}
	
	public Key getKey() {
		return this.key;
	}
}

