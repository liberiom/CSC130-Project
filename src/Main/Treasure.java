package Main;
import Data.BoundingBox;
import Data.Vector2D;
import Data.spriteInfo;
import java.util.Random;

public class Treasure {
	private Random rng = new Random();
	private spriteInfo sprite;
	private BoundingBox treasureBoundingBox;
	private boolean isTreasureVisible;
	
	public Treasure() {
		sprite =  new spriteInfo(new Vector2D(rng.nextInt(1730 - 123) + 123, rng.nextInt(948 - 121) + 121), "treasure");
		treasureBoundingBox = new BoundingBox(sprite.getCoords().getX() - 80, sprite.getCoords().getX() + 80, sprite.getCoords().getY() - 80, sprite.getCoords().getY() + 80);
		isTreasureVisible = true;
	}
	
	public boolean isTreasureVisible() {
		return this.isTreasureVisible;
	}
	
	public spriteInfo getSprite() {
		return this.sprite;
	}
	
	public BoundingBox getBoundingBox() {
		return this.treasureBoundingBox;
	}
	
	public boolean setTreasureVisibility(boolean bool) {
		return this.isTreasureVisible = bool;
	}
	
	public void destroyBoundingBox() {
		this.treasureBoundingBox.destroy();
	}
}
