package Main;

import Data.BoundingBox;
import Data.Vector2D;
import Data.spriteInfo;

public class Door {
	private spriteInfo sprite;
	private BoundingBox boundingBox;
	private boolean isLocked;
	private int xCoord, yCoord;
	
	public Door() {
		this.xCoord = 1580;
		this.yCoord = 73;
		this.sprite = new spriteInfo(new Vector2D(xCoord, yCoord), "door");
		this.boundingBox = new BoundingBox(1559, 1735, 0, 125);
		this.isLocked = true; // Start with a locked door
	}
	
	public spriteInfo getSprite() {
		return this.sprite;
	}
	
	public boolean isDoorLocked() {
		return this.isLocked;
	}
	
	public BoundingBox getBoundingBox() {
		return this.boundingBox;
	}
}
