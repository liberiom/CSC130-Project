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
	public Key() {}
	
	public boolean getVisibility() {
		return this.isVisible;
	}
	
	public void setVisibility(boolean bool) {
		this.isVisible = bool;
	}
	
	public spriteInfo getSprite() {
		return this.sprite;
	}
}
