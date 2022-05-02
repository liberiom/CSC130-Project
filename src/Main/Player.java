package Main;

import java.util.LinkedList;
import java.util.Queue;

import Data.BoundingBox;
import Data.Vector2D;
import Data.spriteInfo;

public class Player {
	private Vector2D startPosition = new Vector2D(500, 500);
	private Vector2D dummyVector2D = new Vector2D(0, 0);
	private int speed = 10;
	private boolean visible = true;
	private boolean isRight = true;
	private boolean hasSword = false;
	private Queue<spriteInfo> spritesRight = new LinkedList<spriteInfo>();
	private Queue<spriteInfo> spritesLeft = new LinkedList<spriteInfo>();
	private Queue<spriteInfo> swordRight = new LinkedList<spriteInfo>();
	private Queue<spriteInfo> swordLeft = new LinkedList<spriteInfo>();
	private spriteInfo playerSprite;
	private BoundingBox playerBox;	
	private boolean isSlashing;
	
	public Player() {
		playerSprite = new spriteInfo(startPosition, "frame1");
		playerBox = new BoundingBox(playerSprite, 30, 100);
		setFrames();
	}
	
	private void setFrames() {
		for (int i = 1; i <= 8; i++) {
			spritesRight.add(new spriteInfo(dummyVector2D, "frame" + i));
			spritesLeft.add(new spriteInfo(dummyVector2D, "flippedframe" + i));
			swordRight.add(new spriteInfo(dummyVector2D, "sword" + i));
			swordLeft.add(new spriteInfo(dummyVector2D, "flippedsword" + i)); 
		}
	}
	
	public boolean isPlayerFacingRight() {
		return this.isRight;
	}
	
	public void setPlayerFacingRight(boolean bool) {
		this.isRight = bool;
	}
	
	public boolean doesPlayerHaveSword() {
		return this.hasSword;
	}
	
	public BoundingBox getPlayerBoundingBox() {
		return this.playerBox;
	}
	
	public spriteInfo getPlayerSprite() {
		return this.playerSprite;
	}
	
	public Vector2D getStartingPosition() {
		return this.startPosition;
	}
	
	public Queue<spriteInfo> getSpritesRight() {
		return this.spritesRight;
	}
	public Queue<spriteInfo> getSwordRight() {
		return this.swordRight;
	}
	public Queue<spriteInfo> getSpritesLeft() {
		return this.spritesLeft;
	}
	public Queue<spriteInfo> getSwordLeft() {
		return this.swordLeft;
	}
	
	public int getPlayerSpeed() {
		return this.speed;
	}
	
	public void setPlayerHaveSword(boolean bool) {
		this.hasSword = bool;
	}
	
}

