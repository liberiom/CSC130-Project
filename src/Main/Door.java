package Main;

import Data.BoundingBox;
import Data.Vector2D;
import Data.spriteInfo;

public class Door {
	private spriteInfo sprite;
	private BoundingBox boundingBox;
	private BoundingBox dialogueBoundingBox;
	private boolean isLocked;
	private int xCoord, yCoord;
	
	public Door() {
		this.xCoord = 1580;
		this.yCoord = 21;
		this.sprite = new spriteInfo(new Vector2D(xCoord, yCoord), "door");
		this.boundingBox = new BoundingBox(1537, 1732, 0, 75);
		this.dialogueBoundingBox = new BoundingBox(this.boundingBox.getX1(), this.boundingBox.getX2(), 70, 152);
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
	
	public BoundingBox getDialogueBoundingBox() {
		return this.dialogueBoundingBox;
	}
	
	public void spawnDialogueBoundingBox() {
		this.dialogueBoundingBox = new BoundingBox(this.boundingBox.getX1(), this.boundingBox.getX2(), 70, 152);
	}
}
