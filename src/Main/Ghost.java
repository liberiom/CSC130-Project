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
	private final int GAP = 20;
	private boolean isVisible = true;
	private boolean isHit = false;
	private static Ghost ghostTarget;
	// private stopWatchX sw;
	
	// Trial and Error
	private final int SLASH_X_GAP = 50; 
	private final int SLASH_Y_GAP = 50;
	
	public Ghost() {
		rng = new Random();
		this.sprite = new spriteInfo(new Vector2D(rng.nextInt(1730 - 123) + 123, rng.nextInt(948 - 121) + 121), "ghost");
		this.slashSprite = new spriteInfo(new Vector2D(sprite.getCoords().getX() - SLASH_X_GAP, sprite.getCoords().getY() - SLASH_Y_GAP), "slash");
		this.defensiveBoundingBox = new BoundingBox(sprite, 100, 100);
		this.offensiveBoundingBox = new BoundingBox(defensiveBoundingBox.getX1() - GAP, defensiveBoundingBox.getX2() + GAP, defensiveBoundingBox.getY1() - GAP, defensiveBoundingBox.getY2() + GAP); // Based off of the defensive bounding box
	}
	
	
	// Slash-then-hit is done so that the update() method main() method can last for a while to give a more convincing animation
	// public void slash() {
		// this.isHit = true;
	// }
	
	public void hitAnimation() {
		this.isHit = true;
		this.defensiveBoundingBox.destroy();
		this.offensiveBoundingBox.destroy();
		this.isVisible = false;
		// sprite.setCoords(-200, -200); // Maybe same coordinates as the BoundingBox disposal location? --> Actually unncessary, use an isVisible variable instead
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
}

