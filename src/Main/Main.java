package Main;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
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
	public static int speed = 10;
	public static boolean visible = true;
	public static boolean isRight = true;
	public static Queue<spriteInfo> spritesRight = new LinkedList<spriteInfo>();
	public static Queue<spriteInfo> spritesLeft = new LinkedList<spriteInfo>();
	public static ArrayList<BoundingBox> boundaryBoxes = new ArrayList<BoundingBox>();
	public static ArrayList<BoundingBox> itemBoxes = new ArrayList<BoundingBox>();
	public static spriteInfo playerSprite = new spriteInfo(startPosition, "frame1");
	public static BoundingBox playerBox;
	private static Random rng = new Random();
	
	// End Static fields...
	public static void main(String[] args) {
		Control ctrl = new Control();				// Do NOT remove!
		ctrl.gameLoop();							// Do NOT remove!
	}
	
	/* This is your access to things BEFORE the game loop starts */
	public static void start(){
		
		// Loading the walking variables
		for (int i = 1; i <= 8; i++) {
			spritesRight.add(new spriteInfo(dummyVector2D, "frame" + i));
			spritesLeft.add(new spriteInfo(dummyVector2D, "flippedframe" + i));
		}
	
		// Player Box
		playerBox = new BoundingBox(playerSprite, 128, 128);
				
		// Adding BoundingBoxes ArrayList
		boundaryBoxes.add(new BoundingBox(0, 1580, 0, 74)); // Top part 1
		boundaryBoxes.add(new BoundingBox(1729, 1920, 0, 70)); // Top part 2
		boundaryBoxes.add(new BoundingBox(0, 48, 0, 1028)); // Left
		boundaryBoxes.add(new BoundingBox(0, 1920, 1026, 1080)); // Bottom 
		boundaryBoxes.add(new BoundingBox(1855, 1920, 0, 1080)); // Right 
		
		// Items x = 123 to 1730, y = 
		int randomX = rng.nextInt(1730 - 123) + 123;
		int randomY = rng.nextInt(948 - 121) + 121;
		spriteInfo treasure = new spriteInfo(new Vector2D(randomX, randomY), "treasure");
		
		// Item BoundingBoxes
		
		// Creating the background
		spriteInfo background = new spriteInfo(new Vector2D(0, 0), "background");
		
	} 
	
	/* This is your access to the "game loop" (It is a "callback" method from the Control class (do NOT modify that class!))*/
	public static void update(Control ctrl) {
		ctrl.addSpriteToFrontBuffer(0, 0, "background");
		if (isRight) {
			ctrl.addSpriteToFrontBuffer(startPosition.getX(), startPosition.getY(), spritesRight.peek().getTag());
		} else {
			ctrl.addSpriteToFrontBuffer(startPosition.getX(), startPosition.getY(), spritesLeft.peek().getTag());
		}
		// Updating the player's BoundingBox
		playerBox.setX1(playerSprite.getCoords().getX());
		playerBox.setX2(playerBox.getX1() + playerBox.getWidth());
		playerBox.setY1(playerSprite.getCoords().getY());
		playerBox.setY2(playerBox.getY1() + playerBox.getHeight());
		
		/*
		 * Checking the player's collision against walls, doors, enemies, and chests
		 */
		for (int i = 0; i < boundaryBoxes.size(); i++) {
			if (checkCollision(playerBox, boundaryBoxes.get(i))) {
				reboundPlayer(playerBox, boundaryBoxes.get(i));
			}
		}
		
	}
	
	private static boolean checkCollision(BoundingBox box1, BoundingBox box2) {
		final boolean COLLISION_NOT_DETECTED = (box1.getX1() > box2.getX2()) 
				|| (box1.getX2() < box2.getX1()) 
				|| (box1.getY1() > box2.getY2())
				|| (box1.getY2() < box2.getY1());
		
		if (COLLISION_NOT_DETECTED) {
			return false;
		}
		return true;
	}
	
	private static void reboundPlayer(BoundingBox box1, BoundingBox box2) {	
		final int PADDING = 1; // Padding insures that there is not an infinite collision
		int currentX = playerSprite.getCoords().getX();
		int currentY = playerSprite.getCoords().getY();	
		if (box1.getY1() < box2.getY2() && box1.getX1() < box2.getX2() && box1.getX2() > box2.getX1() && box1.getY2() > box2.getY2()) { // Top collision
			playerSprite.setCoords(currentX, box2.getY2() + PADDING);
		} else if (box1.getY2() > box2.getY1() && box1.getX1() < box2.getX2() && box1.getX2() > box2.getX1() && box1.getY1() < box2.getY1()) { // Bottom Collision
			playerSprite.setCoords(currentX, box2.getY1() - box1.getHeight() - PADDING);
		} else if (box1.getX1() < box2.getX2() && box1.getY1() < box2.getY2() && box1.getY2() > box2.getY1() && box1.getX2() > box2.getX2()) { // Left collision
			playerSprite.setCoords(box2.getX2() + PADDING, currentY);
		} else if (box1.getX2() > box2.getX1() && box1.getY1() < box2.getY2() && box1.getY2() > box2.getY1() && box1.getX1() < box2.getX1()) { // Right Collision
			playerSprite.setCoords(box2.getX1() - box1.getWidth() - PADDING, currentY);
		}
	}
}
