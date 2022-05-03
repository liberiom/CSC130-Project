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
	public static boolean isDialogBoxShowing = false;
	public static boolean isDoorUnlockedDialogue = false;
	public static boolean isDoorLockedDialogue = false;
	public static boolean isChestOpenedDialogue = false;
	public static boolean isKeyDialog = false;
	private static int thinning = 20;
	private static ArrayList<Ghost> ghosts = new ArrayList<Ghost>();
	private static int slashFrames1, slashFrames2, slashFrames3;
	private static final int FINAL_SLASH_FRAMES = 120;
	private static final int NUM_OF_GHOSTS = 3;
	private static Ghost ghost1, ghost2, ghost3;
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
		boundaryBoxes.add(new BoundingBox(1729, 1920, 0, 70)); // Top part 2
		boundaryBoxes.add(new BoundingBox(0, 48, 0, 1028)); // Left
		boundaryBoxes.add(new BoundingBox(0, 1920, 1006, 1080)); // Bottom 
		boundaryBoxes.add(new BoundingBox(1805, 1920, 0, 1080)); // Right 
		
		// Items 
		treasure = new Treasure();
		
		// Ghosts
		/*
		 * for (int i = 0; i < NUM_OF_GHOSTS; i++) {
			ghosts.add(new Ghost());
			System.out.println(ghosts.get(i).getSprite().getCoords().getX()); 
			System.out.println(ghosts.get(i).getSprite().getCoords().getY());
		}
		 */
		ghost1 = new Ghost("ghost1", "slash1");
		slashFrames1 = 0;
		
		// Creating the background
		spriteInfo background = new spriteInfo(new Vector2D(0, 0), "background");
	
		// Creating the door
		door = new Door();
	} 
	
	/* This is your access to the "game loop" (It is a "callback" method from the Control class (do NOT modify that class!))*/
	public static void update(Control ctrl) {
		ctrl.addSpriteToFrontBuffer(0, 0, "background");
		// Debugging sysouts go here
		// System.out.println(player.getPlayerBoundingBox().getX1() + " " + player.getPlayerBoundingBox().getY1());
		
		/*
		 * TODO: MOVE THE IF STATEMENTS BELOW FOR THE GHOSTS OUT OF THEIR BOX. I HAVE ALREADY MADE A DEEP COPY
		 */
		
		if (ghost1.getVisibility()) {
			ctrl.addSpriteToFrontBuffer(ghost1.getSprite().getCoords().getX(), ghost1.getSprite().getCoords().getY(), ghost1.getSprite().getTag());
		}
		if (ghost1.hasBeenHit()) {
			if (slashFrames1 < FINAL_SLASH_FRAMES) {
				ctrl.addSpriteToFrontBuffer(ghost1.getSlashSprite().getCoords().getX(), ghost1.getSlashSprite().getCoords().getY(), ghost1.getSlashSprite().getTag());
				slashFrames1++;
			} else {
				resetFrames(slashFrames1);
			}
		}
		
			
		/*
		 * for (int i = 0; i < ghosts.size(); i++) {
			if (ghosts.get(i).getVisibility()) {
				ctrl.addSpriteToFrontBuffer(ghosts.get(i).getSprite().getCoords().getX(), ghosts.get(i).getSprite().getCoords().getY(), ghosts.get(i).getSprite().getTag());
			}
			if (ghosts.get(i).hasBeenHit()) {
				if (slashFrames < FINAL_SLASH_FRAMES) {
					ctrl.addSpriteToFrontBuffer(ghosts.get(i).getSlashSprite().getCoords().getX(), ghosts.get(i).getSlashSprite().getCoords().getY(), ghosts.get(i).getSlashSprite().getTag());
					slashFrames++;
				} else {
					resetFrames(slashFrames);
				}
			}
		}
		 */

		

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
				ctrl.drawString(dialogBoxXCoord, dialogBoxYCoord + nextLine(lineSpacing, 1), "You found a sword", white);
			} else if (isDoorLockedDialogue) {
				ctrl.drawString(dialogBoxXCoord, dialogBoxYCoord + nextLine(lineSpacing, 1), "Hmm... this door seems to be locked. There must be a key somewhere...", white);
				ctrl.drawString(dialogBoxXCoord, dialogBoxYCoord + nextLine(lineSpacing, 1), "somewhere...", white);
				ctrl.drawString(dialogBoxXCoord, dialogBoxYCoord + nextLine(lineSpacing, 10), "Press Q to quit", white);
			} else if (isKeyDialog) {
				
			}
		}
		
		// Updating the player's BoundingBox
		player.getPlayerBoundingBox().setX1(player.getPlayerSprite().getCoords().getX() + thinning);
		player.getPlayerBoundingBox().setX2(player.getPlayerBoundingBox().getX1() + player.getPlayerBoundingBox().getWidth() - thinning);
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
			if (!tempHideString) {
				ctrl.drawString(treasure.getSprite().getCoords().getX() - 50, treasure.getSprite().getCoords().getY() + 70, "Press O to open the chest", white);
			}
			KeyProcessor.oKeyEnabled = true;
		} else {
			KeyProcessor.oKeyEnabled = false;
		}
		
		// Checking the player's collision against the door and rebound player
		if (checkCollision(player.getPlayerBoundingBox(), door.getBoundingBox())) {
			reboundPlayer(player.getPlayerBoundingBox(), door.getBoundingBox());
		}
		
		// Door dialogue
		if (checkCollision(player.getPlayerBoundingBox(), door.getDialogueBoundingBox())) {
			ctrl.drawString(door.getSprite().getCoords().getX(), door.getBoundingBox().getY2(), "Press U to Open Door", white);
			KeyProcessor.uKeyEnabled = true;
		} else {
			KeyProcessor.uKeyEnabled = false;
		}
		
		// Ghost offensive collision
		/*
		 * for (int i = 0; i < ghosts.size(); i++) {
			if (checkCollision(player.getPlayerBoundingBox(), ghosts.get(i).getOffensiveBoundingBox())) {
				KeyProcessor.spaceKeyEnabled = true;
				Ghost.setGhostTarget(ghosts.get(i));
			} else {
				KeyProcessor.spaceKeyEnabled = false;
			}
		}
		 */
		if (checkCollision(player.getPlayerBoundingBox(), ghost1.getOffensiveBoundingBox())) {
			KeyProcessor.spaceKeyEnabled = true;
			Ghost.setGhostTarget(ghost1);
		} else {
			KeyProcessor.spaceKeyEnabled = false;
		}	
		
		// Ghost Defensive Collision
		for (int i = 0; i < ghosts.size(); i++) {
			// TODO: Add stuff here
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
		final int PADDING = 1; // Padding ensures that there is not an infinite collision
		int currentX = player.getPlayerSprite().getCoords().getX();
		int currentY = player.getPlayerSprite().getCoords().getY();	
		if (player.getCurrentDirection().equals("up")) { // Top collision
			player.getPlayerSprite().setCoords(currentX, box2.getY2() + PADDING);
		} else if (player.getCurrentDirection().equals("down")) { // Bottom Collision
			player.getPlayerSprite().setCoords(currentX, box2.getY1() - box1.getHeight() - PADDING);
		} else if (player.getCurrentDirection().equals("left")) { // Left collision
			player.getPlayerSprite().setCoords(box2.getX2() + PADDING - thinning, currentY);
		} else if (player.getCurrentDirection().equals("right")) { // Right Collision
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
	
	private static void resetFrames(int i) {
		i = 0;
	}
}
