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
			
		case '$':
			if (spaceKeyEnabled) {
				Ghost.getGhostTarget().hitAnimation(Main.player);
			}
			break;
			
		case 'm':
			// For mouse coordinates
			Control.isMouseCoordsDisplayed = !Control.isMouseCoordsDisplayed;
			break;
			
		case 'o':
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
			break;
			
		case 'u':
			if (uKeyEnabled) {
				isPaused = true;
				Main.isDialogBoxShowing = true;
				Main.isDoorLockedDialogue = true;
				// Pass to q
				qKeyEnabled = true;
				uKeyEnabled = false;
			}
			break;
			
		case 'q':
			if (qKeyEnabled) {
				Main.isDialogBoxShowing = false;
				Main.turnOffDialog();
				// Pass the input to the user
				isPaused = false;
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