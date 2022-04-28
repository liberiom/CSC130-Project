package Main;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

import Data.BoundingBox;
import Data.Vector2D;
import Data.spriteInfo;
import FileIO.EZFileRead;
import logic.Control;
import timer.stopWatchX;

public class Main{
	// Fields (Static) below...
	public static stopWatchX timer = new stopWatchX(3000);
	private static Color white = new Color(255, 255, 255);
	public static String trigger = "";
	public static Vector2D startPosition = new Vector2D(500, 500);
	public static Vector2D dummyVector2D = new Vector2D(0, 0);
	private static Vector2D lastPosition = new Vector2D(0,0);
	public static int speed = 10;
	public static boolean visible = true;
	public static boolean isRight = true;
	public static Queue<spriteInfo> spritesRight = new LinkedList<spriteInfo>();
	public static Queue<spriteInfo> spritesLeft = new LinkedList<spriteInfo>();
	public static ArrayList<BoundingBox> boxes = new ArrayList<BoundingBox>();
	private static spriteInfo playerStartingSprite = new spriteInfo(startPosition, "frame1");
	private static int currentX1 = playerStartingSprite.getCoords().getX();
	private static int currentX2 = playerStartingSprite.getCoords().getX() + 128;
	private static int currentY1 = playerStartingSprite.getCoords().getY();
	private static int currentY2 = playerStartingSprite.getCoords().getY() + 128;
	public static BoundingBox playerBox = new BoundingBox(currentX1, currentX2, currentY1, currentY2);
	
	// End Static fields...
	public static void main(String[] args) {
		Control ctrl = new Control();				// Do NOT remove!
		ctrl.gameLoop();							// Do NOT remove!
	}
	
	/* This is your access to things BEFORE the game loop starts */
	public static void start(){
		for (int i = 1; i <= 8; i++) {
			spritesRight.add(new spriteInfo(dummyVector2D, "frame" + i));
			spritesLeft.add(new spriteInfo(dummyVector2D, "flippedframe" + i));
		}
	
		// Player Box
		
				
		// Adding BoundingBoxes ArrayList
		boxes.add(new BoundingBox(0, 18, 0, 11));
		
	} 
	
	/* This is your access to the "game loop" (It is a "callback" method from the Control class (do NOT modify that class!))*/
	public static void update(Control ctrl) {
		if (isRight) {
			ctrl.addSpriteToFrontBuffer(startPosition.getX(), startPosition.getY(), spritesRight.peek().getTag());
			playerStartingSprite.setTag(spritesRight.peek().getTag());
		} else {
			ctrl.addSpriteToFrontBuffer(startPosition.getX(), startPosition.getY(), spritesLeft.peek().getTag());
			playerStartingSprite.setTag(spritesLeft.peek().getTag());
		}
		updatePlayerBoundingBox(playerStartingSprite, playerBox, 128);
		
		/*
		 * Checking the player's collision against walls, doors, enemies, and chests
		 */
		for (int i = 0; i < boxes.size(); i++) {
			if (checkCollision(playerBox, boxes.get(i))) {
				lastPosition.setX(startPosition.getX());
				lastPosition.setY(startPosition.getY());
				reboundPlayer(lastPosition);
			}
		}
		
	}
	
	private static boolean checkCollision(BoundingBox box1, BoundingBox box2) {
		final boolean COLLISION_DETECTED = (box1.getX1() > box2.getX2()) 
				|| (box1.getX2() < box2.getX1()) 
				|| (box1.getY1() > box2.getY2())
				|| (box1.getY2() < box2.getY1());
		
		if (COLLISION_DETECTED) {
			return true;
		}
		return false;
	}
	
	private static void reboundPlayer(Vector2D lastPosition) {
		startPosition = lastPosition;
	}
	
	
	public static void updatePlayerBoundingBox(spriteInfo sprite, BoundingBox box, int widthAndHeight) {
		box.setX1(sprite.getCoords().getX());
		box.setX2(sprite.getCoords().getX() + widthAndHeight);
		box.setY1(sprite.getCoords().getY());
		box.setY2(sprite.getCoords().getY() + widthAndHeight);
	}
	
}
