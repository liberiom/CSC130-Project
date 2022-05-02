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
				changePosition(0, -(Main.speed));
				changeToNextFrame();
				// Main.playerBox.setY1(Main.playerBox.getY1() - Main.speed);
				// Main.playerBox.setY2(Main.playerBox.getY1() + Main.playerBox.getWidthAndHeight());
			}
			break;
		
		case 'a':
			if (!isPaused) {
				changePosition(-(Main.speed), 0);
				changeToNextFrame();
				Main.isRight = false;
				// Main.playerBox.setX1(Main.playerBox.getX1() - Main.speed);
				// Main.playerBox.setX2(Main.playerBox.getX1() + Main.playerBox.getWidthAndHeight());
			}
			break;
			
		case 's':
			if (!isPaused) {
				changePosition(0, Main.speed);
				changeToNextFrame();
				// Main.playerBox.setY1(Main.playerBox.getY1() + Main.speed);
				// Main.playerBox.setY2(Main.playerBox.getY1() + Main.playerBox.getWidthAndHeight());
			}
			break;
			
		case 'd':
			if (!isPaused) {
				changePosition(Main.speed, 0);
				changeToNextFrame();
				Main.isRight = true;
			}
			break;
			
		case '$':
			Main.trigger = "space bar is triggered";
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
				Main.hasSword = true;
				oKeyEnabled = false;
				qKeyEnabled = false;
				if (Main.isChestOpenedDialogue) {
					Main.isTreasureVisible = false;
					Main.treasureBoundingBox.destroy();
				}
				Main.turnOffDialog();
			}
			break;
		}
	}
	
	
	private static void changePosition(int xMove, int yMove) {
		Main.startPosition.setX(Main.startPosition.getX() + xMove);
		Main.startPosition.setY(Main.startPosition.getY() + yMove);
	}
	private static void changeToNextFrame() {
		Main.spritesRight.add(Main.spritesRight.remove());
		Main.spritesLeft.add(Main.spritesLeft.remove());
		Main.swordRight.add(Main.swordRight.remove());
		Main.swordLeft.add(Main.swordLeft.remove());
	}
}