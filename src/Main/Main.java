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
	public static int speed = 10;
	public static boolean visible = true;
	public static boolean isRight = true;
	public static Queue<spriteInfo> spritesRight = new LinkedList<spriteInfo>();
	public static Queue<spriteInfo> spritesLeft = new LinkedList<spriteInfo>();
	public static ArrayList<BoundingBox> boxes = new ArrayList<BoundingBox>();
	public static spriteInfo playerSprite = new spriteInfo(startPosition, "frame1");
	public static BoundingBox playerBox;
	
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
		playerBox = new BoundingBox(playerSprite, 128, 128);
				
		// Adding BoundingBoxes ArrayList
		boxes.add(new BoundingBox(0, 1920, 0, 11));
		boxes.add(new BoundingBox(0, 1920, 1038, 1079));
		boxes.add(new BoundingBox(0, 100, 0, 1080));
		boxes.add(new BoundingBox(1920 - 50, 1920, 0, 1080));
	} 
	
	/* This is your access to the "game loop" (It is a "callback" method from the Control class (do NOT modify that class!))*/
	public static void update(Control ctrl) {
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
		
		// For deubgging purposes
		System.out.println(playerBox.toString());
		
		
		/*
		 * Checking the player's collision against walls, doors, enemies, and chests
		 */
		for (int i = 0; i < boxes.size(); i++) {
			if (checkCollision(playerBox, boxes.get(i))) {
				System.out.println("COLLISION DETECTED!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
				reboundPlayer(playerBox, boxes.get(i));
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
		final int PADDING = 1;
		int currentX = playerSprite.getCoords().getX();
		int currentY = playerSprite.getCoords().getY();
		if (box1.getY1() < box2.getY2()) { // Top collision
			playerSprite.setCoords(currentX, box2.getY2() + PADDING);
		} else if (box1.getY2() > box2.getY1()) { // Bottom Collision
			playerSprite.setCoords(currentX, box2.getY1() - playerBox.getHeight() - PADDING);
		} else if (box1.getX1() < box2.getX2()) { // Left collision
			playerSprite.setCoords(box2.getX2() + PADDING, currentY); 
		} else if (box1.getX2() > box2.getX1()) { // Right Collision
			playerSprite.setCoords(box2.getX1() - playerBox.getWidth() - PADDING, currentY);
		}
	}
}
