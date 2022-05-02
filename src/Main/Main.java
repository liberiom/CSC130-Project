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
	private static spriteInfo treasure;
	public static BoundingBox treasureBoundingBox;
	public static stopWatchX timer = new stopWatchX(3000);
	private static Color white = new Color(255, 255, 255);
	public static String trigger = "";
	public static Vector2D startPosition = new Vector2D(500, 500);
	public static Vector2D dummyVector2D = new Vector2D(0, 0);
	public static int speed = 10;
	public static boolean visible = true;
	public static boolean isRight = true;
	public static boolean hasSword = false;
	public static Queue<spriteInfo> spritesRight = new LinkedList<spriteInfo>();
	public static Queue<spriteInfo> spritesLeft = new LinkedList<spriteInfo>();
	public static Queue<spriteInfo> swordRight = new LinkedList<spriteInfo>();
	public static Queue<spriteInfo> swordLeft = new LinkedList<spriteInfo>();
	public static ArrayList<BoundingBox> boundaryBoxes = new ArrayList<BoundingBox>();
	public static ArrayList<BoundingBox> itemBoxes = new ArrayList<BoundingBox>();
	public static spriteInfo playerSprite;	
	// public static spriteInfo slashSprite;
	// public static spriteInfo leftSlashSprite;
	// public static spriteInfo upSlashSprite;
	// public static spriteInfo downSlashSprite;
	private static spriteInfo dialogTextbox;	
	public static BoundingBox playerBox;
	public static boolean tempHideString = false;
	public static boolean isTreasureVisible = true;
	private static Random rng = new Random();
	public static boolean isDialogBoxShowing;
	public static boolean isDoorUnlockedDialogue = false;
	public static boolean isDoorLockedDialogue = false;
	public static boolean isChestOpenedDialogue = false;
	public static boolean isKeyDialog = false;
	public static boolean isSlashing = false;
	public static boolean isFacingRight = true;
	
	// End Static fields...
	public static void main(String[] args) {
		Control ctrl = new Control();				// Do NOT remove!
		ctrl.gameLoop();							// Do NOT remove!
	}
	
	/* This is your access to things BEFORE the game loop starts */
	public static void start(){
		
		// Player Sprite
		playerSprite = new spriteInfo(startPosition, "frame1");
		
		// Slash
		// slashSprite = new spriteInfo(new Vector2D(-100, -100), "slash");
		// leftSlashSprite = new spriteInfo(new Vector2D(-100, -100), "flippedslash");
		
		// Loading the walking variables
		for (int i = 1; i <= 8; i++) {
			spritesRight.add(new spriteInfo(dummyVector2D, "frame" + i));
			spritesLeft.add(new spriteInfo(dummyVector2D, "flippedframe" + i));
			swordRight.add(new spriteInfo(dummyVector2D, "sword" + i));
			swordLeft.add(new spriteInfo(dummyVector2D, "flippedsword" + i)); 
		}
	
		// Player Box
		playerBox = new BoundingBox(playerSprite, 30, 100);
		
		// Dialog Boxes
		dialogTextbox = new spriteInfo(new Vector2D(737, 459), "dialogueTextbox");
				
		// Adding BoundingBoxes ArrayList
		boundaryBoxes.add(new BoundingBox(0, 1530, 0, 74)); // Top part 1
		boundaryBoxes.add(new BoundingBox(1709, 1920, 0, 70)); // Top part 2
		boundaryBoxes.add(new BoundingBox(0, 48, 0, 1028)); // Left
		boundaryBoxes.add(new BoundingBox(0, 1920, 1006, 1080)); // Bottom 
		boundaryBoxes.add(new BoundingBox(1805, 1920, 0, 1080)); // Right 
		
		// Items 
		treasure = new spriteInfo(new Vector2D(rng.nextInt(1730 - 123) + 123, rng.nextInt(948 - 121) + 121), "treasure");
				
		// Item BoundingBoxes
		treasureBoundingBox = new BoundingBox(treasure.getCoords().getX() - 30, treasure.getCoords().getX() + 80, treasure.getCoords().getY() - 30, treasure.getCoords().getY() + 80);  // Collision Box of the treasure box, separate from the other boxes
		System.out.println("Treasure X: " + treasure.getCoords().getX());
		System.out.println("Treausre Y: " + treasure.getCoords().getY());
		System.out.println("Treasure X1: " + treasureBoundingBox.getX1());
		System.out.println("Treausre X2: " + treasureBoundingBox.getX2());
		System.out.println("Treasure Y1: " + treasureBoundingBox.getY1());
		System.out.println("Treasure Y2: " + treasureBoundingBox.getY2());
		
		// Creating the background
		spriteInfo background = new spriteInfo(new Vector2D(0, 0), "background");
		
	} 
	
	/* This is your access to the "game loop" (It is a "callback" method from the Control class (do NOT modify that class!))*/
	public static void update(Control ctrl) {
		ctrl.addSpriteToFrontBuffer(0, 0, "background");
	
		// Treasure visibility
		if (isTreasureVisible) {
			ctrl.addSpriteToFrontBuffer(treasure.getCoords().getX(), treasure.getCoords().getY(), "treasure");
		}
	
		// Direction
		if (isRight) {
			if (hasSword) {
				ctrl.addSpriteToFrontBuffer(startPosition.getX(), startPosition.getY(), swordRight.peek().getTag());
			} else {
				ctrl.addSpriteToFrontBuffer(startPosition.getX(), startPosition.getY(), spritesRight.peek().getTag());
			}
		} else {
			if (hasSword) {
				ctrl.addSpriteToFrontBuffer(startPosition.getX(), startPosition.getY(), swordLeft.peek().getTag());
			} else {
				ctrl.addSpriteToFrontBuffer(startPosition.getX(), startPosition.getY(), spritesLeft.peek().getTag());
			}
		}
		
		// Dialog Box toggling 
		if (isDialogBoxShowing) {
			int nextLine = 30;
			int dialogBoxXCoord = dialogTextbox.getCoords().getX() + 10;
			int dialogBoxYCoord = dialogTextbox.getCoords().getY();
			ctrl.addSpriteToFrontBuffer(dialogTextbox.getCoords().getX(), dialogTextbox.getCoords().getY(), dialogTextbox.getTag());
			/*
			 * TODO: Test Dialog here 
			 */
			if (isChestOpenedDialogue) {
				ctrl.drawString(dialogBoxXCoord, dialogBoxYCoord + (nextLine * 1), "You found a sword! Use the sword by pressing the Spacebar near ", white);
				ctrl.drawString(dialogBoxXCoord, dialogBoxYCoord + (nextLine * 2), "enemies to kill them.", white);
				ctrl.drawString(dialogBoxXCoord, dialogBoxYCoord + (nextLine * 10), "Press Q to exit", white);
			} else if (isDoorUnlockedDialogue) {
				ctrl.drawString(dialogBoxXCoord, dialogBoxYCoord + (nextLine * 1), "You found a sword! Use the sword by pressing the Spacebar near ", white);
			} else if (isDoorLockedDialogue) {
				ctrl.drawString(dialogBoxXCoord, dialogBoxYCoord + (nextLine * 1), "Hmm... this door seems to be locked. There must be a key somewhere...", white);
			} else if (isKeyDialog) {
				
			} else {
				
			}
			KeyProcessor.isPaused = true;
		}
		
		// Updating the player's BoundingBox
		playerBox.setX1(playerSprite.getCoords().getX());
		playerBox.setX2(playerBox.getX1() + playerBox.getWidth());
		playerBox.setY1(playerSprite.getCoords().getY());
		playerBox.setY2(playerBox.getY1() + playerBox.getHeight());
		
		// Checking the player's collision against walls
		for (int i = 0; i < boundaryBoxes.size(); i++) {
			if (checkCollision(playerBox, boundaryBoxes.get(i))) {
				reboundPlayer(playerBox, boundaryBoxes.get(i));
			}
		}
		
		// Checking the player's collision against chests
		if (checkCollision(playerBox, treasureBoundingBox)) {
			isChestOpenedDialogue = true;
			KeyProcessor.oKeyEnabled = true;
			if (!tempHideString) {
				ctrl.drawString(treasure.getCoords().getX() - 50, treasure.getCoords().getY() + 70, "Press O to open the chest", white);
			}
		}
		
		// Slashing
		
		
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
	
	public static void turnOffDialog() {
		isChestOpenedDialogue = false;
		isDoorLockedDialogue = false;
		isKeyDialog = false;
		isDoorUnlockedDialogue = false;
	}
}
