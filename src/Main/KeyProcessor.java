/* This will handle the "Hot Key" system. */

package Main;

import logic.Control;
import timer.stopWatchX;

public class KeyProcessor{
	// Static Fields
	private static char last = ' ';			// For debouncing purposes
	private static stopWatchX sw = new stopWatchX(50);
	
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
			changePosition(0, -(Main.speed));
			changeToNextFrame();
			// Main.playerBox.setY1(Main.playerBox.getY1() - Main.speed);
			// Main.playerBox.setY2(Main.playerBox.getY1() + Main.playerBox.getWidthAndHeight());
			break;
		
		case 'a':
			changePosition(-(Main.speed), 0);
			changeToNextFrame();
			Main.isRight = false;
			// Main.playerBox.setX1(Main.playerBox.getX1() - Main.speed);
			// Main.playerBox.setX2(Main.playerBox.getX1() + Main.playerBox.getWidthAndHeight());
			break;
			
		case 's':
			changePosition(0, Main.speed);
			changeToNextFrame();
			// Main.playerBox.setY1(Main.playerBox.getY1() + Main.speed);
			// Main.playerBox.setY2(Main.playerBox.getY1() + Main.playerBox.getWidthAndHeight());
			break;
			
		case 'd':
			changePosition(Main.speed, 0);
			changeToNextFrame();
			Main.isRight = true;
			break;
			
		case '$':
			Main.trigger = "space bar is triggered";
			break;
			
		case 'm':
			// For mouse coordinates
			Control.isMouseCoordsDisplayed = !Control.isMouseCoordsDisplayed;
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
	}
}