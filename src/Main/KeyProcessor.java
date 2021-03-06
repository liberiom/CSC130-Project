/* This will handle the "Hot Key" system. */

package Main;

import logic.Control;
import timer.stopWatchX;

public class KeyProcessor{
	// Static Fields
	private static char last = ' ';			// For debouncing purposes
	private static stopWatchX sw = new stopWatchX(50);
	public static boolean isPaused = false;
	public static boolean oKeyEnabled = false;
	public static boolean qKeyEnabled = false;
	public static boolean uKeyEnabled = false;
	public static boolean spaceKeyEnabled = false;
	public static boolean kKeyEnabled = false;
	
	// Static Method(s)
	public static void processKey(char key){
		if(key == ' ')				return;
		// Debounce routine below...
		if(key == last)
			if(sw.isTimeUp() == false)			return;
		last = key;
		sw.resetWatch();
		
		/* TODO: You can modify values below here! */
		switch(key){
		case '%':								// ESC key
			System.exit(0);
			break;
		case 'w':
			if (!isPaused) {
				changePosition(0, -(Main.player.getPlayerSpeed()));
				changeToNextFrame();
				Main.player.setCurrentDirection("up");
			}
			break;
		
		case 'a':
			if (!isPaused) {
				changePosition(-(Main.player.getPlayerSpeed()), 0);
				changeToNextFrame();
				Main.player.setPlayerFacingRight(false);
				Main.player.setCurrentDirection("left");
			}
			break;
			
		case 's':
			if (!isPaused) {
				changePosition(0, Main.player.getPlayerSpeed());
				changeToNextFrame();
				Main.player.setCurrentDirection("down");
			}
			break;
			
		case 'd':
			if (!isPaused) {
				changePosition(Main.player.getPlayerSpeed(), 0);
				changeToNextFrame();
				Main.player.setPlayerFacingRight(true);
				Main.player.setCurrentDirection("right");
			}
			break;
			
		/*
		 * For anyone looking at the source code in here, the reason why there were uKeyEnabled, oKeyEnabled, etc. was because initially, inspecting each item prompted the player to press different keys (u, o, etc.).
		 * But that's not what the assignment said to do, instead it said to use the space bar for all inspecting. 
		 */
		case '$':
			if (spaceKeyEnabled) {
				Ghost.getGhostTarget().hitAnimation(Main.player);
			}
			if (oKeyEnabled) {
				Main.player.setPlayerHaveSword(true);
				Main.treasure.destroyBoundingBox();
				Main.treasure.setTreasureVisibility(false);
				isPaused = true;
				Main.isDialogBoxShowing = true;
				Main.isChestOpenedDialogue = true;
				// Pass User input to q
				qKeyEnabled = true;
				oKeyEnabled = false;
			}
			if (uKeyEnabled) {
				if (Main.player.doesPlayerHaveSword() && Main.player.doesPlayerHaveKey()) { // Last check to make sure that the player has the key AND sword before it is possible to unlock the door
					Main.door.setDoorLocked(false); // Unlocks the door
				}
				if (Main.door.isDoorLocked()) {
					isPaused = true;
					Main.isDialogBoxShowing = true;
					Main.isDoorLockedDialogue = true;
					// Pass to q
					qKeyEnabled = true;
					uKeyEnabled = false;
				} else {
					isPaused = true;
					Main.displayObjective = false; // The "Press u to open door" prompt is automatically gone because the boundingBox disappeared 
					Main.isDialogBoxShowing = true;
					Main.isDoorUnlockedDialogue = true;
					// No need to pass to Q here, the game is finished. The player would be guided to press the Esc key
				}
			}
			if (kKeyEnabled) {
				Main.player.setPlayerKeyStatus(true);
				Main.ghost1.getKey().destroyBoundingBox();
				Main.ghost1.getKey().setVisibility(false);
				isPaused = true;
				Main.isDialogBoxShowing = true;
				Main.isKeyDialog = true;
				// Pass UI to Q
				qKeyEnabled = true;
				kKeyEnabled = false;
			}
			break;
			
		case 'm':
			// For mouse coordinates
			Control.isMouseCoordsDisplayed = !Control.isMouseCoordsDisplayed;
			break;
			
		case 'q':
			if (qKeyEnabled) {
				Main.isDialogBoxShowing = false;
				Main.turnOffDialog();
				// Pass the input to the user
				isPaused = false;
				qKeyEnabled = false; // Prevents the user from pressing Q at the end and unpausing the game
			}
			break;
		}
	}
	
	
	private static void changePosition(int xMove, int yMove) {
		Main.player.getStartingPosition().setX(Main.player.getStartingPosition().getX() + xMove);
		Main.player.getStartingPosition().setY(Main.player.getStartingPosition().getY() + yMove);
	}
	private static void changeToNextFrame() {
		Main.player.getSpritesRight().add(Main.player.getSpritesRight().remove());
		Main.player.getSpritesLeft().add(Main.player.getSpritesLeft().remove());
		Main.player.getSwordRight().add(Main.player.getSwordRight().remove());
		Main.player.getSwordLeft().add(Main.player.getSwordLeft().remove());
	}
}