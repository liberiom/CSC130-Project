package Main;

import Data.BoundingBox;
import Data.spriteInfo;

public class Key {
	private spriteInfo sprite;
	private BoundingBox boundingBox;
	private boolean isVisible = false;
	
	// Create the new Key object in the ghost class
	public Key(spriteInfo sprite, BoundingBox boundingBox) {
		this.sprite = sprite;
		this.boundingBox = boundingBox;
	}
	
	// Other constructor to avoid NullPointerExceptions
	// public Key() {} -> not necessary, just use the other constructor with -100 data for x and y coordinates
	
	public boolean getVisibility() {
		return this.isVisible;
	}
	
	public void setVisibility(boolean bool) {
		this.isVisible = bool;
	}
	
	public spriteInfo getSprite() {
		return this.sprite;
	}
	
	public BoundingBox getBoundingBox() {
		return this.boundingBox;
	}
	
	public void destroyBoundingBox() {
		this.boundingBox.destroy();
	}
}
