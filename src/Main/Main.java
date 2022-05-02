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
	public static Treasure treasure;
	public static Player player;
	public static Door door;
	private static Color white = new Color(255, 255, 255);
	public static ArrayList<BoundingBox> boundaryBoxes = new ArrayList<BoundingBox>();
	private static spriteInfo dialogTextbox;	
	public static boolean tempHideString = false;
	public static boolean isDialogBoxShowing;
	public static boolean isDoorUnlockedDialogue = false;
	public static boolean isDoorLockedDialogue = false;
	public static boolean isChestOpenedDialogue = false;
	public static boolean isKeyDialog = false;
	
	// End Static fields...
	public static void main(String[] args) {
		Control ctrl = new Control();				// Do NOT remove!
		ctrl.gameLoop();							// Do NOT remove!
	}
	
	/* This is your access to things BEFORE the game loop starts */
	public static void start(){
		
		// Player Sprite
		player = new Player();	
		
		// Dialog Boxes
		dialogTextbox = new spriteInfo(new Vector2D(737, 459), "dialogueTextbox");
				
		// Adding BoundingBoxes ArrayList
		boundaryBoxes.add(new BoundingBox(0, 1530, 0, 74)); // Top part 1
		boundaryBoxes.add(new BoundingBox(1679, 1920, 0, 70)); // Top part 2
		boundaryBoxes.add(new BoundingBox(0, 48, 0, 1028)); // Left
		boundaryBoxes.add(new BoundingBox(0, 1920, 1006, 1080)); // Bottom 
		boundaryBoxes.add(new BoundingBox(1805, 1920, 0, 1080)); // Right 
		
		// Items 
		treasure = new Treasure();
		
		// Creating the background
		spriteInfo background = new spriteInfo(new Vector2D(0, 0), "background");
	
		// Creating the door
		door = new Door();
	} 
	
	/* This is your access to the "game loop" (It is a "callback" method from the Control class (do NOT modify that class!))*/
	public static void update(Control ctrl) {
		ctrl.addSpriteToFrontBuffer(0, 0, "background");
		
		// Door visibility
		if (door.isDoorLocked()) {
			ctrl.addSpriteToFrontBuffer(door.getSprite().getCoords().getX(), door.getSprite().getCoords().getY(), door.getSprite().getTag());
		}
	
		// Treasure visibility
		if (treasure.isTreasureVisible()) {
			ctrl.addSpriteToFrontBuffer(treasure.getSprite().getCoords().getX(), treasure.getSprite().getCoords().getY(), treasure.getSprite().getTag());
		}
	
		// Direction
		if (player.isPlayerFacingRight()) {
			if (player.doesPlayerHaveSword()) {
				ctrl.addSpriteToFrontBuffer(player.getStartingPosition().getX(), player.getStartingPosition().getY(), player.getSwordRight().peek().getTag());
			} else {
				ctrl.addSpriteToFrontBuffer(player.getStartingPosition().getX(), player.getStartingPosition().getY(), player.getSpritesRight().peek().getTag());
			}
		} else {
			if (player.doesPlayerHaveSword()) {
				ctrl.addSpriteToFrontBuffer(player.getStartingPosition().getX(), player.getStartingPosition().getY(), player.getSwordLeft().peek().getTag());
			} else {
				ctrl.addSpriteToFrontBuffer(player.getStartingPosition().getX(), player.getStartingPosition().getY(), player.getSpritesLeft().peek().getTag());
			}
		}
		
		// Dialog Box toggling 
		if (isDialogBoxShowing) {
			int lineSpacing = 30;
			int dialogBoxXCoord = dialogTextbox.getCoords().getX() + 10;
			int dialogBoxYCoord = dialogTextbox.getCoords().getY();
			ctrl.addSpriteToFrontBuffer(dialogTextbox.getCoords().getX(), dialogTextbox.getCoords().getY(), dialogTextbox.getTag());
			if (isChestOpenedDialogue) {
				ctrl.drawString(dialogBoxXCoord, dialogBoxYCoord + nextLine(lineSpacing, 1), "You found a sword! Use the sword by pressing the Spacebar near ", white);
				ctrl.drawString(dialogBoxXCoord, dialogBoxYCoord + nextLine(lineSpacing, 2), "enemies to kill them.", white);
				ctrl.drawString(dialogBoxXCoord, dialogBoxYCoord + nextLine(lineSpacing, 10), "Press Q to exit", white);
			} else if (isDoorUnlockedDialogue) {
				ctrl.drawString(dialogBoxXCoord, dialogBoxYCoord + nextLine(lineSpacing, 1), "You found a sword! Use the sword by pressing the Spacebar near ", white);
			} else if (isDoorLockedDialogue) {
				ctrl.drawString(dialogBoxXCoord, dialogBoxYCoord + nextLine(lineSpacing, 1), "Hmm... this door seems to be locked. There must be a key somewhere...", white);
			} else if (isKeyDialog) {
				
			} else {
				
			}
			KeyProcessor.isPaused = true;
		}
		
		// Updating the player's BoundingBox
		player.getPlayerBoundingBox().setX1(player.getPlayerSprite().getCoords().getX());
		player.getPlayerBoundingBox().setX2(player.getPlayerBoundingBox().getX1() + player.getPlayerBoundingBox().getWidth());
		player.getPlayerBoundingBox().setY1(player.getPlayerSprite().getCoords().getY());
		player.getPlayerBoundingBox().setY2(player.getPlayerBoundingBox().getY1() + player.getPlayerBoundingBox().getHeight());
		
		// Checking the player's collision against walls and rebound player
		for (int i = 0; i < boundaryBoxes.size(); i++) {
			if (checkCollision(player.getPlayerBoundingBox(), boundaryBoxes.get(i))) {
				reboundPlayer(player.getPlayerBoundingBox(), boundaryBoxes.get(i));
			}
			// DON'T PUT GHOST BOUNDARY BOXES HERE!
		}
		
		// Checking the player's collision against chests
		if (checkCollision(player.getPlayerBoundingBox(), treasure.getBoundingBox())) {
			isChestOpenedDialogue = true;
			KeyProcessor.oKeyEnabled = true;
			if (!tempHideString) {
				ctrl.drawString(treasure.getSprite().getCoords().getX() - 50, treasure.getSprite().getCoords().getY() + 70, "Press O to open the chest", white);
			}
		}
		
		// Checking the player's collision against the door and rebound player
		if (checkCollision(player.getPlayerBoundingBox(), door.getBoundingBox())) {
			reboundPlayer(player.getPlayerBoundingBox(), door.getBoundingBox());
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
		int currentX = player.getPlayerSprite().getCoords().getX();
		int currentY = player.getPlayerSprite().getCoords().getY();	
		if (box1.getY1() < box2.getY2() && box1.getX1() < box2.getX2() && box1.getX2() > box2.getX1() && box1.getY2() > box2.getY2()) { // Top collision
			player.getPlayerSprite().setCoords(currentX, box2.getY2() + PADDING);
		} else if (box1.getY2() > box2.getY1() && box1.getX1() < box2.getX2() && box1.getX2() > box2.getX1() && box1.getY1() < box2.getY1()) { // Bottom Collision
			player.getPlayerSprite().setCoords(currentX, box2.getY1() - box1.getHeight() - PADDING);
		} else if (box1.getX1() < box2.getX2() && box1.getY1() < box2.getY2() && box1.getY2() > box2.getY1() && box1.getX2() > box2.getX2()) { // Left collision
			player.getPlayerSprite().setCoords(box2.getX2() + PADDING, currentY);
		} else if (box1.getX2() > box2.getX1() && box1.getY1() < box2.getY2() && box1.getY2() > box2.getY1() && box1.getX1() < box2.getX1()) { // Right Collision
			player.getPlayerSprite().setCoords(box2.getX1() - box1.getWidth() - PADDING, currentY);
		}
	}
	
	public static void turnOffDialog() {
		isChestOpenedDialogue = false;
		isDoorLockedDialogue = false;
		isKeyDialog = false;
		isDoorUnlockedDialogue = false;
	}
	
	private static int nextLine(int sizeOfLine, int numOfLines) {
		return sizeOfLine * numOfLines;	
	}
}
