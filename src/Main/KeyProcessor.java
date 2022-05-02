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
				// Main.playerBox.setY1(Main.playerBox.getY1() - Main.speed);
				// Main.playerBox.setY2(Main.playerBox.getY1() + Main.playerBox.getWidthAndHeight());
			}
			break;
		
		case 'a':
			if (!isPaused) {
				changePosition(-(Main.player.getPlayerSpeed()), 0);
				changeToNextFrame();
				Main.player.setPlayerFacingRight(false);
				// Main.isFacingRight = false;
				// Main.playerBox.setX1(Main.playerBox.getX1() - Main.speed);
				// Main.playerBox.setX2(Main.playerBox.getX1() + Main.playerBox.getWidthAndHeight());
			}
			break;
			
		case 's':
			if (!isPaused) {
				changePosition(0, Main.player.getPlayerSpeed());
				changeToNextFrame();
				// Main.playerBox.setY1(Main.playerBox.getY1() + Main.speed);
				// Main.playerBox.setY2(Main.playerBox.getY1() + Main.playerBox.getWidthAndHeight());
			}
			break;
			
		case 'd':
			if (!isPaused) {
				changePosition(Main.player.getPlayerSpeed(), 0);
				changeToNextFrame();
				Main.player.setPlayerFacingRight(true);
				// Main.isFacingRight = true;
			}
			break;
			
		case '$':
			
			break;
			
		case 'm':
			// For mouse coordinates
			Control.isMouseCoordsDisplayed = !Control.isMouseCoordsDisplayed;
			break;
			
		case 'o':
			System.out.println("O has been pressed");
			if (oKeyEnabled) {
				Main.tempHideString = true; // Temporarily hides the String in case it overlays the dialog box
				Main.isDialogBoxShowing = true;
				qKeyEnabled = true; // I put this here so that people can't just skip the order of flow by just pressing the Q key before pressing the O key
			}
			break;
			
		case 'q':
			System.out.println("Q has been pressed");
			if (qKeyEnabled) {
				Main.tempHideString = false; // Puts that String back after the dialog box is finished showing
				isPaused = false;
				Main.isDialogBoxShowing = false;
				Main.player.setPlayerHaveSword(true);
				oKeyEnabled = false;
				qKeyEnabled = false;
				if (Main.isChestOpenedDialogue) {
					Main.treasure.setTreasureVisibility(false);
					Main.treasure.destroyBoundingBox();
				}
				Main.turnOffDialog();
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